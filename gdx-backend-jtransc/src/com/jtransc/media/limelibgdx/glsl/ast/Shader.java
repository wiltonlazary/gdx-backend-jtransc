package com.jtransc.media.limelibgdx.glsl.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shader {
	public List<Decl> decls = new ArrayList<Decl>();
	public HashMap<String, Decl.Global> varyings = new HashMap<String, Decl.Global>();
	public HashMap<String, Decl.Global> uniforms = new HashMap<String, Decl.Global>();
	public HashMap<String, Decl.Global> attributes = new HashMap<String, Decl.Global>();
	public HashMap<String, Decl.Precision> precisions = new HashMap<String, Decl.Precision>();
	public HashMap<String, Decl.Function> functions = new HashMap<String, Decl.Function>();

	public Shader(List<Decl> decls) {
		this.decls = decls;

		for (Decl decl : decls) {
			if (decl instanceof Decl.Global) {
				Decl.Global global = (Decl.Global) decl;
				switch (global.kind) {
					case "varying":
						varyings.put(global.name, global);
						break;
					case "uniform":
						uniforms.put(global.name, global);
						break;
					case "attribute":
						attributes.put(global.name, global);
						break;
				}
			} else if (decl instanceof Decl.Function) {
				functions.put(decl.name, (Decl.Function) decl);
			} else if (decl instanceof Decl.Precision) {
				precisions.put(decl.name, (Decl.Precision) decl);
			}
		}

	}
}