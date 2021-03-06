package edu.bit.hinge;

import java.io.File;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.lexer.Lexer;
import edu.bit.hinge.memery.MemerySpace;
import edu.bit.hinge.parser.Parser;
import edu.bit.hinge.visitor.EvalVisitor;

public class Hinge {
	
	private Lexer lexer;
	private Parser parser;
	private AST root;
	private MemerySpace globalSpace = new MemerySpace("_GLOBAL_SPACE_", null);

	public static void main(String[] args) {
		Hinge interpreter = new Hinge(new File("/Users/zhang/Documents/JavaWorkspace/JHinge/test.hg"));
		interpreter.run();
	}
	
	public Hinge(File file) {
		lexer = new Lexer(new File("/Users/zhang/Documents/JavaWorkspace/JHinge/test.hg"));
		parser = new Parser(lexer);
	}
	
	public void run() {
		parser.entry();
		root = parser.getAST();
		
		globalSpace.put("version", 0.01);
		root.visit(new EvalVisitor(globalSpace));
	}
}
