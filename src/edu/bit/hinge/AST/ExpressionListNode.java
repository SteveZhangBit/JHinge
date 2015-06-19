package edu.bit.hinge.AST;

import java.util.LinkedList;
import java.util.List;

public class ExpressionListNode extends AST {

	private List<AST> expressionList = new LinkedList<AST>();
	
	public void addChild(AST node) {
		expressionList.add(0, node);
	}
	
	public List<AST> getExpressionList() {
		return expressionList;
	}
}
