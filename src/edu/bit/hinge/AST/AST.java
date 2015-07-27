package edu.bit.hinge.AST;

import edu.bit.hinge.visitor.Visitor;

public abstract class AST {

	public void visit(Visitor visitor) {
		visitor.visit(this);
	}

}
