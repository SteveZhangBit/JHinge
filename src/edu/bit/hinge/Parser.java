/**
 * 
 */
package edu.bit.hinge;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.builder.ASTBuilder;
import edu.bit.hinge.Token.TokenType;

/**
 * @author zhang
 *
 */
public class Parser {

	private Lexer input;
	private Token lookahead;
	
	private ASTBuilder treeBuilder = new ASTBuilder();

	public Parser(Lexer input) {
		this.input = input;
		this.lookahead = input.nextToken();
	}
	
	public AST getAST() {
		return treeBuilder.getAST();
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
			}
			else {
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
			
		} else if (lookahead.value().equals("[")) {
			match("[");
			expressionList();
			match("]");
			
			treeBuilder.indexingNode();
			
		} else if (lookahead.value().equals("(")) {
			match("(");
			expressionList();
			match(")");
			
			treeBuilder.callNode();
			
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

			default:
				break;
			}
		}
	}

	private void match(String value) {
		if (this.lookahead.value().equals(value)) {
			consume();
		} else {
			throw new Error("Error: expecting " + value + "; found "
					+ lookahead.value());
		}
	}

	private void match(TokenType type) {
		if (this.lookahead.type().equals(type)) {
			consume();
		} else {
			throw new Error("Error: expecting " + type.name() + "; found "
					+ lookahead.type().name());
		}
	}

	private void consume() {
		this.lookahead = input.nextToken();
	}
}
