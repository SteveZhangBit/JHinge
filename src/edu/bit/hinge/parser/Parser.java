/**
 * 
 */
package edu.bit.hinge.parser;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.builder.LL1ASTBuilder;
import edu.bit.hinge.lexer.Lexer;
import edu.bit.hinge.lexer.Token;
import edu.bit.hinge.lexer.Token.TokenType;

/**
 * @author zhang
 *
 */
public class Parser {

	private Lexer input;
	private Token lookahead;
	private int lineCount = 1;

	private LL1ASTBuilder treeBuilder = new LL1ASTBuilder();

	public Parser(Lexer input) {
		this.input = input;
		this.lookahead = input.nextToken();
	}

	public AST getAST() {
		return treeBuilder.getAST();
	}

	public void fileInput() {
		while (!lookahead.type().equals(TokenType.EOF)) {
			if (lookahead.type().equals(TokenType.Newline)) {
				match(TokenType.Newline);
			} else {
				statement();
			}
		}
	}

	public void statement() {
		if (lookahead.value().equals("if")) {
			ifStmt();
		} else if (lookahead.value().equals("while")) {
			whileStmt();
		} else if (lookahead.value().equals("for")) {
			forStmt();
		} else if (lookahead.value().equals("do")) {
			doWhileStmt();
		} else if (lookahead.value().equals("func")) {
			funcDef();
		} else if (lookahead.value().equals("class")) {
			classDef();
		} else {
			simpleStmt();
			match(TokenType.Newline);
		}
	}

	public void suite() {
		if (lookahead.type().equals(TokenType.Newline)) {
			match(TokenType.Newline);
		}
		if (lookahead.value().equals("{")) {
			match("{");
			while (!lookahead.value().equals("}")) {
				if (lookahead.type().equals(TokenType.Newline)) {
					match(TokenType.Newline);
				} else {
					statement();
				}
			}
			match("}");
		} else {
			statement();
		}
	}

	public void braceSuite() {
		if (lookahead.type().equals(TokenType.Newline)) {
			match(TokenType.Newline);
		}
		match("{");
		while (!lookahead.value().equals("}")) {
			if (lookahead.type().equals(TokenType.Newline)) {
				match(TokenType.Newline);
			} else {
				statement();
			}
		}
		match("}");
	}

	public void ifStmt() {
		match("if");
		expression();
		suite();

		while (lookahead.value().equals("else")) {
			match("else");
			if (lookahead.value().equals("if")) {
				match("if");
				expression();
				suite();
			} else {
				suite();
				break;
			}
		}
	}

	public void whileStmt() {
		match("while");
		expression();
		suite();
	}

	public void forStmt() {
		match("for");
		primary();
		match("in");
		expressionList();
		suite();
	}

	public void doWhileStmt() {
		match("do");
		braceSuite();
		match("while");
		match(TokenType.Newline);
	}

	public void funcDef() {
		match("func");
		match(TokenType.Identifier);
		match("(");
		if (!lookahead.value().equals(")")) {
			parameterList();
		}
		match(")");
		braceSuite();
	}

	public void parameterList() {
		defParameter();
		while (lookahead.value().equals(",")) {
			match(",");
			defParameter();
		}
	}

	public void defParameter() {
		match(TokenType.Identifier);
		if (lookahead.value().equals("=")) {
			match("=");
			expression();
		}
	}

	public void classDef() {
		match("class");
		match(TokenType.Identifier);
		if (lookahead.value().equals(":")) {
			match(":");
			expressionList();
		}
		braceSuite();
	}

	public void simpleStmt() {
		if (lookahead.value().equals("return")) {
			match("return");
			if (!lookahead.type().equals(TokenType.Newline)) {
				expressionList();
			}
		} else if (lookahead.value().equals("break")) {
			match("break");
		} else if (lookahead.value().equals("continue")) {
			match("continue");
		} else {
			expressionList();
			expressionStmt();
		}
	}

	public void expressionStmt() {
		if (lookahead.value().equals("=")) {
			match("=");
			expressionList();
		} else if (lookahead.type().equals(TokenType.AssignOp)) {
			match(TokenType.AssignOp);
			expressionList();
		}
	}

	public void expressionList() {
		expression();
		if (lookahead.value().equals(",")) {
			int size = 1;
			do {
				match(",");
				expression();

				size++;

			} while (lookahead.value().equals(","));
			treeBuilder.expressionListNode(size);
		}
	}

	public void expression() {
		orTest();
		if (lookahead.value().equals("if")) {
			match("if");
			orTest();
			match("else");
			expression();

			treeBuilder.ifelseExpression();
		}
	}

	public void orTest() {
		andTest();
		orTestMore();
	}

	public void orTestMore() {
		if (lookahead.value().equals("or")) {
			match("or");
			andTest();

			treeBuilder.logicalNode("or");

			orTestMore();
		}
	}

	public void andTest() {
		notTest();
		andTestMore();
	}

	public void andTestMore() {
		if (lookahead.value().equals("and")) {
			match("and");
			notTest();

			treeBuilder.logicalNode("and");

			andTestMore();
		}
	}

	public void notTest() {
		if (lookahead.value().equals("not")) {
			match("not");
			notTest();

			treeBuilder.unaryNode("not");
		} else {
			comparison();
		}
	}

	public void comparison() {
		orExpr();
		String operator = compOperator();
		if (operator != null) {
			orExpr();
			treeBuilder.comparisonNode(operator);
		}
	}

