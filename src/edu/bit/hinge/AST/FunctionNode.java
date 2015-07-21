package edu.bit.hinge.AST;

public class FunctionNode extends AST {

	private AST identifier;
	private AST parameters;
	private AST body;
	
	public FunctionNode(AST identifier, AST parameters, AST body) {
		super();
		this.identifier = identifier;
		this.parameters = parameters;
		this.body = body;
	}

	public AST getIdentifier() {
		return identifier;
	}


	public AST getParameters() {
		return parameters;
	}


	public AST getBody() {
		return body;
	}
	
}
