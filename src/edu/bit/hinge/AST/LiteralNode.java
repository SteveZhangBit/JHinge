package edu.bit.hinge.AST;

public class LiteralNode<T> extends AST {

	protected T value;
	
	public LiteralNode(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}
