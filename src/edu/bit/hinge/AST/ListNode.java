package edu.bit.hinge.AST;

import java.util.List;

public class ListNode extends AST {

	private List<AST> list;

	public ListNode(List<AST> list) {
		super();
		this.list = list;
	}

	public List<AST> getList() {
		return list;
	}
	
}
