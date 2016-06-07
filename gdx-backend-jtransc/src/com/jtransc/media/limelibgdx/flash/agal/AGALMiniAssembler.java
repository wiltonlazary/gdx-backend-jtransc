package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.JTranscSystem;
import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.util.BinWriter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AGALMiniAssembler {
	static private class Regex {
		final Pattern pattern;
		final String flags;
		final boolean global;
		final boolean ignoreCase;

		public Regex(String pattern, String flags) {
			this.pattern = Pattern.compile(pattern);
			this.flags = flags;
			this.global = flags.contains("g");
			this.ignoreCase = flags.contains("i");
		}

		public String[] match(String reg) {
			Matcher m = pattern.matcher(reg);
			List<String> allMatches = new ArrayList<String>();
			while (m.find()) {
				allMatches.add(m.group());
			}
			if (allMatches.size() == 0) {
				return null;
			} else {
				return allMatches.toArray(new String[0]);
			}
		}

		public int search(String source) {
			Matcher matcher = pattern.matcher(source);
			while (matcher.find()) {
				return matcher.start();
			}
			return -1;
		}

		public String replace(String source, String replacement) {
			if (global) {
				return pattern.matcher(source).replaceAll(replacement);
			} else {
				return pattern.matcher(source).replaceFirst(replacement);
			}
		}
	}

	static private Regex REX(String pattern) {
		return new Regex(pattern, "");
	}

	static private Regex REX(String pattern, String flags) {
		return new Regex(pattern, flags);
	}

	// ======================================================================
	//	Constants
	// ----------------------------------------------------------------------
	//protected static final Regex REGEXP_OUTER_SPACES = REX("^\\s+|\\s+$");

	// ======================================================================
	//	Properties
	// ----------------------------------------------------------------------
	// AGAL bytes and error buffer
	public BinWriter agalcode = null;
	public String error = "";

	private boolean debugEnabled = false;

	private static boolean initialized = false;
	public boolean verbose = false;

	// ======================================================================
	//	Constructor
	// ----------------------------------------------------------------------
	public AGALMiniAssembler(boolean debugging) {
		debugEnabled = debugging;
		if (!initialized)
			init();
	}
	// ======================================================================
	//	Methods
	// ----------------------------------------------------------------------

	static private void trace(Object obj) {
		System.out.println(obj);
	}

	static private int getTimer() {
		return (int) JTranscSystem.stamp();
	}

	public byte[] assemble(ShaderType mode, String source) {
		return assemble(mode, source, 1, false);
	}

	public byte[] assemble(ShaderType mode, String source, int version, boolean ignorelimits) {
		double start = getTimer();

		agalcode = new BinWriter();
		error = "";

		boolean isFrag = false;

		if (mode == ShaderType.Fragment)
			isFrag = true;
		else if (mode != ShaderType.Vertex) {
			error = "ERROR: mode needs to be \"" + FRAGMENT + "\" or \"" + VERTEX + "\" but is \"" + mode + "\".";
		}

		agalcode.i8(0xa0);                // tag version
		agalcode.i32(version);        // AGAL version, big endian, bit pattern will be 0x01000000
		agalcode.i8(0xa1);                // tag program id
		agalcode.i8(isFrag ? 1 : 0);    // vertex or fragment

		initregmap(version, ignorelimits);

		String[] lines = REX("[\\f\\n\\r\\v]+", "g").replace(source, "\n").split("\n");
		int nest = 0;
		int nops = 0;
		int i;
		int lng = lines.length;

		for (i = 0; i < lng && Objects.equals(error, ""); i++) {
			String line = lines[i];
			line = line.trim();
			String lineRaw = line;


			// remove comments
			int startcomment = REX("//").search(line);
			if (startcomment != -1)
				line = line.substring(0, startcomment);

			// grab options
			int optsi = REX("<.*>", "g").search(line);
			String[] opts = null;
			if (optsi != -1) {
				opts = REX("([\\w\\.\\-\\+]+)", "gi").match(line.substring(optsi));
				line = line.substring(0, optsi);
			}

			// find opcode
			Object[] opCode = REX("\\w{3}", "ig").match(line);
			//Object[] opCode = REX("\\w\\w\\w", "ig").match(line);
			if (opCode == null) {
				if (line.length() >= 3) {
					trace("warning: bad line " + i + ": " + lines[i]);
				}
				continue;
			}
			OpCode opFound = OPMAP.get(opCode[0]);

			// if debug is enabled, output the opcodes
			if (debugEnabled)
				trace(opFound);

			if (opFound == null) {
				if (line.length() >= 3)
					trace("warning: bad line " + i + ": " + lines[i]);
				continue;
			}

			line = line.substring(REX(opFound.name).search(line) + opFound.name.length());

			if ((opFound.flags & OP_VERSION2) != 0 && version < 2) {
				error = "error: opcode requires version 2.";
				break;
			}

			if ((opFound.flags & OP_VERT_ONLY) != 0 && isFrag) {
				error = "error: opcode is only allowed in vertex programs.";
				break;
			}

			if ((opFound.flags & OP_FRAG_ONLY) != 0 && !isFrag) {
				error = "error: opcode is only allowed in fragment programs.";
				break;
			}
			if (verbose)
				trace("emit opcode=" + opFound);

			agalcode.i32(opFound.emitCode);
			nops++;

			if (nops > MAX_OPCODES) {
				error = "error: too many opcodes. maximum is " + MAX_OPCODES + ".";
				break;
			}

			// get operands, use regexp
			String[] regs;

			// will match both syntax
			regs = REX("vc\\[([vof][acostdip]?)(\\d*)?(\\.[xyzw](\\+\\d{1,3})?)?\\](\\.[xyzw]{1,4})?|([vof][acostdip]?)(\\d*)?(\\.[xyzw]{1,4})?", "gi").match(line);


			if (regs == null || regs.length != opFound.numRegister) {
				error = "error: wrong number of operands. found " + regs.length + " but expected " + opFound.numRegister + ".";
				break;
			}

			boolean badreg = false;
			int pad = 64 + 64 + 32;
			int regLength = regs.length;

			for (int j = 0; j < regLength; j++) {
				boolean isRelative = false;
				String[] relreg = REX("\\[.*\\]", "ig").match(regs[j]);
				if (relreg != null && relreg.length > 0) {
					regs[j] = regs[j].replace(relreg[0], "0");

					if (verbose)
						trace("IS REL");
					isRelative = true;
				}

				String[] res = REX("^\\b[A-Za-z]{1,2}", "ig").match(regs[j]);
				if (res == null) {
					error = "error: could not parse operand " + j + " (" + regs[j] + ").";
					badreg = true;
					break;
				}
				Register regFound = REGMAP.get(res[0]);

				// if debug is enabled, output the registers
				if (debugEnabled)
					trace(regFound);

				if (regFound == null) {
					error = "error: could not find register name for operand " + j + " (" + regs[j] + ").";
					badreg = true;
					break;
				}

				if (isFrag) {
					if ((regFound.flags & REG_FRAG) == 0) {
						error = "error: register operand " + j + " (" + regs[j] + ") only allowed in vertex programs.";
						badreg = true;
						break;
					}
					if (isRelative) {
						error = "error: register operand " + j + " (" + regs[j] + ") relative adressing not allowed in fragment programs.";
						badreg = true;
						break;
					}
				} else {
					if ((regFound.flags & REG_VERT) == 0) {
						error = "error: register operand " + j + " (" + regs[j] + ") only allowed in fragment programs.";
						badreg = true;
						break;
					}
				}

				regs[j] = regs[j].substring(regs[j].indexOf(regFound.name) + regFound.name.length());
				//trace( "REGNUM: " +regs[j] );
				String[] idxmatch = isRelative ? REX("\\d+").match(relreg[0]) : REX("\\d+").match(regs[j]);
				int regidx = 0;

				if (idxmatch != null)
					regidx = Integer.parseInt(idxmatch[0]);

				if (regFound.range < regidx) {
					error = "error: register operand " + j + " (" + regs[j] + ") index exceeds limit of " + (regFound.range + 1) + ".";
					badreg = true;
					break;
				}

				int regmask = 0;
				String[] maskmatch = REX("(\\.[xyzw]{1,4})").match(regs[j]);
				boolean isDest = (j == 0 && (opFound.flags & OP_NO_DEST) == 0);
				boolean isSampler = (j == 2 && (opFound.flags & OP_SPECIAL_TEX) != 0);
				int reltype = 0;
				int relsel = 0;
				int reloffset = 0;

				if (isDest && isRelative) {
					error = "error: relative can not be destination";
					badreg = true;
					break;
				}

				if (maskmatch != null) {
					regmask = 0;
					int cv = 0;
					int maskLength = maskmatch[0].length();
					int k;
					for (k = 1; k < maskLength; k++) {
						cv = maskmatch[0].charAt(k) - 'x';
						//System.out.println("CV:" + cv + " || " + maskmatch[0].charAt(k));
						if (cv < 0 || cv > 2)
							cv = 3;
						if (isDest)
							regmask |= 1 << cv;
						else
							regmask |= cv << ((k - 1) << 1);
					}
					if (!isDest)
						for (; k <= 4; k++)
							regmask |= cv << ((k - 1) << 1); // repeat last
				} else {
					regmask = isDest ? 0xf : 0xe4; // id swizzle or mask
				}

				if (isRelative) {
					String[] relname = REX("[A-Za-z]{1,2}", "ig").match(relreg[0]);
					Register regFoundRel = REGMAP.get(relname[0]);
					if (regFoundRel == null) {
						error = "error: bad index register";
						badreg = true;
						break;
					}
					reltype = regFoundRel.emitCode;
					String[] selmatch = REX("(\\.[xyzw]{1,1})").match(relreg[0]);
					if (selmatch.length == 0) {
						error = "error: bad index register select";
						badreg = true;
						break;
					}
					relsel = selmatch[0].charAt(1) - 'x';
					if (relsel > 2)
						relsel = 3;
					String[] relofs = REX("\\+\\d{1,3}", "ig").match(relreg[0]);
					if (relofs.length > 0)
						reloffset = Integer.parseInt(relofs[0]);
					if (reloffset < 0 || reloffset > 255) {
						error = "error: index offset " + reloffset + " out of bounds. [0..255]";
						badreg = true;
						break;
					}
					if (verbose)
						trace("RELATIVE: type=" + reltype + "==" + relname[0] + " sel=" + relsel + "==" + selmatch[0] + " idx=" + regidx + " offset=" + reloffset);
				}

				if (verbose)
					trace("  emit argcode=" + regFound + "[" + regidx + "][" + regmask + "]");
				if (isDest) {
					agalcode.i16(regidx);
					agalcode.i8(regmask);
					agalcode.i8(regFound.emitCode);
					pad -= 32;
				} else {
					if (isSampler) {
						if (verbose)
							trace("  emit sampler");
						int samplerbits = 5; // type 5
						int optsLength = opts == null ? 0 : opts.length;
						double bias = 0;
						int k = 0;
						for (k = 0; k < optsLength; k++) {
							if (verbose)
								trace("    opt: " + opts[k]);
							Sampler optfound = SAMPLEMAP.get(opts[k]);
							if (optfound == null) {
								// todo check that it's a number...
								//trace( "Warning, unknown sampler option: "+opts[k] );
								bias = Double.parseDouble(opts[k]);
								if (verbose)
									trace("    bias: " + bias);
							} else {
								if (optfound.flag != SAMPLER_SPECIAL_SHIFT)
									samplerbits &= ~(0xf << optfound.flag);
								samplerbits |= (int) (optfound.mask) << (int) (optfound.flag);
							}
						}
						agalcode.i16(regidx);
						agalcode.i8((int) (bias * 8.0));
						agalcode.i8(0);
						agalcode.i32(samplerbits);

						if (verbose)
							trace("    bits: " + (samplerbits - 5));
						pad -= 64;
					} else {
						if (j == 0) {
							agalcode.i32(0);
							pad -= 32;
						}
						agalcode.i16(regidx);
						agalcode.i8(reloffset);
						agalcode.i8(regmask);
						agalcode.i8(regFound.emitCode);
						agalcode.i8(reltype);
						agalcode.i16(isRelative ? (relsel | (1 << 15)) : 0);

						pad -= 64;
					}
				}
			}

			// pad unused regs
			for (int j = 0; j < pad; j += 8)
				agalcode.i8(0);

			if (badreg)
				break;
		}

		if (!Objects.equals(error, "")) {
			error += "\n  at line " + i + " " + lines[i];
			agalcode.clear();
			trace(error);
		}

		// trace the bytecode bytes if debugging is enabled
		if (debugEnabled) {
			String dbgLine = "generated bytecode:";
			int agalLength = agalcode.length();
			for (int index = 0; index < agalLength; index++) {
				if ((index % 16) == 0)
					dbgLine += "\n";
				if ((index % 4) == 0)
					dbgLine += " ";

				String byteStr = Integer.toString(agalcode.get(index), 16);
				if (byteStr.length() < 2)
					byteStr = "0" + byteStr;

				dbgLine += byteStr;
			}
			trace(dbgLine);
		}

		if (verbose)
			trace("AGALMiniAssembler.assemble time: " + ((getTimer() - start) / 1000) + "s");

		return agalcode.toByteArray();
	}

	private void initregmap(int version, boolean ignorelimits) {
		// version changes limits
		REGMAP.put(VA, new Register(VA, "vertex attribute", 0x0, ignorelimits ? 1024 : ((version == 1 || version == 2) ? 7 : 15), REG_VERT | REG_READ));
		REGMAP.put(VC, new Register(VC, "vertex constant", 0x1, ignorelimits ? 1024 : (version == 1 ? 127 : 249), REG_VERT | REG_READ));
		REGMAP.put(VT, new Register(VT, "vertex temporary", 0x2, ignorelimits ? 1024 : (version == 1 ? 7 : 25), REG_VERT | REG_WRITE | REG_READ));
		REGMAP.put(VO, new Register(VO, "vertex output", 0x3, ignorelimits ? 1024 : 0, REG_VERT | REG_WRITE));
		REGMAP.put(VI, new Register(VI, "varying", 0x4, ignorelimits ? 1024 : (version == 1 ? 7 : 9), REG_VERT | REG_FRAG | REG_READ | REG_WRITE));
		REGMAP.put(FC, new Register(FC, "fragment constant", 0x1, ignorelimits ? 1024 : (version == 1 ? 27 : ((version == 2) ? 63 : 199)), REG_FRAG | REG_READ));
		REGMAP.put(FT, new Register(FT, "fragment temporary", 0x2, ignorelimits ? 1024 : (version == 1 ? 7 : 25), REG_FRAG | REG_WRITE | REG_READ));
		REGMAP.put(FS, new Register(FS, "texture sampler", 0x5, ignorelimits ? 1024 : 7, REG_FRAG | REG_READ));
		REGMAP.put(FO, new Register(FO, "fragment output", 0x3, ignorelimits ? 1024 : (version == 1 ? 0 : 3), REG_FRAG | REG_WRITE));
		REGMAP.put(FD, new Register(FD, "fragment depth output", 0x6, ignorelimits ? 1024 : (version == 1 ? -1 : 0), REG_FRAG | REG_WRITE));

		// aliases
		REGMAP.put("op", REGMAP.get(VO));
		REGMAP.put("i", REGMAP.get(VI));
		REGMAP.put("v", REGMAP.get(VI));
		REGMAP.put("oc", REGMAP.get(FO));
		REGMAP.put("od", REGMAP.get(FD));
		REGMAP.put("fi", REGMAP.get(VI));
	}

	static private void init() {
		initialized = true;

		// Fill the dictionaries with opcodes and registers
		OPMAP.put(MOV, new OpCode(MOV, 2, 0x00, 0));
		OPMAP.put(ADD, new OpCode(ADD, 3, 0x01, 0));
		OPMAP.put(SUB, new OpCode(SUB, 3, 0x02, 0));
		OPMAP.put(MUL, new OpCode(MUL, 3, 0x03, 0));
		OPMAP.put(DIV, new OpCode(DIV, 3, 0x04, 0));
		OPMAP.put(RCP, new OpCode(RCP, 2, 0x05, 0));
		OPMAP.put(MIN, new OpCode(MIN, 3, 0x06, 0));
		OPMAP.put(MAX, new OpCode(MAX, 3, 0x07, 0));
		OPMAP.put(FRC, new OpCode(FRC, 2, 0x08, 0));
		OPMAP.put(SQT, new OpCode(SQT, 2, 0x09, 0));
		OPMAP.put(RSQ, new OpCode(RSQ, 2, 0x0a, 0));
		OPMAP.put(POW, new OpCode(POW, 3, 0x0b, 0));
		OPMAP.put(LOG, new OpCode(LOG, 2, 0x0c, 0));
		OPMAP.put(EXP, new OpCode(EXP, 2, 0x0d, 0));
		OPMAP.put(NRM, new OpCode(NRM, 2, 0x0e, 0));
		OPMAP.put(SIN, new OpCode(SIN, 2, 0x0f, 0));
		OPMAP.put(COS, new OpCode(COS, 2, 0x10, 0));
		OPMAP.put(CRS, new OpCode(CRS, 3, 0x11, 0));
		OPMAP.put(DP3, new OpCode(DP3, 3, 0x12, 0));
		OPMAP.put(DP4, new OpCode(DP4, 3, 0x13, 0));
		OPMAP.put(ABS, new OpCode(ABS, 2, 0x14, 0));
		OPMAP.put(NEG, new OpCode(NEG, 2, 0x15, 0));
		OPMAP.put(SAT, new OpCode(SAT, 2, 0x16, 0));
		OPMAP.put(M33, new OpCode(M33, 3, 0x17, OP_SPECIAL_MATRIX));
		OPMAP.put(M44, new OpCode(M44, 3, 0x18, OP_SPECIAL_MATRIX));
		OPMAP.put(M34, new OpCode(M34, 3, 0x19, OP_SPECIAL_MATRIX));
		OPMAP.put(DDX, new OpCode(DDX, 2, 0x1a, OP_VERSION2 | OP_FRAG_ONLY));
		OPMAP.put(DDY, new OpCode(DDY, 2, 0x1b, OP_VERSION2 | OP_FRAG_ONLY));
		OPMAP.put(IFE, new OpCode(IFE, 2, 0x1c, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR));
		OPMAP.put(INE, new OpCode(INE, 2, 0x1d, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR));
		OPMAP.put(IFG, new OpCode(IFG, 2, 0x1e, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR));
		OPMAP.put(IFL, new OpCode(IFL, 2, 0x1f, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR));
		OPMAP.put(ELS, new OpCode(ELS, 0, 0x20, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_DECNEST | OP_SCALAR));
		OPMAP.put(EIF, new OpCode(EIF, 0, 0x21, OP_NO_DEST | OP_VERSION2 | OP_DECNEST | OP_SCALAR));
		// space
		//OPMAP[ TED ] = new OpCode( TED, 3, 0x26, OP_FRAG_ONLY | OP_SPECIAL_TEX | OP_VERSION2);	//ted is not available in AGAL2
		OPMAP.put(KIL, new OpCode(KIL, 1, 0x27, OP_NO_DEST | OP_FRAG_ONLY));
		OPMAP.put(TEX, new OpCode(TEX, 3, 0x28, OP_FRAG_ONLY | OP_SPECIAL_TEX));
		OPMAP.put(SGE, new OpCode(SGE, 3, 0x29, 0));
		OPMAP.put(SLT, new OpCode(SLT, 3, 0x2a, 0));
		OPMAP.put(SGN, new OpCode(SGN, 2, 0x2b, 0));
		OPMAP.put(SEQ, new OpCode(SEQ, 3, 0x2c, 0));
		OPMAP.put(SNE, new OpCode(SNE, 3, 0x2d, 0));


		SAMPLEMAP.put(RGBA, new Sampler(RGBA, SAMPLER_TYPE_SHIFT, 0));
		SAMPLEMAP.put(DXT1, new Sampler(DXT1, SAMPLER_TYPE_SHIFT, 1));
		SAMPLEMAP.put(DXT5, new Sampler(DXT5, SAMPLER_TYPE_SHIFT, 2));
		SAMPLEMAP.put(VIDEO, new Sampler(VIDEO, SAMPLER_TYPE_SHIFT, 3));
		SAMPLEMAP.put(D2, new Sampler(D2, SAMPLER_DIM_SHIFT, 0));
		SAMPLEMAP.put(D3, new Sampler(D3, SAMPLER_DIM_SHIFT, 2));
		SAMPLEMAP.put(CUBE, new Sampler(CUBE, SAMPLER_DIM_SHIFT, 1));
		SAMPLEMAP.put(MIPNEAREST, new Sampler(MIPNEAREST, SAMPLER_MIPMAP_SHIFT, 1));
		SAMPLEMAP.put(MIPLINEAR, new Sampler(MIPLINEAR, SAMPLER_MIPMAP_SHIFT, 2));
		SAMPLEMAP.put(MIPNONE, new Sampler(MIPNONE, SAMPLER_MIPMAP_SHIFT, 0));
		SAMPLEMAP.put(NOMIP, new Sampler(NOMIP, SAMPLER_MIPMAP_SHIFT, 0));
		SAMPLEMAP.put(NEAREST, new Sampler(NEAREST, SAMPLER_FILTER_SHIFT, 0));
		SAMPLEMAP.put(LINEAR, new Sampler(LINEAR, SAMPLER_FILTER_SHIFT, 1));
		SAMPLEMAP.put(ANISOTROPIC2X, new Sampler(ANISOTROPIC2X, SAMPLER_FILTER_SHIFT, 2));
		SAMPLEMAP.put(ANISOTROPIC4X, new Sampler(ANISOTROPIC4X, SAMPLER_FILTER_SHIFT, 3));
		SAMPLEMAP.put(ANISOTROPIC8X, new Sampler(ANISOTROPIC8X, SAMPLER_FILTER_SHIFT, 4));
		SAMPLEMAP.put(ANISOTROPIC16X, new Sampler(ANISOTROPIC16X, SAMPLER_FILTER_SHIFT, 5));
		SAMPLEMAP.put(CENTROID, new Sampler(CENTROID, SAMPLER_SPECIAL_SHIFT, 1 << 0));
		SAMPLEMAP.put(SINGLE, new Sampler(SINGLE, SAMPLER_SPECIAL_SHIFT, 1 << 1));
		SAMPLEMAP.put(IGNORESAMPLER, new Sampler(IGNORESAMPLER, SAMPLER_SPECIAL_SHIFT, 1 << 2));
		SAMPLEMAP.put(REPEAT, new Sampler(REPEAT, SAMPLER_REPEAT_SHIFT, 1));
		SAMPLEMAP.put(WRAP, new Sampler(WRAP, SAMPLER_REPEAT_SHIFT, 1));
		SAMPLEMAP.put(CLAMP, new Sampler(CLAMP, SAMPLER_REPEAT_SHIFT, 0));
		SAMPLEMAP.put(CLAMP_U_REPEAT_V, new Sampler(CLAMP_U_REPEAT_V, SAMPLER_REPEAT_SHIFT, 2));
		SAMPLEMAP.put(REPEAT_U_CLAMP_V, new Sampler(REPEAT_U_CLAMP_V, SAMPLER_REPEAT_SHIFT, 3));
	}

	// ======================================================================
	//	Constants
	// ----------------------------------------------------------------------
	private static final Map<String, OpCode> OPMAP = new HashMap<>();
	private static final Map<String, Register> REGMAP = new HashMap<>();
	private static final Map<String, Sampler> SAMPLEMAP = new HashMap<>();

	private static final int MAX_NESTING = 4;
	private static final int MAX_OPCODES = 2048;

	private static final String FRAGMENT = "fragment";
	private static final String VERTEX = "vertex";

	// masks and shifts
	private static final int SAMPLER_TYPE_SHIFT = 8;
	private static final int SAMPLER_DIM_SHIFT = 12;
	private static final int SAMPLER_SPECIAL_SHIFT = 16;
	private static final int SAMPLER_REPEAT_SHIFT = 20;
	private static final int SAMPLER_MIPMAP_SHIFT = 24;
	private static final int SAMPLER_FILTER_SHIFT = 28;

	// regmap flags
	private static final int REG_WRITE = 0x1;
	private static final int REG_READ = 0x2;
	private static final int REG_FRAG = 0x20;
	private static final int REG_VERT = 0x40;

	// opmap flags
	private static final int OP_SCALAR = 0x1;
	private static final int OP_SPECIAL_TEX = 0x8;
	private static final int OP_SPECIAL_MATRIX = 0x10;
	private static final int OP_FRAG_ONLY = 0x20;
	private static final int OP_VERT_ONLY = 0x40;
	private static final int OP_NO_DEST = 0x80;
	private static final int OP_VERSION2 = 0x100;
	private static final int OP_INCNEST = 0x200;
	private static final int OP_DECNEST = 0x400;

	// opcodes
	private static final String MOV = "mov";
	private static final String ADD = "add";
	private static final String SUB = "sub";
	private static final String MUL = "mul";
	private static final String DIV = "div";
	private static final String RCP = "rcp";
	private static final String MIN = "min";
	private static final String MAX = "max";
	private static final String FRC = "frc";
	private static final String SQT = "sqt";
	private static final String RSQ = "rsq";
	private static final String POW = "pow";
	private static final String LOG = "log";
	private static final String EXP = "exp";
	private static final String NRM = "nrm";
	private static final String SIN = "sin";
	private static final String COS = "cos";
	private static final String CRS = "crs";
	private static final String DP3 = "dp3";
	private static final String DP4 = "dp4";
	private static final String ABS = "abs";
	private static final String NEG = "neg";
	private static final String SAT = "sat";
	private static final String M33 = "m33";
	private static final String M44 = "m44";
	private static final String M34 = "m34";
	private static final String DDX = "ddx";
	private static final String DDY = "ddy";
	private static final String IFE = "ife";
	private static final String INE = "ine";
	private static final String IFG = "ifg";
	private static final String IFL = "ifl";
	private static final String ELS = "els";
	private static final String EIF = "eif";
	private static final String TED = "ted";
	private static final String KIL = "kil";
	private static final String TEX = "tex";
	private static final String SGE = "sge";
	private static final String SLT = "slt";
	private static final String SGN = "sgn";
	private static final String SEQ = "seq";
	private static final String SNE = "sne";

	// registers
	private static final String VA = "va";
	private static final String VC = "vc";
	private static final String VT = "vt";
	private static final String VO = "vo";
	private static final String VI = "vi";
	private static final String FC = "fc";
	private static final String FT = "ft";
	private static final String FS = "fs";
	private static final String FO = "fo";
	private static final String FD = "fd";

	// samplers
	private static final String D2 = "2d";
	private static final String D3 = "3d";
	private static final String CUBE = "cube";
	private static final String MIPNEAREST = "mipnearest";
	private static final String MIPLINEAR = "miplinear";
	private static final String MIPNONE = "mipnone";
	private static final String NOMIP = "nomip";
	private static final String NEAREST = "nearest";
	private static final String LINEAR = "linear";
	private static final String ANISOTROPIC2X = "anisotropic2x"; //Introduced by Flash 14
	private static final String ANISOTROPIC4X = "anisotropic4x"; //Introduced by Flash 14
	private static final String ANISOTROPIC8X = "anisotropic8x"; //Introduced by Flash 14
	private static final String ANISOTROPIC16X = "anisotropic16x"; //Introduced by Flash 14
	private static final String CENTROID = "centroid";
	private static final String SINGLE = "single";
	private static final String IGNORESAMPLER = "ignoresampler";
	private static final String REPEAT = "repeat";
	private static final String WRAP = "wrap";
	private static final String CLAMP = "clamp";
	private static final String REPEAT_U_CLAMP_V = "repeat_u_clamp_v"; //Introduced by Flash 13
	private static final String CLAMP_U_REPEAT_V = "clamp_u_repeat_v"; //Introduced by Flash 13
	private static final String RGBA = "rgba";
	private static final String DXT1 = "dxt1";
	private static final String DXT5 = "dxt5";
	private static final String VIDEO = "video";


	static private class OpCode {
		// ======================================================================
		//	Properties
		// ----------------------------------------------------------------------
		public final int emitCode;
		public final int flags;
		public final String name;
		public final int numRegister;

		// ======================================================================
		//	Constructor
		// ----------------------------------------------------------------------
		OpCode(String name, int numRegister, int emitCode, int flags) {
			this.name = name;
			this.numRegister = numRegister;
			this.emitCode = emitCode;
			this.flags = flags;
		}

		// ======================================================================
		//	Methods
		// ----------------------------------------------------------------------
		public String toString() {
			return "[OpCode name=\"" + name + "\", numRegister=" + numRegister + ", emitCode=" + emitCode + ", flags=" + flags + "]";
		}
	}

	// ===========================================================================
	//	Class
	// ---------------------------------------------------------------------------
	static private class Register {
		// ======================================================================
		//	Properties
		// ----------------------------------------------------------------------
		private final int emitCode;
		private final String name;
		private final String longName;
		private final int flags;
		private final int range;

		// ======================================================================
		//	Constructor
		// ----------------------------------------------------------------------
		Register(String name, String longName, int emitCode, int range, int flags) {
			this.name = name;
			this.longName = longName;
			this.emitCode = emitCode;
			this.range = range;
			this.flags = flags;
		}

		// ======================================================================
		//	Methods
		// ----------------------------------------------------------------------
		public String toString() {
			return "[Register name=\"" + name + "\", longName=\"" + longName + "\", emitCode=" + emitCode + ", range=" + range + ", flags=" + flags + "]";
		}
	}

	// ===========================================================================
	//	Class
	// ---------------------------------------------------------------------------
	static private class Sampler {
		// ======================================================================
		//	Properties
		// ----------------------------------------------------------------------
		public final int flag;
		public final int mask;
		public final String name;

		// ======================================================================
		//	Constructor
		// ----------------------------------------------------------------------
		Sampler(String name, int flag, int mask) {
			this.name = name;
			this.flag = flag;
			this.mask = mask;
		}

		// ======================================================================
		//	Methods
		// ----------------------------------------------------------------------
		@Override
		public String toString() {
			return "[Sampler name=\"" + name + "\", flag=\"" + flag + "\", mask=" + mask + "]";
		}
	}
}