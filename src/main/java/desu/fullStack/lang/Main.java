package desu.fullStack.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import desu.fullStack.App;
import desu.fullStack.lang.ast.Expression;
import desu.fullStack.lang.ast.Statement;
import desu.fullStack.lang.lexer.Lexer;
import desu.fullStack.lang.lexer.Token;
import desu.fullStack.lang.tools.Compiler;

public class Main {
	public static void main(String[] args) {
		try {
			new Main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Main() throws IOException {
		Lexer lexer = new Lexer();
		Parser parser;
		Compiler compiler = new Compiler();
		
		String source = readFile(new File("res/prog.lg"));
		
		List<Token> tokens = lexer.tokenize(source);
		tokens.forEach(System.out::println);
		parser = new Parser(tokens);
		Statement statement = parser.parse();
		System.out.println(statement);
		
		PrintWriter pw = new PrintWriter("res/prog.vm");
		pw.print(compiler.compileStatement(statement));
		pw.print("\n\n\n\n#_start\ncall #main 0\nhalt\n");
		pw.close();
		
		App.main(new String[]{});
		
	}
	
	
	public String readFile(File file) throws IOException {
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line;
		
		while((line = reader.readLine()) != null)
			buffer.append(line).append('\n');
		
		reader.close();
		
		return buffer.toString();
	}
}
