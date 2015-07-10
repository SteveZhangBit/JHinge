public class Token {

	public static enum TokenType {
		<<token>>
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
