package edu.bit.hinge.memery;

import java.util.LinkedList;
import java.util.List;

import edu.bit.hinge.AST.AST;

public class MethodFunctionInstance extends FunctionInstance {

	public MethodFunctionInstance(String name, MemerySpace enclosingSpace,
			AST body) {
		super(name, enclosingSpace, body);
	}

	public Object run(Instance instance, List<Object> args) {
		if (args == null) {
			args = new LinkedList<Object>();
		}
		args.add(0, instance);
		return run(args);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<method function " + getName() + ">";
	}
}
