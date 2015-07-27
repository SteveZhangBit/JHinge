package edu.bit.hinge.memery;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JavaInstance implements Space {
	
	private Object instance;
	private Class<?> type;
	
	public JavaInstance(Object instance) {
		this.instance = instance;
		this.type = instance.getClass();
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		try {
			Field field = type.getField(key);
			return field.get(instance);
		} catch (Exception e) {
			
		}
		
		for (Method method : type.getMethods()) {
			if (method.getName().equals(key)) {
				return new JavaFunction(key, this);
			}
		}
		
		return null;
	}
	
	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		return get(key);
	}

	@Override
	public void put(String key, Object val) {
		// TODO Auto-generated method stub
		Field field;
		try {
			field = type.getField(key);
			field.set(instance, val);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Error("No such attribute for '" + type.getName() + "'");
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "<" + type.getName() + ">";
	}

	public Object getInstance() {
		return instance;
	}

	public Class<?> getType() {
		return type;
	}

	@Override
	public Space getEnclosingSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Space getParentSpace() {
		// TODO Auto-generated method stub
		return null;
	}

}
