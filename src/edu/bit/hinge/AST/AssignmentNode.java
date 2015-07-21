package edu.bit.hinge.AST;

import java.util.List;

public class AssignmentNode extends AST {

	private List<AST> target;
	private AST expression;
	
	public AssignmentNode(List<AST> target, AST expression) {
		this.target = target;
		this.expression = expression;
	}

	public List<AST> getTarget() {
		return target;
	}

	public AST getExpression() {
		return expression;
	}
}
