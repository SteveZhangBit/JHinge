package edu.bit.hinge.AST;

public class WhileStatementNode extends AST {

	private AST condition;
	private AST body;
	
	public WhileStatementNode(AST condition, AST body) {
		this.condition = condition;
		this.body = body;
	}

	public AST getCondition() {
		return condition;
	}

	public AST getBody() {
		return body;
	}
	
}
