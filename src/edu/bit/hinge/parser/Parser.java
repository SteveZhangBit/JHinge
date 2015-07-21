package edu.bit.hinge.parser;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.builder.ASTBuilder;
import edu.bit.hinge.lexer.Lexer;
import edu.bit.hinge.lexer.Token;
import edu.bit.hinge.lexer.Token.TokenType;

public class Parser {

	private Lexer input;
	private Token lookahead;
	private int lineCount = 1;
	private int colTokenCount = 0;
	
	private ASTBuilder treeBuilder = new ASTBuilder();

	public Parser(Lexer input) {
		this.input = input;
		this.lookahead = input.nextToken();
	}
	
	public AST getAST() {
		return treeBuilder.getAST();
	}

	public void entry() {
		statement_list();
	}

	public void statement_list() {
		int size = 0;
		while (!isToken(TokenType.EOF) && !isToken("}")) {
			if (isToken(TokenType.Newline)) {
				match(TokenType.Newline);
			} else {
				statement(); size++;
			}
		}
		treeBuilder.statementListNode(size);
	}

	public void statement() {
		switch (lookahead.value()) {
		case "if":
			if_stmt();
			break;

		case "while":
			while_stmt();
			break;

		case "for":
			for_stmt();
			break;

		case "do":
			do_while_stmt();
			break;

		case "func":
			funcdef();
			break;

		case "class":
			classdef();
			break;

		default:
			simple_stmt(); 
			match(TokenType.Newline);
			break;
		}
	}

	public void classdef() {
		match("class"); treeBuilder.identifierNode(lookahead.value()); match(TokenType.Identifier);
		if (isToken(":")) {
			match(":"); expression_list(); suite(); treeBuilder.classNode(true);
		} else {
			suite(); treeBuilder.classNode(false);
		}
	}

	public void funcdef() {
		match("func"); treeBuilder.identifierNode(lookahead.value()); match(TokenType.Identifier);
		match("(");
		if (isToken(TokenType.Identifier)) {
			parameter_list(); match(")"); suite(); treeBuilder.functionNode(true);
		} else {
			match(")"); suite(); treeBuilder.functionNode(false);
		}
	}

	public void parameter_list() {
		int size = 1;
		defparameter();
		while (isToken(",")) {
			match(","); defparameter(); size++;
		}
		treeBuilder.parameterListNode(size);
	}

	public void defparameter() {
		treeBuilder.identifierNode(lookahead.value()); match(TokenType.Identifier);
		if (isToken("=")) {
			match("="); expression(); treeBuilder.defParameterNode(true);
		} else {
			treeBuilder.defParameterNode(false);
		}
	}

	public void do_while_stmt() {
		match("do"); suite(); match("while"); expression(); match(TokenType.Newline);
		treeBuilder.doWhileNode();
	}

	public void for_stmt() {
		match("for"); primary(); match("in"); expression_list(); suite();
		treeBuilder.forStatementNode();
	}

	public void while_stmt() {
		match("while"); expression(); suite(); treeBuilder.whileStatementNode();
	}

	public void if_stmt() {
		match("if"); expression(); suite();
		if (isToken("elif") || isToken("else")) {
			elif_stmt(); treeBuilder.ifStatementNode(true);
		} else {
			treeBuilder.ifStatementNode(false);
		}
	}

	public void elif_stmt() {
		if (isToken("elif")) {
			match("elif"); expression(); suite();
			if (isToken("elif") || isToken("else")) {
				elif_stmt(); treeBuilder.ifStatementNode(true);
			} else {
				treeBuilder.ifStatementNode(false);
			}
		} else {
			else_stmt();
		}
	}

	public void else_stmt() {
		if (isToken("else")) {
			match("else"); suite();
		}
	}

	public void suite() {
		if (isToken(TokenType.Newline)) {
			match(TokenType.Newline);
		}
		match("{"); statement_list(); match("}");
	}

	public void simple_stmt() {
		switch (lookahead.value()) {
		case "return":
			return_stmt();
			break;

		case "break":
			break_stmt();
			break;

		case "continue":
			continue_stmt();
			break;

		default:
			expression_stmt();
			break;
		}
	}

