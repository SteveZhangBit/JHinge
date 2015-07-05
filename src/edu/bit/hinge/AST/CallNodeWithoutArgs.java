package edu.bit.hinge.AST;

public class CallNodeWithoutArgs extends AST {

	private AST identifier;
	
	public CallNodeWithoutArgs(AST identifier) {
		this.identifier = identifier;
	}

	public AST getIdentifier() {
		return identifier;
	}
	
}
