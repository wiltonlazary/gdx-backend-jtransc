package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ast.Type;
import com.jtransc.media.limelibgdx.glsl.ir.*;

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
		Operand stackTemp = Operand.temp(0, Type.VEC4);
		for (Sir item : items) {
			// @TODO: CHECK operation type to determine type!
			if (item instanceof Sir.Unop) {
				Operand source = stack.pop();
				Type resultType = ((Sir.Unop) item).operator.getResultType(source.type);
				Operand temp = stackTemp.withLanesCount(resultType.getLaneCount());
				out.add(new Ir3.Unop(temp, ((Sir.Unop) item).operator, source));
				stack.push(temp);
			} else if (item instanceof Sir.Binop) {
				Operand right = stack.pop();
				Operand left = stack.pop();
				Type resultType = ((Sir.Binop) item).operator.getResultType(left.type, right.type);
				Operand temp = stackTemp.withLanesCount(resultType.getLaneCount());

				out.add(new Ir3.Binop(temp, ((Sir.Binop) item).operator, left, right));
				stack.push(temp);
			} else if (item instanceof Sir.Get) {
				stack.push(((Sir.Get) item).operand);
			} else if (item instanceof Sir.Set) {
				Operand source = stack.pop();
				out.add(new Ir3.Unop(((Sir.Set) item).target, UnaryOperator.fromString("="), source));
			} else {
				throw new RuntimeException("SirToIr3.compile unhandled " + item);
			}
		}
	}
}
