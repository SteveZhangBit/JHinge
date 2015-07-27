package edu.bit.hinge.memery;

public class Instance extends MemerySpace {
	
	private String typeName;

	public Instance(String typeName, MemerySpace enclosingSpace) {
		super(null, enclosingSpace);
		// TODO Auto-generated constructor stub
		this.typeName = typeName;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Integer.toHexString(hashCode());
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<" + typeName + " instance at " + getName() + ">";
	}

}
