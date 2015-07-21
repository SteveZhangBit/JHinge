package edu.bit.hinge.AST;

import edu.bit.hinge.symbol.Scope;
import edu.bit.hinge.visitor.Visitor;

public abstract class AST {
	
	private Scope scope;

	public void visit(Visitor visitor) {
		visitor.visit(this);
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}
}