	public void expression_stmt() {
		expression_list();
		switch (lookahead.value()) {
		case "=":
			assignment_stmt();
			break;

		case "+=":case "-=":case "*=":case "/=":case "%=":case "**=":
		case ">>=":case "<<=":case "&=":case "|=":case "^=":
			String op = lookahead.value();
			match(lookahead.value()); expression_list(); treeBuilder.augmentedAssignNode(op);
			break;

		default:
			break;
		}
	}

	public void assignment_stmt() {
		int size = 0;
		do {
			match("="); expression_list(); size++;
		} while(isToken("="));
		treeBuilder.assignmentNode(size);
	}

	public void continue_stmt() {
		match("continue"); treeBuilder.continueNode();
	}

	public void break_stmt() {
		match("break"); treeBuilder.breakNode();
	}

	public void return_stmt() {
		match("return");
		if (!isToken(TokenType.Newline)) {
			expression_list(); treeBuilder.returnNode(true);
		} else {
			treeBuilder.returnNode(false);
		}
	}

	public void expression_list() {
		expression();
		int size = 1;
		while (isToken(",")) {
			match(","); expression(); size++;
		}
		if (size > 1) {
			treeBuilder.expressionListNode(size);
		}
	}

	public void expression() {
		or_test();
		if (isToken("if")) {
			match("if"); or_test(); match("else"); expression(); treeBuilder.ifelseExpression();
		}
	}

	public void or_test() {
		and_test(); or_test_();
	}

	public void or_test_() {
		if (isToken("or")) {
			match("or"); and_test(); treeBuilder.logicalNode("or");
			or_test_();
		}
	}

	public void and_test() {
		not_test(); and_test_();
	}

	public void and_test_() {
		if (isToken("and")) {
			match("and"); not_test(); treeBuilder.logicalNode("and");
			and_test_();
		}
	}

	public void not_test() {
		if (isToken("not")) {
			match("not"); not_test(); treeBuilder.unaryNode("not");
		} else {
			comparison();
		}
	}

	public void comparison() {
		or_expr();
		switch (lookahead.value()) {
		case "<":case ">":case "==":case ">=":case "<=":case "!=":
			String op = lookahead.value();
			match(lookahead.value()); or_expr(); treeBuilder.comparisonNode(op);
			break;

		case "is":
			match("is");
			if (isToken("not")) {
				match("not"); or_expr(); treeBuilder.comparisonNode("is not");
			} else {
				or_expr(); treeBuilder.comparisonNode("is");
			}
			break;

		case "not":
			match("not"); match("in"); or_expr(); treeBuilder.comparisonNode("not in");
			break;

		case "in":
			match("in"); or_expr(); treeBuilder.comparisonNode("in");
			break;

		default:
			break;
		}
	}

	public void or_expr() {
		xor_expr(); or_expr_();
	}

	public void or_expr_() {
		if (isToken("|")) {
			match("|"); xor_expr(); treeBuilder.binaryNode("|");
			or_expr_();
		}
	}

	public void xor_expr() {
		and_expr(); xor_expr_();
	}

	public void xor_expr_() {
		if (isToken("^")) {
			match("^"); and_expr(); treeBuilder.binaryNode("^");
			xor_expr_();
		}
	}

	public void and_expr() {
		shift_expr(); and_expr_();
	}

	public void and_expr_() {
		if (isToken("&")) {
			match("&"); shift_expr(); treeBuilder.binaryNode("&");
			and_expr_();
		}
	}

	public void shift_expr() {
		a_expr(); shift_expr_();
	}

	public void shift_expr_() {
		if (isToken("<<")) {
			match("<<"); a_expr(); treeBuilder.binaryNode("<<");
			shift_expr_();
		} else if (isToken(">>")) {
			match(">>"); a_expr(); treeBuilder.binaryNode(">>");
			shift_expr_();
		}
	}

	public void a_expr() {
		m_expr(); a_expr_();
	}

	public void a_expr_() {
		if (isToken("+")) {
			match("+"); m_expr(); treeBuilder.binaryNode("+");
			a_expr_();
		} else if (isToken("-")) {
			match("-"); m_expr(); treeBuilder.binaryNode("-");
			a_expr_();
		}
	}