	public String compOperator() {
		switch (lookahead.value()) {
		case "<":
			match("<");
			return "<";

		case "<=":
			match("<=");
			return "<=";

		case ">":
			match(">");
			return ">";

		case ">=":
			match(">=");
			return ">=";

		case "!=":
			match("!=");
			return "!=";

		case "==":
			match("==");
			return "==";

		case "in":
			match("in");
			return "in";

		case "not":
			match("not");
			match("in");
			return "not in";

		case "is":
			match("is");
			if (lookahead.value().equals("not")) {
				match("not");
				return "is not";
			} else {
				return "is";
			}

		default:
			return null;
		}
	}

	public void orExpr() {
		xorExpr();
		orExprMore();
	}

	public void orExprMore() {
		if (lookahead.value().equals("|")) {
			match("|");
			xorExpr();

			treeBuilder.binaryNode("|");

			orExprMore();
		}
	}

	public void xorExpr() {
		andExpr();
		xorExprMore();
	}

	public void xorExprMore() {
		if (lookahead.value().equals("^")) {
			match("^");
			andExpr();

			treeBuilder.binaryNode("^");

			xorExprMore();
		}
	}

	public void andExpr() {
		shiftExpr();
		andExprMore();
	}

	public void andExprMore() {
		if (lookahead.value().equals("&")) {
			match("&");
			shiftExpr();

			treeBuilder.binaryNode("&");

			andExprMore();
		}
	}

	public void shiftExpr() {
		aExpr();
		shiftExprMore();
	}

	public void shiftExprMore() {
		if (lookahead.value().equals("<<")) {
			match("<<");
			aExpr();

			treeBuilder.binaryNode("<<");

			shiftExprMore();
		} else if (lookahead.value().equals(">>")) {
			match(">>");
			aExpr();

			treeBuilder.binaryNode(">>");

			shiftExprMore();
		}
	}

	public void aExpr() {
		mExpr();
		aExprMore();
	}

	public void aExprMore() {
		if (lookahead.value().equals("+")) {
			match("+");
			mExpr();

			treeBuilder.binaryNode("+");

			aExprMore();
		} else if (lookahead.value().equals("-")) {
			match("-");
			mExpr();

			treeBuilder.binaryNode("-");

			aExprMore();
		}
	}

	public void mExpr() {
		uExpr();
		mExprMore();
	}

	public void mExprMore() {
		if (lookahead.value().equals("*")) {
			match("*");
			uExpr();

			treeBuilder.binaryNode("*");

			mExprMore();
		} else if (lookahead.value().equals("/")) {
			match("/");
			uExpr();

			treeBuilder.binaryNode("/");

			mExprMore();
		} else if (lookahead.value().equals("%")) {
			match("%");
			uExpr();

			treeBuilder.binaryNode("%");

			mExprMore();
		}
	}

	public void uExpr() {
		if (lookahead.value().equals("+")) {
			match("+");
			uExpr();

			treeBuilder.unaryNode("+");

		} else if (lookahead.value().equals("-")) {
			match("-");
			uExpr();

			treeBuilder.unaryNode("-");

		} else if (lookahead.value().equals("~")) {
			match("~");
			uExpr();

			treeBuilder.unaryNode("~");

		} else {
			power();
		}
	}

	public void power() {
		primary();
		if (lookahead.value().equals("**")) {
			match("**");
			uExpr();

			treeBuilder.binaryNode("**");
		}
	}

	public void primary() {
		atom();
		primaryArg();
	}

	public void primaryArg() {
		if (lookahead.value().equals(".")) {
			match(".");
			treeBuilder.identifierNode(lookahead.value());

			match(TokenType.Identifier);
			treeBuilder.attributeNode();

			primaryArg();
		} else if (lookahead.value().equals("[")) {
			match("[");
			expressionList();
			match("]");

			treeBuilder.indexingNode();

			primaryArg();
		} else if (lookahead.value().equals("(")) {
			match("(");
			if (lookahead.value().equals(")")) {
				match(")");
				treeBuilder.callNodeWithoutArgs();
			} else {
				expressionList();
				match(")");

				treeBuilder.callNode();
			}

			primaryArg();
		}
	}

	public void atom() {
		if (lookahead.value().equals("(")) {
			match("(");
			expressionList();
			match(")");
		} else {
			switch (lookahead.type()) {
			case Identifier:
				treeBuilder.identifierNode(lookahead.value());
				match(TokenType.Identifier);
				break;

			case String:
				treeBuilder.stringNode(lookahead.value());
				match(TokenType.String);
				break;

			case LongIntger:
				treeBuilder.longNode(lookahead.value());
				match(TokenType.LongIntger);
				break;

			case Integer:
				treeBuilder.intNode(lookahead.value());
				match(TokenType.Integer);
				break;

			case Float:
				treeBuilder.doubleNode(lookahead.value());
				match(TokenType.Float);
				break;

			case Keyword:
				if (lookahead.value().equals("true")) {
					match("true");
					treeBuilder.boolNode(true);
					break;
				} else if (lookahead.value().equals("false")) {
					match("false");
					treeBuilder.boolNode(false);
					break;
				}

			default:
				throw new Error("Error in line : " + lineCount
						+ ". Unexpected token '" + lookahead + "'");
			}
		}
	}

	private void match(String value) {
		if (lookahead.value().equals(value)) {
			consume();
		} else {
			throw new Error("Error in line : " + lineCount + ". Expecting '"
					+ value + "'; found '" + lookahead.value() + "'");
		}
	}

	private void match(TokenType type) {
		if (lookahead.type().equals(type)) {
			consume();
		} else {
			throw new Error("Error in line : " + lineCount + ". Expecting '"
					+ type.name() + "'; found '" + lookahead.type().name()
					+ "'");
		}
	}

	private void consume() {
		if (lookahead.type().equals(TokenType.Newline)) {
			lineCount++;
		}
		lookahead = input.nextToken();
	}
}
