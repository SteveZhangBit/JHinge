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
		statement_list();
	}

	
	public void _14_extend() {
		
		if (lookahead.value().equals("-")||lookahead.value().equals(">>")||lookahead.value().equals("+")||lookahead.value().equals("^")||lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("|")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("%")||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("<<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals("/")||lookahead.value().equals("|=")||lookahead.value().equals("*")||lookahead.value().equals("if")||lookahead.value().equals("==")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals(">>=")||lookahead.value().equals("!=")||lookahead.value().equals("&")||lookahead.value().equals("**=")||lookahead.value().equals("%=")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("in")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals("<=")||lookahead.value().equals("is")||lookahead.value().equals(",")) {
			
		}
		
		else if (lookahead.value().equals("**")) {
			match("**"); u_expr();
		}
		
		else error();
	}
	
	public void and_test_() {
		
		if (lookahead.value().equals("and")) {
			match("and"); not_test();and_test_();
		}
		
		else if (lookahead.value().equals("if")||lookahead.value().equals(")")||lookahead.value().equals("|=")||lookahead.value().equals("]")||lookahead.value().equals("&=")||lookahead.value().equals("/=")||lookahead.value().equals("^=")||lookahead.value().equals("or")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals(">>=")||lookahead.value().equals(",")||lookahead.value().equals("%=")) {
			
		}
		
		else error();
	}
	
	public void _4_extend() {
		
		if (lookahead.value().equals("=")) {
			match("="); expression();
		}
		
		else if (lookahead.value().equals(")")||lookahead.value().equals(",")) {
			
		}
		
		else error();
	}
	
	public void _11_extend() {
		
		if (lookahead.value().equals("if")||lookahead.value().equals(")")||lookahead.value().equals(">>=")||lookahead.value().equals("]")||lookahead.value().equals("&=")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals("or")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals("%=")||lookahead.value().equals(",")||lookahead.value().equals("|=")) {
			
		}
		
		else if (lookahead.value().equals("==")) {
			match("=="); or_expr();
		}
		
		else if (lookahead.value().equals(">")) {
			match(">"); or_expr();
		}
		
		else if (lookahead.value().equals("<")) {
			match("<"); or_expr();
		}
		
		else if (lookahead.value().equals("not")||lookahead.value().equals("in")) {
			_12_extend();match("in"); or_expr();
		}
		
		else if (lookahead.value().equals(">=")) {
			match(">="); or_expr();
		}
		
		else if (lookahead.value().equals("is")) {
			match("is"); _12_extend();or_expr();
		}
		
		else if (lookahead.value().equals("!=")) {
			match("!="); or_expr();
		}
		
		else if (lookahead.value().equals("<=")) {
			match("<="); or_expr();
		}
		
		else error();
	}
	
	public void primary() {
		
		if (lookahead.type().equals(TokenType.String)||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("(")||lookahead.value().equals("false")||lookahead.type().equals(TokenType.Integer)||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("true")||lookahead.type().equals(TokenType.LongInteger)) {
			atom();primary_();
		}
		
		else error();
	}
	
	public void not_test() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			comparison();
		}
		
		else if (lookahead.value().equals("not")) {
			match("not"); not_test();
		}
		
		else error();
	}
	
	public void _12_extend() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.value().equals("in")||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.LongInteger)) {
			
		}
		
		else if (lookahead.value().equals("not")) {
			match("not"); 
		}
		
		else error();
	}
	
	public void or_expr() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			xor_expr();or_expr_();
		}
		
		else error();
	}
	
	public void enclosure() {
		
		if (lookahead.value().equals("(")) {
			parenth_form();
		}
		
		else error();
	}
	
	public void _9_extend() {
		
		if (lookahead.value().equals(")")||lookahead.value().equals("]")||lookahead.value().equals("&=")||lookahead.value().equals("/=")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals("{")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")||lookahead.value().equals(",")) {
			_9_extend_();
		}
		
		else error();
	}
	
	public void a_expr_() {
		
		if (lookahead.value().equals("-")) {
			match("-"); m_expr();a_expr_();
		}
		
		else if (lookahead.value().equals(">>")||lookahead.value().equals("^")||lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("|")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("<<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")||lookahead.value().equals("if")||lookahead.value().equals("==")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("!=")||lookahead.value().equals("&")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("in")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals("<=")||lookahead.value().equals("is")||lookahead.value().equals(",")) {
			
		}
		
		else if (lookahead.value().equals("+")) {
			match("+"); m_expr();a_expr_();
		}
		
		else error();
	}
	
	public void or_expr_() {
		
		if (lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals("%=")||lookahead.value().equals("|=")||lookahead.value().equals("if")||lookahead.value().equals("**=")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals(">>=")||lookahead.value().equals("!=")||lookahead.value().equals("==")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("in")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals(",")||lookahead.value().equals("is")||lookahead.value().equals("<=")) {
			
		}
		
		else if (lookahead.value().equals("|")) {
			match("|"); xor_expr();or_expr_();
		}
		
		else error();
	}
	
	public void shift_expr_() {
		
		if (lookahead.value().equals("<<")) {
			match("<<"); a_expr();shift_expr_();
		}
		
		else if (lookahead.value().equals("^")||lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("|")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")||lookahead.value().equals("if")||lookahead.value().equals("**=")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("!=")||lookahead.value().equals("&")||lookahead.value().equals("==")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("in")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals(",")||lookahead.value().equals("is")||lookahead.value().equals("<=")) {
			
		}
		
		else if (lookahead.value().equals(">>")) {
			match(">>"); a_expr();shift_expr_();
		}
		
		else error();
	}
	
	public void _2_extend() {
		
		if (lookahead.value().equals(")")) {
			
		}
		
		else if (lookahead.type().equals(TokenType.Identifier)) {
			match(TokenType.Identifier); _4_extend();_3_extend();
		}
		
		else error();
	}
	
	public void u_expr() {
		
		if (lookahead.value().equals("-")) {
			match("-"); u_expr();
		}
		
		else if (lookahead.value().equals("~")) {
			match("~"); u_expr();
		}
		
		else if (lookahead.value().equals("+")) {
			match("+"); u_expr();
		}
		
		else if (lookahead.type().equals(TokenType.String)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			power();
		}
		
		else error();
	}
	
	public void shift_expr() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			a_expr();shift_expr_();
		}
		
		else error();
	}
	
	public void _10_extend() {
		
		if (lookahead.value().equals(")")||lookahead.value().equals("]")||lookahead.value().equals("&=")||lookahead.value().equals("/=")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals("{")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")||lookahead.value().equals(",")) {
			
		}
		
		else if (lookahead.value().equals("if")) {
			match("if"); or_test();match("else"); expression();
		}
		
		else error();
	}
	
	public void _3_extend() {
		
		if (lookahead.value().equals(")")||lookahead.value().equals(",")) {
			_3_extend_();
		}
		
		else error();
	}
	
	public void else_stmt() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("if")||lookahead.value().equals("(")||lookahead.type().equals(TokenType.EOF)||lookahead.value().equals("true")||lookahead.type().equals(TokenType.LongInteger)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("continue")||lookahead.value().equals("false")||lookahead.value().equals("for")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("return")||lookahead.value().equals("while")||lookahead.type().equals(TokenType.Newline)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("do")||lookahead.value().equals("}")||lookahead.value().equals("class")||lookahead.value().equals("func")||lookahead.value().equals("~")||lookahead.value().equals("break")) {
			
		}
		
		else if (lookahead.value().equals("else")) {
			match("else"); suite();
		}
		
		else error();
	}
	
	public void _17_extend() {
		
		if (lookahead.value().equals(",")) {
			match(","); defparameter();
		}
		
		else error();
	}
	
	public void parenth_form() {
		
		if (lookahead.value().equals("(")) {
			match("("); expression_list();match(")"); 
		}
		
		else error();
	}
	
	public void literal() {
		
		if (lookahead.type().equals(TokenType.String)) {
			match(TokenType.String); 
		}
		
		else if (lookahead.type().equals(TokenType.Integer)) {
			match(TokenType.Integer); 
		}
		
		else if (lookahead.value().equals("true")) {
			match("true"); 
		}
		
		else if (lookahead.type().equals(TokenType.Float)) {
			match(TokenType.Float); 
		}
		
		else if (lookahead.value().equals("false")) {
			match("false"); 
		}
		
		else if (lookahead.type().equals(TokenType.LongInteger)) {
			match(TokenType.LongInteger); 
		}
		
		else error();
	}
	
	public void and_expr_() {
		
		if (lookahead.value().equals("^")||lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("|")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")||lookahead.value().equals("if")||lookahead.value().equals("**=")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("!=")||lookahead.value().equals("==")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("in")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals(",")||lookahead.value().equals("is")||lookahead.value().equals("<=")) {
			
		}
		
		else if (lookahead.value().equals("&")) {
			match("&"); shift_expr();and_expr_();
		}
		
		else error();
	}
	
	public void defparameter() {
		
		if (lookahead.type().equals(TokenType.Identifier)) {
			match(TokenType.Identifier); _4_extend();
		}
		
		else error();
	}
	
	public void _8_extend() {
		
		if (lookahead.value().equals("-")) {
			match("-"); u_expr();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals(")")||lookahead.type().equals(TokenType.Newline)) {
			
		}
		
		else if (lookahead.type().equals(TokenType.String)) {
			match(TokenType.String); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals("+")) {
			match("+"); u_expr();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.type().equals(TokenType.Integer)) {
			match(TokenType.Integer); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals("(")) {
			match("("); expression_list();match(")"); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals("true")) {
			match("true"); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.type().equals(TokenType.Float)) {
			match(TokenType.Float); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals("not")) {
			match("not"); not_test();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.type().equals(TokenType.Identifier)) {
			match(TokenType.Identifier); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals("~")) {
			match("~"); u_expr();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.value().equals("false")) {
			match("false"); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else if (lookahead.type().equals(TokenType.LongInteger)) {
			match(TokenType.LongInteger); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();
		}
		
		else error();
	}
	
	public void _5_extend() {
		
		if (lookahead.value().equals("{")) {
			
		}
		
		else if (lookahead.type().equals(TokenType.Newline)) {
			match(TokenType.Newline); 
		}
		
		else error();
	}
	
	public void and_expr() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			shift_expr();and_expr_();
		}
		
		else error();
	}
	
	public void power() {
		
		if (lookahead.type().equals(TokenType.String)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			primary();_14_extend();
		}
		
		else error();
	}
	
	public void a_expr() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			m_expr();a_expr_();
		}
		
		else error();
	}
	
	public void primary_() {
		
		if (lookahead.value().equals("-")||lookahead.value().equals(">>")||lookahead.value().equals("+")||lookahead.value().equals("^")||lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("in")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("%")||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("<<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals("/")||lookahead.value().equals("|=")||lookahead.value().equals("*")||lookahead.value().equals("if")||lookahead.value().equals("**")||lookahead.value().equals("**=")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals(">>=")||lookahead.value().equals("!=")||lookahead.value().equals("&")||lookahead.value().equals("==")||lookahead.value().equals("%=")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("|")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals(",")||lookahead.value().equals("is")||lookahead.value().equals("<=")) {
			
		}
		
		else if (lookahead.value().equals(".")) {
			match("."); match(TokenType.Identifier); primary_();
		}
		
		else if (lookahead.value().equals("(")) {
			match("("); _8_extend();match(")"); primary_();
		}
		
		else if (lookahead.value().equals("[")) {
			match("["); expression_list();match("]"); primary_();
		}
		
		else error();
	}
	
	public void _3_extend_() {
		
		if (lookahead.value().equals(",")) {
			_17_extend();_3_extend_();
		}
		
		else if (lookahead.value().equals(")")) {
			
		}
		
		else error();
	}
	
	public void _16_extend() {
		
		if (lookahead.value().equals("-")) {
			match("-"); u_expr();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.type().equals(TokenType.String)) {
			match(TokenType.String); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("+")) {
			match("+"); u_expr();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("true")) {
			match("true"); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.type().equals(TokenType.LongInteger)) {
			match(TokenType.LongInteger); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("not")) {
			match("not"); not_test();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("for")) {
			match("for"); primary();match("in"); expression_list();suite();
		}
		
		else if (lookahead.type().equals(TokenType.Newline)) {
			match(TokenType.Newline); 
		}
		
		else if (lookahead.type().equals(TokenType.Integer)) {
			match(TokenType.Integer); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("class")) {
			match("class"); match(TokenType.Identifier); _1_extend();suite();
		}
		
		else if (lookahead.value().equals("break")) {
			match("break"); match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("if")) {
			match("if"); expression();suite();elif_stmt();
		}
		
		else if (lookahead.value().equals("(")) {
			match("("); expression_list();match(")"); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.type().equals(TokenType.Float)) {
			match(TokenType.Float); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.type().equals(TokenType.Identifier)) {
			match(TokenType.Identifier); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("continue")) {
			match("continue"); match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("false")) {
			match("false"); primary_();_14_extend();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("return")) {
			match("return"); _8_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("do")) {
			match("do"); suite();match("while"); expression();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("while")) {
			match("while"); expression();suite();
		}
		
		else if (lookahead.value().equals("~")) {
			match("~"); u_expr();m_expr_();a_expr_();shift_expr_();and_expr_();xor_expr_();or_expr_();_11_extend();and_test_();or_test_();_10_extend();_9_extend();_6_extend();match(TokenType.Newline); 
		}
		
		else if (lookahead.value().equals("func")) {
			match("func"); match(TokenType.Identifier); match("("); _2_extend();match(")"); suite();
		}
		
		else error();
	}
	
	public void elif_stmt() {
		
		if (lookahead.value().equals("elif")) {
			match("elif"); expression();suite();elif_stmt();
		}
		
		else if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("if")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.type().equals(TokenType.EOF)||lookahead.value().equals("true")||lookahead.type().equals(TokenType.LongInteger)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("continue")||lookahead.value().equals("false")||lookahead.value().equals("for")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("return")||lookahead.value().equals("while")||lookahead.type().equals(TokenType.Newline)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("do")||lookahead.value().equals("}")||lookahead.value().equals("class")||lookahead.value().equals("func")||lookahead.value().equals("else")||lookahead.value().equals("break")) {
			else_stmt();
		}
		
		else error();
	}
	
	public void and_test() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			not_test();and_test_();
		}
		
		else error();
	}
	
	public void _9_extend_() {
		
		if (lookahead.value().equals(")")||lookahead.value().equals("]")||lookahead.value().equals("&=")||lookahead.value().equals("/=")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals("{")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")) {
			
		}
		
		else if (lookahead.value().equals(",")) {
			_20_extend();_9_extend_();
		}
		
		else error();
	}
	
	public void _0_extend() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("if")||lookahead.value().equals("(")||lookahead.type().equals(TokenType.EOF)||lookahead.value().equals("true")||lookahead.type().equals(TokenType.LongInteger)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("continue")||lookahead.value().equals("false")||lookahead.value().equals("for")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("return")||lookahead.value().equals("while")||lookahead.type().equals(TokenType.Newline)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("do")||lookahead.value().equals("}")||lookahead.value().equals("class")||lookahead.value().equals("func")||lookahead.value().equals("~")||lookahead.value().equals("break")) {
			_0_extend_();
		}
		
		else error();
	}
	
	public void m_expr() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			u_expr();m_expr_();
		}
		
		else error();
	}
	
	public void _1_extend() {
		
		if (lookahead.value().equals("{")||lookahead.type().equals(TokenType.Newline)) {
			
		}
		
		else if (lookahead.value().equals(":")) {
			match(":"); expression_list();
		}
		
		else error();
	}
	
	public void atom() {
		
		if (lookahead.type().equals(TokenType.String)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.Integer)||lookahead.type().equals(TokenType.LongInteger)||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)) {
			literal();
		}
		
		else if (lookahead.type().equals(TokenType.Identifier)) {
			match(TokenType.Identifier); 
		}
		
		else if (lookahead.value().equals("(")) {
			enclosure();
		}
		
		else error();
	}
	
	public void xor_expr() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			and_expr();xor_expr_();
		}
		
		else error();
	}
	
	public void _0_extend_() {
		
		if (lookahead.value().equals("}")||lookahead.type().equals(TokenType.EOF)) {
			
		}
		
		else if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("if")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.LongInteger)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("continue")||lookahead.value().equals("false")||lookahead.value().equals("for")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("return")||lookahead.value().equals("while")||lookahead.type().equals(TokenType.Newline)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("do")||lookahead.value().equals("class")||lookahead.value().equals("func")||lookahead.value().equals("~")||lookahead.value().equals("break")) {
			_16_extend();_0_extend_();
		}
		
		else error();
	}
	
	public void comparison() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			or_expr();_11_extend();
		}
		
		else error();
	}
	
	public void _20_extend() {
		
		if (lookahead.value().equals(",")) {
			match(","); expression();
		}
		
		else error();
	}
	
	public void _18_extend() {
		
		if (lookahead.value().equals("=")) {
			match("="); expression_list();
		}
		
		else error();
	}
	
	public void m_expr_() {
		
		if (lookahead.value().equals("%")) {
			match("%"); u_expr();m_expr_();
		}
		
		else if (lookahead.value().equals("-")||lookahead.value().equals(">>")||lookahead.value().equals("+")||lookahead.value().equals("^")||lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("in")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("<<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals(">>=")||lookahead.value().equals("|=")||lookahead.value().equals("if")||lookahead.value().equals("**=")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals("%=")||lookahead.value().equals("!=")||lookahead.value().equals("&")||lookahead.value().equals("==")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("|")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals(",")||lookahead.value().equals("is")||lookahead.value().equals("<=")) {
			
		}
		
		else if (lookahead.value().equals("/")) {
			match("/"); u_expr();m_expr_();
		}
		
		else if (lookahead.value().equals("*")) {
			match("*"); u_expr();m_expr_();
		}
		
		else error();
	}
	
	public void or_test_() {
		
		if (lookahead.value().equals("if")||lookahead.value().equals(")")||lookahead.value().equals("]")||lookahead.value().equals("&=")||lookahead.value().equals("/=")||lookahead.value().equals("^=")||lookahead.value().equals(">>=")||lookahead.value().equals("**=")||lookahead.value().equals("<<=")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals("%=")||lookahead.value().equals(",")||lookahead.value().equals("|=")) {
			
		}
		
		else if (lookahead.value().equals("or")) {
			match("or"); and_test();or_test_();
		}
		
		else error();
	}
	
	public void expression_list() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			expression();_9_extend();
		}
		
		else error();
	}
	
	public void or_test() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			and_test();or_test_();
		}
		
		else error();
	}
	
	public void _6_extend() {
		
		if (lookahead.type().equals(TokenType.Newline)) {
			
		}
		
		else if (lookahead.value().equals("**=")) {
			match("**="); expression_list();
		}
		
		else if (lookahead.value().equals("<<=")) {
			match("<<="); expression_list();
		}
		
		else if (lookahead.value().equals("+=")) {
			match("+="); expression_list();
		}
		
		else if (lookahead.value().equals("*=")) {
			match("*="); expression_list();
		}
		
		else if (lookahead.value().equals("=")) {
			_7_extend();
		}
		
		else if (lookahead.value().equals("&=")) {
			match("&="); expression_list();
		}
		
		else if (lookahead.value().equals("-=")) {
			match("-="); expression_list();
		}
		
		else if (lookahead.value().equals("/=")) {
			match("/="); expression_list();
		}
		
		else if (lookahead.value().equals("^=")) {
			match("^="); expression_list();
		}
		
		else if (lookahead.value().equals("%=")) {
			match("%="); expression_list();
		}
		
		else if (lookahead.value().equals(">>=")) {
			match(">>="); expression_list();
		}
		
		else if (lookahead.value().equals("|=")) {
			match("|="); expression_list();
		}
		
		else error();
	}
	
	public void xor_expr_() {
		
		if (lookahead.value().equals(")")||lookahead.value().equals("&=")||lookahead.value().equals("not")||lookahead.value().equals("in")||lookahead.value().equals("or")||lookahead.type().equals(TokenType.Newline)||lookahead.value().equals(">")||lookahead.value().equals("<")||lookahead.value().equals("{")||lookahead.value().equals("else")||lookahead.value().equals("%=")||lookahead.value().equals("|=")||lookahead.value().equals("if")||lookahead.value().equals("**=")||lookahead.value().equals("]")||lookahead.value().equals("/=")||lookahead.value().equals("and")||lookahead.value().equals("^=")||lookahead.value().equals(">>=")||lookahead.value().equals("!=")||lookahead.value().equals("==")||lookahead.value().equals("<<=")||lookahead.value().equals("+=")||lookahead.value().equals("*=")||lookahead.value().equals("|")||lookahead.value().equals("=")||lookahead.value().equals("-=")||lookahead.value().equals(">=")||lookahead.value().equals(",")||lookahead.value().equals("is")||lookahead.value().equals("<=")) {
			
		}
		
		else if (lookahead.value().equals("^")) {
			match("^"); and_expr();xor_expr_();
		}
		
		else error();
	}
	
	public void _7_extend() {
		
		if (lookahead.value().equals("=")) {
			_18_extend();_7_extend_();
		}
		
		else error();
	}
	
	public void suite() {
		
		if (lookahead.value().equals("{")||lookahead.type().equals(TokenType.Newline)) {
			_5_extend();match("{"); statement_list();match("}"); 
		}
		
		else error();
	}
	
	public void statement_list() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("if")||lookahead.value().equals("(")||lookahead.type().equals(TokenType.EOF)||lookahead.value().equals("true")||lookahead.type().equals(TokenType.LongInteger)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.value().equals("continue")||lookahead.value().equals("false")||lookahead.value().equals("for")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("return")||lookahead.value().equals("while")||lookahead.type().equals(TokenType.Newline)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("do")||lookahead.value().equals("}")||lookahead.value().equals("class")||lookahead.value().equals("func")||lookahead.value().equals("~")||lookahead.value().equals("break")) {
			_0_extend();
		}
		
		else error();
	}
	
	public void expression() {
		
		if (lookahead.value().equals("-")||lookahead.type().equals(TokenType.String)||lookahead.value().equals("+")||lookahead.value().equals("~")||lookahead.value().equals("(")||lookahead.value().equals("true")||lookahead.type().equals(TokenType.Float)||lookahead.value().equals("not")||lookahead.type().equals(TokenType.Identifier)||lookahead.type().equals(TokenType.Integer)||lookahead.value().equals("false")||lookahead.type().equals(TokenType.LongInteger)) {
			or_test();_10_extend();
		}
		
		else error();
	}
	
	public void _7_extend_() {
		
		if (lookahead.type().equals(TokenType.Newline)) {
			
		}
		
		else if (lookahead.value().equals("=")) {
			_18_extend();_7_extend_();
		}
		
		else error();
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