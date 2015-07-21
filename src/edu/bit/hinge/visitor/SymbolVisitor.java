package edu.bit.hinge.visitor;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.AssignmentNode;
import edu.bit.hinge.AST.ClassNode;
import edu.bit.hinge.AST.DefParamNode;
import edu.bit.hinge.AST.DoWhileNode;
import edu.bit.hinge.AST.ExpressionListNode;
import edu.bit.hinge.AST.ForStatementNode;
import edu.bit.hinge.AST.FunctionNode;
import edu.bit.hinge.AST.IdentifierNode;
import edu.bit.hinge.AST.IfStatementNode;
import edu.bit.hinge.AST.ParamListNode;
import edu.bit.hinge.AST.StatementListNode;
import edu.bit.hinge.AST.WhileStatementNode;
import edu.bit.hinge.symbol.ClassSymbol;
import edu.bit.hinge.symbol.FunctionSymbol;
import edu.bit.hinge.symbol.LocalScope;
import edu.bit.hinge.symbol.Scope;
import edu.bit.hinge.symbol.Symbol;
import edu.bit.hinge.symbol.VariableSymbol;

public class SymbolVisitor extends Visitor {
	
	private Scope currentScope;

	public SymbolVisitor(Scope currentScope) {
		super();
		this.currentScope = currentScope;
	}

	@Override
	public void visit(AST tree) {
		// TODO Auto-generated method stub
		tree.setScope(currentScope);
		
		if (tree instanceof StatementListNode) {
			for (AST node : ((StatementListNode) tree).getStatementList()) {
				visit(node);
			}
		}
		else if (tree instanceof ClassNode) {
			ClassNode node = (ClassNode) tree;
			IdentifierNode identifier = (IdentifierNode) node.getIdentifier();
			IdentifierNode inheritance = (IdentifierNode) node.getInheritance();
			
			ClassSymbol classSymbol = null;
			if (inheritance != null) {
				ClassSymbol superClass = (ClassSymbol) currentScope.resolve(inheritance.getValue());
				classSymbol = new ClassSymbol(identifier.getValue(), superClass, currentScope);
				
			} else {
				classSymbol = new ClassSymbol(identifier.getValue(), null, currentScope);
			}
			currentScope.define(classSymbol);
			
			currentScope = classSymbol;		// push
			visit(node.getBody());
			currentScope = classSymbol.getEnclosingScope();		// pop
		}
		else if (tree instanceof FunctionNode) {
			FunctionNode node = (FunctionNode) tree;
			IdentifierNode identifier = (IdentifierNode) node.getIdentifier();
			
			FunctionSymbol functionSymbol = new FunctionSymbol(identifier.getValue(), currentScope);
			currentScope.define(functionSymbol);
			
			currentScope = functionSymbol;		// push
			if (node.getParameters() != null) {
				visit(node.getParameters());
			}
			visit(node.getBody());
			
			currentScope = currentScope.getEnclosingScope();	// pop
		}
		else if (tree instanceof ParamListNode) {
			for (AST node : ((ParamListNode) tree).getParameters()) {
				visit(node);
			}
		}
		else if (tree instanceof DefParamNode) {
			DefParamNode node = (DefParamNode) tree;
			IdentifierNode identifier = (IdentifierNode) node.getIdentifier();
			currentScope.define(new VariableSymbol(identifier.getValue()));
		}
		else if (tree instanceof IfStatementNode) {
			IfStatementNode node = (IfStatementNode) tree;
			
			currentScope = new LocalScope(currentScope);	// push
			visit(node.getIfbody());
			currentScope = currentScope.getEnclosingScope();	// pop
			
			if (node.getElsebody() != null) {
				currentScope = new LocalScope(currentScope);	// push
				visit(node.getElsebody());
				currentScope = currentScope.getEnclosingScope();	// pop
			}
		}
		else if (tree instanceof WhileStatementNode) {
			WhileStatementNode node = (WhileStatementNode) tree;
			
			currentScope = new LocalScope(currentScope);	// push
			visit(node.getBody());
			currentScope = currentScope.getEnclosingScope();	// pop
		}
		else if (tree instanceof ForStatementNode) {
			ForStatementNode node = (ForStatementNode) tree;
			
			currentScope = new LocalScope(currentScope);	// push
			visit(node.getIterator());
			visit(node.getBody());
			currentScope = currentScope.getEnclosingScope();	// pop
		}
		else if (tree instanceof DoWhileNode) {
			DoWhileNode node = (DoWhileNode) tree;
			currentScope = new LocalScope(currentScope);	// push
			visit(node.getBody());
			currentScope = currentScope.getEnclosingScope();	// pop
		}
		else if (tree instanceof AssignmentNode) {
			for (AST node : ((AssignmentNode) tree).getTarget()) {
				visit(node);
			}
		}
		else if (tree instanceof ExpressionListNode) {
			for (AST node : ((ExpressionListNode) tree).getExpressionList()) {
				visit(node);
			}
		}
		else if (tree instanceof IdentifierNode) {
			IdentifierNode node = (IdentifierNode) tree;
			currentScope.define(new Symbol(node.getValue()));
		}
	}

}
