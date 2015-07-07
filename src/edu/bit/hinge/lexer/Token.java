/**
 * This file defined the Token class and the TokenType enumeration. A token include a TokenType
 * and the value that is a string. The tokens can be printed in the format '<token name, value>'.
 * 
 */
package edu.bit.hinge.lexer;

import java.util.HashMap;

/**
 * @author zhang
 *
 */
public class Token {
	
	public static enum TokenType {
		Comment("#.*"),
		String("r?(?:\'[^\']*\'|\"[^\"]*\")"),
		Identifier("[a-zA-Z_]\\w*"),
		Keyword(""),
		Float("(?:\\d*\\.\\d+|\\d+\\.|\\d+)[eE][+-]?\\d+|\\d*\\.\\d+|\\d+\\."),
		LongIntger("(?:[1-9]\\d*|0[0-7]+|0[xX][0-9a-fA-F]+|0[bB][01]+|0)[lL]"),
		Integer("[1-9]\\d*|0[0-7]+|0[xX][0-9a-fA-F]+|0[bB][01]+|0"),
		AssignOp("\\+=|-=|\\*=|\\*\\*=|/=|%=|<<=|>>=|&=|\\|=|\\^="),
		Bitwise("<<|>>|&|\\||\\^|~"),
		Comparison("<=|>=|==|!=|<|>"),
		Operator("\\+|-|\\*\\*|\\*|/|%"),
		Delimiter("[()\\[\\]{},.?:]"),
		Assign("="),
		Blank("[ \\t\\x0B\\f\\r]+"),
		Newline("\\n"),
		EOF("");
		
		public final String pattern;
		
		private TokenType(String pattern) {
			this.pattern = pattern;
		}
	}
	
	private static final String[] Keywords = {
		"and",		"or",		"not",		"is",		"in",		"true",			"false",
		"continue",	"break",	"for",		"while", 	"do",		"switch",		"case",
		"if",		"else",		"raise",	"import",	"try",		"catch",		"finally",
		"return",	"func",		"class",	"super",	"self",		"none"
	};
	private static final HashMap<Integer, String> keywordMap = new HashMap<Integer, String>();
	static {
		for (String keyword : Keywords) {
			keywordMap.put(keyword.hashCode(), keyword);
		}
	}
	
	private static boolean isKeyword(String value) {
		Integer key = value.hashCode();
		return (keywordMap.containsKey(key) && keywordMap.get(key).equals(value));
	}
	
	
	private TokenType	type;
	private String		value;
	
	public Token(TokenType tokenType, String value) {
		this.type = tokenType;
		this.value = value;
		
		// Check if it's keyword
		if (tokenType.equals(TokenType.Identifier) && isKeyword(value)) {
			this.type = TokenType.Keyword;
		}
	}
	
	public TokenType type() {
		return type;
	}
	
	public String value() {
		return value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<" + this.type.name() + ", " + this.value + ">";
	}
}
