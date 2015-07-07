/**
 * In this file, define the tokens and a lexer for Hinge. The lexer will parse
 * the lexes in the input file and return a list of tokens which can be used in
 * the parser.
 * 
 */
package edu.bit.hinge.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bit.hinge.lexer.Token.TokenType;

/**
 * @author zhang
 * Lexer will parse the source input, that should be a file or string, into tokens.
 * 
 */
public class Lexer {

	private String	input;
	private Pattern	pattern;
	private Matcher	matcher;
	
	{
		StringBuilder patternString = new StringBuilder();
		for (TokenType type : TokenType.values()) {
			// skip keyword
			// Keyword will match as Identifier first, then check the identifier
			// whether if it's a keyword.
			if (type.equals(TokenType.Keyword) || type.equals(TokenType.EOF))
				continue;
			
			patternString.append(String.format("|(?<%s>%s)", type.name(), type.pattern));
		}
		this.pattern = Pattern.compile(patternString.substring(1));
	}
	
	public Lexer() {}
	
	public Lexer(String source) {
		setInput(source.toString());
	}
	
	public Lexer(File file) {
		StringBuilder source = new StringBuilder();
		try {
			FileReader reader = new FileReader(file);
			char[] buffer = new char[4096];
			while (reader.read(buffer) != -1) {
				source.append(buffer);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: File " + file.getPath() + " not found.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: Cannot read file " + file.getPath() + ".");
		}
		
		setInput(source.toString());
	}
	
	/**
	 * 
	 * @return null means the end of file
	 */
	public Token nextToken() {
		if (matcher.find()) {
			for (TokenType type : TokenType.values()) {
				// Skip Keyword
				if (type.equals(TokenType.Keyword) || type.equals(TokenType.EOF))
					continue;
				
				if (matcher.group(type.name()) != null) {
					// Recurse comment, blank
					if (type.equals(TokenType.Comment) || type.equals(TokenType.Blank))
						return nextToken();
					
					return new Token(type, matcher.group(type.name()));
				}
			}
		}
		
		return new Token(TokenType.EOF, "");
	}
	
	public void setInput(String input) {
		this.input = input;
		this.matcher = this.pattern.matcher(this.input);
	}
}
