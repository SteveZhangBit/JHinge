package edu.bit.hinge.visitor;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.AttributeNode;
import edu.bit.hinge.AST.BinaryExpressionNode;
import edu.bit.hinge.AST.CallNode;
import edu.bit.hinge.AST.CallNodeWithoutArgs;
import edu.bit.hinge.AST.ExpressionListNode;
import edu.bit.hinge.AST.IfElseExpressionNode;
import edu.bit.hinge.AST.IndexingNode;
import edu.bit.hinge.AST.LiteralNode;
import edu.bit.hinge.AST.LongNode;
import edu.bit.hinge.AST.UnaryNode;

public class PrintVisitor extends Visitor {

	@Override
	public void visit(AST tree) {
		// TODO Auto-generated method stub
		if (tree instanceof ExpressionListNode) {
			print((ExpressionListNode)tree);
		}
		else if (tree instanceof IfElseExpressionNode) {
			print((IfElseExpressionNode)tree);
		}
		else if (tree instanceof BinaryExpressionNode) {
			print((BinaryExpressionNode)tree);
		}
		else if (tree instanceof UnaryNode) {
			print((UnaryNode)tree);
		}
		else if (tree instanceof AttributeNode) {
			print((AttributeNode)tree);
		}
		else if (tree instanceof IndexingNode) {
			print((IndexingNode)tree);
		}
		else if (tree instanceof CallNode) {
			print((CallNode)tree);
		}
		else if (tree instanceof CallNodeWithoutArgs) {
			print((CallNodeWithoutArgs)tree);
		}
		else if (tree instanceof LongNode) {
			System.out.print(((LongNode)tree).getValue() + "l");
		}
		else if (tree instanceof LiteralNode<?>) {
			System.out.print(((LiteralNode<?>)tree).getValue());
		}
	}
	
	private void print(ExpressionListNode node) {
		System.out.print('(');
		for (AST child : node.getExpressionList()) {
			visit(child);
			System.out.print(", ");
		}
		System.out.print(')');
	}
	
	private void print(IfElseExpressionNode node) {
		System.out.print('(');
		visit(node.getIfExprssion());
		System.out.print(") if ");
		visit(node.getCondition());
		System.out.print(" else (");
		visit(node.getElseExpression());
		System.out.print(')');
	}
	
	private void print(BinaryExpressionNode node) {
		System.out.print('(');
		visit(node.getLeft());
		System.out.print(" " + node.getOperator() + " ");
		visit(node.getRight());
		System.out.print(')');
	}
	
	private void print(UnaryNode node) {
		System.out.print('(');
		System.out.print(node.getOperator());
		visit(node.getChild());
		System.out.print(')');
	}
	
	private void print(AttributeNode node) {
		visit(node.getIdentifier());
		System.out.print('.');
		visit(node.getAttribute());
	}
	
	private void print(IndexingNode node) {
		visit(node.getIdentifier());
		System.out.print('[');
		visit(node.getIndexing());
		System.out.print(']');
	}
	
	private void print(CallNode node) {
		visit(node.getIdentifier());
		System.out.print('(');
		visit(node.getArguments());
		System.out.print(')');
	}
	
	private void print(CallNodeWithoutArgs node) {
		visit(node.getIdentifier());
		System.out.print('(');
		System.out.print(')');
	}
	
}
