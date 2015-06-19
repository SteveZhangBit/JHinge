package edu.bit.hinge.AST;

public class UnaryNode extends AST {

	private String operator;
	private AST child;
	
	public UnaryNode(String operator, AST child) {
		this.operator = operator;
		this.child = child;
	}

	public AST getChild() {
		return child;
	}

	public String getOperator() {
		return operator;
	}
	
}
