package edu.bit.hinge.memery;

public interface Space {

	Object get(String key);
	
	Object getAttribute(String key);
	
	void put(String key, Object val);
	
	String getName();
	
	Space getEnclosingSpace();
	
	Space getParentSpace();
	
}
