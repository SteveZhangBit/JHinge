package edu.bit.hinge.AST;

import java.util.List;

public class ParamListNode extends AST {

	private List<AST> parameters;

	public ParamListNode(List<AST> parameters) {
		this.parameters = parameters;
	}
	
	public List<AST> getParameters() {
		return parameters;
	}
}
