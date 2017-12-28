package desu.fullStack.lang;

import java.util.List;

import desu.fullStack.lang.ast.Expression;
import desu.fullStack.lang.ast.Statement;
import desu.fullStack.lang.lexer.Lexer;
import desu.fullStack.lang.lexer.Token;
import desu.fullStack.lang.tools.Compiler;

public class Main {
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		Lexer lexer = new Lexer();
		Parser parser;
		Compiler compiler = new Compiler();
		
		List<Token> tokens = lexer.tokenize("print -10 * 2 + 34 / 2;");
		//tokens.forEach(System.out::println);
		parser = new Parser(tokens);
		Statement statement = parser.parseStatement();
		
		
		System.out.println(compiler.compileStatement(statement));
		
	}
}
