package edu.bit.hinge.AST.builder;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.ArithmaticNode;
import edu.bit.hinge.AST.AssignmentNode;
import edu.bit.hinge.AST.AttributeNode;
import edu.bit.hinge.AST.AugmentedAssignNode;
import edu.bit.hinge.AST.BoolNode;
import edu.bit.hinge.AST.BreakNode;
import edu.bit.hinge.AST.CallNode;
import edu.bit.hinge.AST.ClassNode;
import edu.bit.hinge.AST.ComparisonNode;
import edu.bit.hinge.AST.ContinueNode;
import edu.bit.hinge.AST.DefParamNode;
import edu.bit.hinge.AST.DoWhileNode;
import edu.bit.hinge.AST.DoubleNode;
import edu.bit.hinge.AST.ExpressionListNode;
import edu.bit.hinge.AST.ForStatementNode;
import edu.bit.hinge.AST.FunctionNode;
import edu.bit.hinge.AST.IdentifierNode;
import edu.bit.hinge.AST.IfElseExpressionNode;
import edu.bit.hinge.AST.IfStatementNode;
import edu.bit.hinge.AST.IndexingNode;
import edu.bit.hinge.AST.IntNode;
import edu.bit.hinge.AST.LogicalNode;
import edu.bit.hinge.AST.LongNode;
import edu.bit.hinge.AST.ParamListNode;
import edu.bit.hinge.AST.ReturnNode;
import edu.bit.hinge.AST.StatementListNode;
import edu.bit.hinge.AST.StringNode;
import edu.bit.hinge.AST.UnaryNode;
import edu.bit.hinge.AST.WhileStatementNode;

public class ASTBuilder {

	private Deque<AST> stack = new LinkedList<AST>();
	
	public AST getAST() {
		return stack.element();
	}
	
	public void statementListNode(int size) {
		List<AST> statements = new LinkedList<AST>();
		for (int i = 0; i < size; i++) {
			statements.add(0, stack.pop());
		}
		stack.push(new StatementListNode(statements));
	}
	
	public void classNode(boolean withInherit) {
		AST body = stack.pop();
		AST inheritance = null;
		if (withInherit) {
			inheritance = stack.pop();
		}
		AST identifier = stack.pop();
		stack.push(new ClassNode(identifier, inheritance, body));
	}
	
	public void functionNode(boolean withParam) {
		AST body = stack.pop();
		AST parameters = null;
		if (withParam) {
			parameters = stack.pop();
		}
		AST identifier = stack.pop();
		stack.push(new FunctionNode(identifier, parameters, body));
	}
	
	public void parameterListNode(int size) {
		List<AST> parameters = new LinkedList<AST>();
		for (int i = 0; i < size; i++) {
			parameters.add(0, stack.pop());
		}
		stack.push(new ParamListNode(parameters));
	}
	
	public void defParameterNode(boolean withDef) {
		AST defValue = null;
		if (withDef) {
			defValue = stack.pop();
		}
		AST identifier = stack.pop();
		stack.push(new DefParamNode(identifier, defValue));
	}
	
	public void forStatementNode() {
		AST body = stack.pop();
		AST range = stack.pop();
		AST iterator = stack.pop();
		stack.push(new ForStatementNode(iterator, range, body));
	}
	
	public void doWhileNode() {
		AST condition = stack.pop();
		AST body = stack.pop();
		stack.push(new DoWhileNode(condition, body));
	}
	
	public void whileStatementNode() {
		AST body = stack.pop();
		AST condition = stack.pop();
		stack.push(new WhileStatementNode(condition, body));
	}
	
	public void ifStatementNode(boolean withElseBody) {
		AST elsebody = null;
		if (withElseBody) {
			elsebody = stack.pop();
		}
		AST ifbody = stack.pop();
		AST condition = stack.pop();
		stack.push(new IfStatementNode(condition, ifbody, elsebody));
	}
	
	public void augmentedAssignNode(String operator) {
		AST expression = stack.pop();
		AST target = stack.pop();
		stack.push(new AugmentedAssignNode(target, operator, expression));
	}
	
	public void assignmentNode(int size) {
		AST expression = stack.pop();
		List<AST> targets = new LinkedList<AST>();
		for (int i = 0; i < size; i++) {
			targets.add(0, stack.pop());
		}
		stack.push(new AssignmentNode(targets, expression));
	}
	
	public void breakNode() {
		stack.push(new BreakNode());
	}
	
	public void continueNode() {
		stack.push(new ContinueNode());
	}
	
	public void returnNode(boolean withExpr) {
		if (withExpr) {
			stack.push(new ReturnNode(stack.pop()));
		} else {
			stack.push(new ReturnNode());
		}
	}
	
	public void expressionListNode(int size) {
		List<AST> expressions = new LinkedList<AST>();
		for (int i = 0; i < size; i++) {
			expressions.add(0, stack.pop());
		}
		stack.push(new ExpressionListNode(expressions));
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
		stack.push(new CallNode(identifier));
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
		char[] chars = value.toCharArray();
		if (chars[0] == '0' && chars.length > 1) {
			// Hex
			if (chars[1] == 'x' || chars[1] == 'X') {
				stack.push(new LongNode(Long.parseLong(value.substring(2), 16)));
			}
			// binary
			else if (chars[1] == 'b' || chars[1] == 'B') {
				stack.push(new LongNode(Long.parseLong(value.substring(2), 2)));
			}
			// octal
			else {
				stack.push(new LongNode(Long.parseLong(value.substring(1), 8)));
			}
		}
		else {
			stack.push(new LongNode(Long.parseLong(value)));
		}
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
