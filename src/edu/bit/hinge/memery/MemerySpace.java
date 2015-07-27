package edu.bit.hinge.memery;

import java.util.HashMap;
import java.util.Map;

public class MemerySpace implements Space {
	
	private String name;
	private Map<String, Object> members = new HashMap<String, Object>();
	private MemerySpace enclosingSpace;
	
	public MemerySpace(String name, MemerySpace enclosingSpace) {
		this.name = name;
		this.enclosingSpace = enclosingSpace;
	}
	
	@Override
	public Object get(String key) {
		System.out.println("get " + key + " from " + toString()
				+ " val: " + members.get(key));
		Object val = members.get(key);
		if (val != null) {
			return val;
		}
		if (getEnclosingSpace() != null) {
			return getEnclosingSpace().get(key);
		}
		return null;
	}
	
	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		System.out.println("get attribute " + key + " from " + toString()
				+ " val: " + members.get(key));
		Object val = members.get(key);
		if (val != null) {
			return val;
		}
		if (getParentSpace() != null) {
			return getParentSpace().getAttribute(key);
		}
		return null;
	}
	
	@Override
	public void put(String key, Object val) {
		System.out.println("put " + key + " to " + toString()
				+ " val: " + val);
		members.put(key, val);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public MemerySpace getEnclosingSpace() {
		return enclosingSpace;
	}
	
	@Override
	public Space getParentSpace() {
		// TODO Auto-generated method stub
		return enclosingSpace;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<" + name + ">";
	}
}
