package edu.bit.hinge.AST;

public abstract class BinaryExpressionNode extends AST {

	private AST left;
	private AST right;
	private String operator;
	
	public BinaryExpressionNode(AST left, String operator, AST right) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	public AST getLeft() {
		return left;
	}

	public AST getRight() {
		return right;
	}

	public String getOperator() {
		return operator;
	}
	
}
