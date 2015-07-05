package edu.bit.hinge.AST;

public class DoWhileNode extends AST {

	private AST condition;
	private AST body;
	
	public DoWhileNode(AST condition, AST body) {
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
