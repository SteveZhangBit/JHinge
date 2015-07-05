package edu.bit.hinge.AST;

import java.util.List;

public class StatementListNode extends AST {

	private List<AST> statementList;

	public StatementListNode(List<AST> statementList) {
		this.statementList = statementList;
	}

	public List<AST> getStatementList() {
		return statementList;
	}
}
