package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;
import com.jtransc.media.limelibgdx.glsl.ir.Operator;
import com.jtransc.media.limelibgdx.glsl.ir.Sir;

import java.util.ArrayList;
import java.util.LinkedList;

public class SirToIr3 {
	ArrayList<Ir3> out = new ArrayList<>();
	LinkedList<Operand> stack = new LinkedList<>();
	ArrayList<Sir> items;

	private SirToIr3(ArrayList<Sir> items) {
		this.items = items;
	}

	static public ArrayList<Ir3> convert(ArrayList<Sir> items) {
		SirToIr3 conv = new SirToIr3(items);
		conv.convert();
		return conv.out;
	}

	private void convert() {
		int tempId = 0;
		for (Sir item : items) {
			if (item instanceof Sir.Unop) {
				Operand temp = Operand.temp(tempId++);
				Operand source = stack.pop();
				out.add(new Ir3.Unop(temp, ((Sir.Unop) item).operator, source));
				stack.push(temp);
			} else if (item instanceof Sir.Binop) {
				Operand temp = Operand.temp(tempId++);
				Operand right = stack.pop();
				Operand left = stack.pop();

				out.add(new Ir3.Binop(temp, ((Sir.Binop) item).operator, left, right));
				stack.push(temp);
			} else if (item instanceof Sir.Get) {
				stack.push(((Sir.Get) item).operand);
			} else if (item instanceof Sir.Set) {
				Operand source = stack.pop();
				out.add(new Ir3.Unop(((Sir.Set) item).target, Operator.fromString("="), source));
			} else {
				throw new RuntimeException("SirToIr3.convert unhandled " + item);
			}
		}
	}
}
