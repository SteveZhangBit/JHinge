package edu.bit.hinge.visitor;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.AssignmentNode;
import edu.bit.hinge.AST.AttributeNode;
import edu.bit.hinge.AST.AugmentedAssignNode;
import edu.bit.hinge.AST.BinaryExpressionNode;
import edu.bit.hinge.AST.BreakNode;
import edu.bit.hinge.AST.CallNode;
import edu.bit.hinge.AST.ClassNode;
import edu.bit.hinge.AST.ContinueNode;
import edu.bit.hinge.AST.DefParamNode;
import edu.bit.hinge.AST.DoWhileNode;
import edu.bit.hinge.AST.ExpressionListNode;
import edu.bit.hinge.AST.ForStatementNode;
import edu.bit.hinge.AST.FunctionNode;
import edu.bit.hinge.AST.IfElseExpressionNode;
import edu.bit.hinge.AST.IfStatementNode;
import edu.bit.hinge.AST.IndexingNode;
import edu.bit.hinge.AST.LiteralNode;
import edu.bit.hinge.AST.LongNode;
import edu.bit.hinge.AST.ParamListNode;
import edu.bit.hinge.AST.ReturnNode;
import edu.bit.hinge.AST.StatementListNode;
import edu.bit.hinge.AST.UnaryNode;
import edu.bit.hinge.AST.WhileStatementNode;

public class PrintVisitor extends Visitor {

	@Override
	public void visit(AST tree) {
		// TODO Auto-generated method stub
		if (tree instanceof StatementListNode) {
			for (AST stmt : ((StatementListNode)tree).getStatementList()) {
				visit(stmt);
				System.out.print('\n');
			}
		}
		else if (tree instanceof ClassNode) {
			ClassNode node = (ClassNode) tree;
			System.out.print("class ");
			visit(node.getIdentifier());
			if (node.getInheritance() != null) {
				System.out.print(" : ");
				visit(node.getInheritance());
			}
			System.out.println(" {");
			visit(node.getBody());
			System.out.print("}");
		}
		else if (tree instanceof FunctionNode) {
			print((FunctionNode)tree);
		}
		else if (tree instanceof ParamListNode) {
			for (AST param : ((ParamListNode)tree).getParameters()) {
				visit(param);
				System.out.print(", ");
			}
		}
		else if (tree instanceof DefParamNode) {
			print((DefParamNode)tree);
		}
		else if (tree instanceof IfStatementNode) {
			print((IfStatementNode)tree);
		}
		else if (tree instanceof WhileStatementNode) {
			print((WhileStatementNode)tree);
		}
		else if (tree instanceof ForStatementNode) {
			print((ForStatementNode)tree);
		}
		else if (tree instanceof DoWhileNode) {
			print((DoWhileNode)tree);
		}
		else if (tree instanceof AssignmentNode) {
			print((AssignmentNode)tree);
		}
		else if (tree instanceof AugmentedAssignNode) {
			print((AugmentedAssignNode)tree);
		}
		else if (tree instanceof ReturnNode) {
			print((ReturnNode)tree);
		}
		else if (tree instanceof ContinueNode) {
			System.out.println("continue");
		}
		else if (tree instanceof BreakNode) {
			System.out.println("break");
		}
		else if (tree instanceof ExpressionListNode) {
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
		else if (tree instanceof LongNode) {
			System.out.print(((LongNode)tree).getValue() + "l");
		}
		else if (tree instanceof LiteralNode<?>) {
			System.out.print(((LiteralNode<?>)tree).getValue());
		}
		else {
			new Error("Unknown AST node.");
		}
	}
	
	private void print(FunctionNode node) {
		System.out.print("function ");
		visit(node.getIdentifier());
		System.out.print("(");
		if (node.getParameters() != null) {
			visit(node.getParameters());
		}
		System.out.println(") {");
		visit(node.getBody());
		System.out.print("}");
	}
	
	private void print(DefParamNode node) {
		visit(node.getIdentifier());
		System.out.print("=");
		if (node.getDefValue() != null) {
			visit(node.getDefValue());
		}
	}
	
	private void print(IfStatementNode node) {
		System.out.print("if ");
		visit(node.getCondition());
		System.out.println(" {");
		visit(node.getIfbody());
		System.out.print("}");
		if (node.getElsebody() != null) {
			System.out.println("else {");
			visit(node.getElsebody());
			System.out.print("}");
		}
	}
	
	private void print(WhileStatementNode node) {
		System.out.print("while ");
		visit(node.getCondition());
		System.out.println(" {");
		visit(node.getBody());
		System.out.print("}");
	}
	
	private void print(ForStatementNode node) {
		System.out.print("for ");
		visit(node.getIterator());
		System.out.print(" in ");
		visit(node.getRange());
		System.out.println(" {");
		visit(node.getBody());
		System.out.print("}");
	}
	
	private void print(DoWhileNode node) {
		System.out.println("do {");
		visit(node.getBody());
		System.out.print("} while ");
		visit(node.getCondition());
		System.out.print("\n");
	}
	
	private void print(AssignmentNode node) {
		for (AST target : node.getTarget()) {
			visit(target);
			System.out.print(" = ");
		}
		visit(node.getExpression());
	}
	
	private void print(AugmentedAssignNode node) {
		visit(node.getTarget());
		System.out.print(' ' + node.getOperator() + ' ');
		visit(node.getExpression());
	}
	
	private void print(ReturnNode node) {
		if (node.getExpressionList() == null) {
			System.out.print("return");
		} else {
			System.out.print("return ");
			visit(node.getExpressionList());
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
		if (node.getArguments() != null) {
			visit(node.getArguments());
		}
		System.out.print(')');
	}
	
}
