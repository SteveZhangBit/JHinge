package edu.bit.hinge.AST;

public class AssignmentNode extends AST {

	private AST target;
	private AST expression;
	
	public AssignmentNode(AST target, AST expression) {
		this.target = target;
		this.expression = expression;
	}

	public AST getTarget() {
		return target;
	}

	public AST getExpression() {
		return expression;
	}
}
