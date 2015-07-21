package edu.bit.hinge.AST;

public class AugmentedAssignNode extends AST {

	private AST target;
	private String operator;
	private AST expression;
	
	public AugmentedAssignNode(AST target, String operator, AST expression) {
		this.target = target;
		this.expression = expression;
		this.operator = operator;
	}
	
	public AST getTarget() {
		return target;
	}

	public AST getExpression() {
		return expression;
	}

	public String getOperator() {
		return operator;
	}
}
