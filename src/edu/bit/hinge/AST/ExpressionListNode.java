package edu.bit.hinge.AST;

import java.util.List;

public class ExpressionListNode extends AST {

	private List<AST> expressionList;
	
	public ExpressionListNode(List<AST> expressions) {
		this.expressionList = expressions;
	}
	
	public List<AST> getExpressionList() {
		return expressionList;
	}
}
