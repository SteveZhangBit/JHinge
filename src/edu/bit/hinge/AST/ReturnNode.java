package edu.bit.hinge.AST;


public class ReturnNode extends AST {

	private AST expressionList;
	
	public ReturnNode() {
		expressionList = null;
	}
	
	public ReturnNode(AST expressionList) {
		this.expressionList = expressionList;
	}

	public AST getExpressionList() {
		return expressionList;
	}
}
