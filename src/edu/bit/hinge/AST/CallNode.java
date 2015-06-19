package edu.bit.hinge.AST;

public class CallNode extends AST {

	private AST identifier;
	private AST arguments;
	
	public CallNode(AST identifier, AST arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	}

	public AST getIdentifier() {
		return identifier;
	}

	public AST getArguments() {
		return arguments;
	}
}
