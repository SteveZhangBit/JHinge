package edu.bit.hinge.memery;

public class ReturnValue extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5894651675789409611L;

	private Object value;

	public ReturnValue(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
	
}
