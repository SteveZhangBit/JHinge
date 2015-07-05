package edu.bit.hinge.AST;

public class BoolNode extends LiteralNode<Integer> {

	public BoolNode(boolean value) {
		super(0);
		this.value = (value) ? 1 : 0;
	}

}
