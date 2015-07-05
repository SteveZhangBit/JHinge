package edu.bit.hinge.AST;

public class IfStatementNode extends AST {

	private AST condition;
	private AST ifbody;
	private AST elsebody;
	
	public IfStatementNode(AST condition, AST ifbody, AST elsebody) {
		this.condition = condition;
		this.ifbody = ifbody;
		this.elsebody = elsebody;
	}

	public AST getCondition() {
		return condition;
	}

	public AST getIfbody() {
		return ifbody;
	}

	public AST getElsebody() {
		return elsebody;
	}
	
}
