package edu.bit.hinge.AST;

public class AttributeNode extends AST {

	private AST identifier;
	private AST attribute;
	
	public AttributeNode(AST identifier, AST attribute) {
		this.identifier = identifier;
		this.attribute = attribute;
	}

	public AST getIdentifier() {
		return identifier;
	}

	public AST getAttribute() {
		return attribute;
	}
	
}