	public void m_expr() {
		u_expr(); m_expr_();
	}

	public void m_expr_() {
		if (isToken("*")) {
			match("*"); u_expr(); treeBuilder.binaryNode("*");
			m_expr_();
		} else if (isToken("/")) {
			match("/"); u_expr(); treeBuilder.binaryNode("/");
			m_expr_();
		} else if (isToken("%")) {
			match("%"); u_expr(); treeBuilder.binaryNode("%");
			m_expr_();
		}
	}

	public void u_expr() {
		if (isToken("-")) {
			match("-"); u_expr(); treeBuilder.unaryNode("-");
		} else if (isToken("+")) {
			match("+"); u_expr(); treeBuilder.unaryNode("+");
		} else if (isToken("~")) {
			match("~"); u_expr(); treeBuilder.unaryNode("~");
		} else {
			power();
		}
	}

	public void power() {
		primary();
		if (isToken("**")) {
			match("**"); u_expr(); treeBuilder.binaryNode("**");
		}
	}

	public void primary() {
		atom(); primary_();
	}

	public void primary_() {
		switch (lookahead.value()) {
		case ".":
			match(".");
			treeBuilder.identifierNode(lookahead.value());
			match(TokenType.Identifier);
			treeBuilder.attributeNode();
			primary_();
			break;

		case "[":
			match("["); expression_list(); match("]"); treeBuilder.indexingNode();
			primary_();
			break;

		case "(":
			match("(");
			if (!isToken(")")) {
				expression_list(); treeBuilder.callNode();
			} else {
				treeBuilder.callNodeWithoutArgs();
			}
			match(")"); primary_();
			break;

		default:
			break;
		}
 	}

 	public void atom() {
 		if (isToken(TokenType.Identifier)) {
 			treeBuilder.identifierNode(lookahead.value());
 			match(TokenType.Identifier);
 		} else if (isToken("(")) {
 			enclosure();
 		} else {
 			literal();
 		}
 	}

 	public void enclosure() {
 		match("("); expression_list(); match(")");
 	}

 	public void literal() {
 		switch (lookahead.type()) {
 		case String:
 			treeBuilder.stringNode(lookahead.value());
 			match(TokenType.String);
 			break;

 		case Integer:
 			treeBuilder.intNode(lookahead.value());
 			match(TokenType.Integer);
 			break;

 		case LongInteger:
 			treeBuilder.longNode(lookahead.value());
 			match(TokenType.LongInteger);
 			break;

 		case Float:
 			treeBuilder.doubleNode(lookahead.value());
 			match(TokenType.Float);
 			break;

 		default:
 			if (isToken("true")) {
 				match("true");
 				treeBuilder.boolNode(true);
 			} else if (isToken("false")) {
 				match("false");
 				treeBuilder.boolNode(false);
 			} else if (isToken("self")) {
 				match("self");
 				treeBuilder.identifierNode("self");
 			} else {
 				error();
 			}
 			break;
 		}
 	}

	private boolean isToken(TokenType type) {
		return lookahead.type().equals(type);
	}

	private boolean isToken(String value) {
		return lookahead.value().equals(value);
	}

	private void error() {
		throw new Error("Error in line : " + lineCount + " the "
			+ colTokenCount + " token. Unexpected " + lookahead);
	}

	private void match(String value) {
		if (lookahead.value().equals(value)) {
			consume();
		} else {
			throw new Error("Error in line : " + lineCount + " the "
				+ colTokenCount + " token. Expecting '"
				+ value + "'; found '" + lookahead.value() + "'");
		}
	}

	private void match(TokenType type) {
		if (lookahead.type().equals(type)) {
			consume();
		} else {
			throw new Error("Error in line : " + lineCount + " the "
				+ colTokenCount + " token. Expecting '"
				+ type.name() + "'; found '" + lookahead.type().name()
				+ "'");
		}
	}

	private void consume() {
		if (lookahead.type().equals(TokenType.Newline)) {
			lineCount++;
			colTokenCount = 0;
		}
		colTokenCount++;
		lookahead = input.nextToken();
	}
}