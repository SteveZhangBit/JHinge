package edu.bit.hinge.AST;

public class IndexingNode extends AST {

	private AST identifier;
	private AST indexing;
	
	public IndexingNode(AST identifier, AST indexing) {
		this.identifier = identifier;
		this.indexing = indexing;
	}

	public AST getIdentifier() {
		return identifier;
	}

	public AST getIndexing() {
		return indexing;
	}
	
}
