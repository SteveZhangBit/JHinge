package edu.bit.hinge.memery;

import java.util.List;

public class ClassInstance extends MemerySpace implements Function {

	private ClassInstance superClass;
	
	public ClassInstance(String name, MemerySpace enclosingSpace, ClassInstance superClass) {
		super(name, enclosingSpace);
		this.superClass = superClass;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<class " + getName() + ">";
	}

	@Override
	public Object run(List<Object> args) {
		// TODO Auto-generated method stub
		MethodFunctionInstance init = (MethodFunctionInstance) getAttribute("__init__");
		Instance instance = new Instance(getName(), this);
		if (init != null) {
			init.run(instance, args);
		}
		return instance;
	}
	
	@Override
	public Space getParentSpace() {
		// TODO Auto-generated method stub
		return superClass;
	}
}
