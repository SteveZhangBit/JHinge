public class Parser {

	private Lexer input;
	private Token lookahead;
	private int lineCount = 1;
	private int colTokenCount = 0;

	public Parser(Lexer input) {
		this.input = input;
		this.lookahead = input.nextToken();
	}

	public void entry() {
		<<entry>>
	}

	{%rules
	public void <<rule>>() {
		{%statements
		else if (<<statement_first_set>>) {
			<<statement>>
		}
		statements%}
		else error();
	}
	rules%}

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
			throw new Error("Error in line : " + lineCount + "the "
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