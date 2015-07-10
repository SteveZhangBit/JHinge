package edu.bit.hinge.AST.builder;

import java.util.Deque;
import java.util.LinkedList;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.ArithmaticNode;
import edu.bit.hinge.AST.AttributeNode;
import edu.bit.hinge.AST.BoolNode;
import edu.bit.hinge.AST.CallNode;
import edu.bit.hinge.AST.CallNodeWithoutArgs;
import edu.bit.hinge.AST.ComparisonNode;
import edu.bit.hinge.AST.DoubleNode;
import edu.bit.hinge.AST.ExpressionListNode;
import edu.bit.hinge.AST.IdentifierNode;
import edu.bit.hinge.AST.IfElseExpressionNode;
import edu.bit.hinge.AST.IndexingNode;
import edu.bit.hinge.AST.IntNode;
import edu.bit.hinge.AST.LogicalNode;
import edu.bit.hinge.AST.LongNode;
import edu.bit.hinge.AST.StringNode;
import edu.bit.hinge.AST.UnaryNode;

public class LL1ASTBuilder {

	private Deque<AST> stack = new LinkedList<AST>();
	
	public AST getAST() {
		return stack.element();
	}
	
	public void expressionListNode(int size) {
		ExpressionListNode node = new ExpressionListNode();
		for (int i = 0; i < size; i++) {
			node.addChild(stack.pop());
		}
		stack.push(node);
	}
	
	public void ifelseExpression() {
		AST elseAst = stack.pop();
		AST conditionAst = stack.pop();
		AST ifAst = stack.pop();
		stack.push(new IfElseExpressionNode(ifAst, conditionAst, elseAst));
	}
	
	public void logicalNode(String operator) {
		AST right = stack.pop();
		AST left = stack.pop();
		stack.push(new LogicalNode(left, operator, right));
	}
	
	public void comparisonNode(String operator) {
		AST right = stack.pop();
		AST left = stack.pop();
		stack.push(new ComparisonNode(left, operator, right));
	}
	
	public void binaryNode(String operator) {
		AST right = stack.pop();
		AST left = stack.pop();
		stack.push(new ArithmaticNode(left, operator, right));
	}
	
	public void unaryNode(String operator) {
		AST child = stack.pop();
		stack.push(new UnaryNode(operator, child));
	}
	
	public void attributeNode() {
		AST attribute = stack.pop();
		AST identifier = stack.pop();
		stack.push(new AttributeNode(identifier, attribute));
	}
	
	public void indexingNode() {
		AST indexing = stack.pop();
		AST identifier = stack.pop();
		stack.push(new IndexingNode(identifier, indexing));
	}
	
	public void callNode() {
		AST arguments = stack.pop();
		AST identifier = stack.pop();
		stack.push(new CallNode(identifier, arguments));
	}
	
	public void callNodeWithoutArgs() {
		AST identifier = stack.pop();
		stack.push(new CallNodeWithoutArgs(identifier));
	}
	
	public void intNode(String value) {
		char[] chars = value.toCharArray();
		if (chars[0] == '0' && chars.length > 1) {
			// Hex
			if (chars[1] == 'x' || chars[1] == 'X') {
				stack.push(new IntNode(Integer.parseInt(value.substring(2), 16)));
			}
			// binary
			else if (chars[1] == 'b' || chars[1] == 'B') {
				stack.push(new IntNode(Integer.parseInt(value.substring(2), 2)));
			}
			// octal
			else {
				stack.push(new IntNode(Integer.parseInt(value.substring(1), 8)));
			}
		}
		else {
			stack.push(new IntNode(Integer.parseInt(value)));
		}
	}
	
	public void longNode(String value) {
		value = value.substring(0, value.length() - 1);
		stack.push(new LongNode(Long.parseLong(value)));
	}
	
	public void doubleNode(String value) {
		stack.push(new DoubleNode(Double.parseDouble(value)));
	}
	
	public void stringNode(String value) {
		stack.push(new StringNode(value));
	}
	
	public void identifierNode(String value) {
		stack.push(new IdentifierNode(value));
	}
	
	public void boolNode(boolean value) {
		stack.push(new BoolNode(value));
	}
	
}
