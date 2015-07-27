package edu.bit.hinge.memery;

import java.lang.reflect.Method;
import java.util.List;

public class JavaFunction implements Function {
	
	private String name;
	private JavaInstance instance;

	public JavaFunction(String name, JavaInstance instance) {
		this.name = name;
		this.instance = instance;
	}

	@Override
	public Object run(List<Object> args) {
		// TODO Auto-generated method stub
		Class<?>[] argTypes = new Class<?>[args.size()];
		for (int i = 0; i < args.size(); i++) {
			argTypes[i] = args.get(i).getClass();
		}
		
		try {
			Method method = instance.getType().getMethod(name, argTypes);
			return method.invoke(instance.getInstance(), args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Error("No such meth for '" + instance.getName() + "'");
		}
	}

}
