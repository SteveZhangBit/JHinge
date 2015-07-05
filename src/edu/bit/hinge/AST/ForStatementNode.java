package edu.bit.hinge.AST;

public class ForStatementNode extends AST {

	private AST iterator;
	private AST range;
	private AST body;
	
	public ForStatementNode(AST iterator, AST range, AST body) {
		this.iterator = iterator;
		this.range = range;
		this.body = body;
	}

	public AST getIterator() {
		return iterator;
	}

	public AST getRange() {
		return range;
	}

	public AST getBody() {
		return body;
	}
}
