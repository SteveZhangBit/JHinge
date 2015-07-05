package edu.bit.hinge.AST;

public class AugmentedAssignNode extends AssignmentNode {

	private String operator;
	
	public AugmentedAssignNode(AST target, String operator, AST expression) {
		super(target, expression);
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}
}
