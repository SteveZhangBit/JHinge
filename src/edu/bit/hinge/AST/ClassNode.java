package edu.bit.hinge.AST;

public class ClassNode extends AST {

	private AST identifier;
	private AST inheritance;
	private AST body;
	
	public ClassNode(AST identifier, AST inheritance, AST body) {
		super();
		this.identifier = identifier;
		this.inheritance = inheritance;
		this.body = body;
	}
	
	public AST getIdentifier() {
		return identifier;
	}
	
	public AST getInheritance() {
		return inheritance;
	}
	
	public AST getBody() {
		return body;
	}
	
}
