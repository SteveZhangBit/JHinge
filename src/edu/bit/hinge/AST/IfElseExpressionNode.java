package edu.bit.hinge.AST;

public class IfElseExpressionNode extends AST {

	private AST ifExprssion;
	private AST elseExpression;
	private AST condition;
	
	public IfElseExpressionNode(AST ifAst, AST conditionAst, AST elseAst) {
		ifExprssion = ifAst;
		condition = conditionAst;
		elseExpression = elseAst;
	}

	public AST getIfExprssion() {
		return ifExprssion;
	}

	public AST getElseExpression() {
		return elseExpression;
	}

	public AST getCondition() {
		return condition;
	}
}
