package edu.bit.hinge.lexer;

public class Token {

	public static enum TokenType {
		Comment("#.*"),
		String("r?(?:\'[^\']*\'|\"[^\"]*\")"),
		Keyword("and|or|not|is|in|true|false|continue|break|for|while|do|switch|case|if|else|elif|raise|import|try|catch|finally|return|func|class|super|self|none"),
		Identifier("[a-zA-Z_]\\w*"),
		Float("(?:\\d*\\.\\d+|\\d+\\.|\\d+)[eE][+-]?\\d+|\\d*\\.\\d+|\\d+\\."),
		LongInteger("(?:[1-9]\\d*|0[0-7]+|0[xX][0-9a-fA-F]+|0[bB][01]+|0)[lL]"),
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

	private TokenType	type;
	private String		value;

	public Token(TokenType tokenType, String value) {
		this.type = tokenType;
		this.value = value;
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
