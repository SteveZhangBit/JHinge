package edu.bit.hinge;

import java.io.File;
import java.util.Scanner;

import edu.bit.hinge.visitor.PrintVisitor;

public class Hinge {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input;
		Lexer lexer = new Lexer();
		while (!(input = scanner.nextLine()).equals("")) {
			lexer.setInput(input);
//			Token token;
//			while ((token = lexer.nextToken()) != null) {
//				System.out.println(token);
//			}
			Parser parser = new Parser(lexer);
			parser.expressionList();
			parser.getAST().visit(new PrintVisitor());
		}
		scanner.close();
		
		/*Lexer lexer = new Lexer(new File("/Users/zhang/Documents/JavaWorkspace/JHinge/test.hg"));
		Token token;
		while ((token = lexer.nextToken()) != null) {
			System.out.println(token);
		}*/
	}
}
