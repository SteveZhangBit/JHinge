package edu.bit.hinge.AST;

public class DefParamNode extends AST {
	
	private AST identifier;
	private AST defValue;
	
	public DefParamNode(AST identifier, AST defValue) {
		this.identifier = identifier;
		this.defValue = defValue;
	}

	public AST getIdentifier() {
		return identifier;
	}

	public AST getDefValue() {
		return defValue;
	}

}
