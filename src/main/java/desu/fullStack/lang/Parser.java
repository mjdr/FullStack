package desu.fullStack.lang;

import java.util.List;

import desu.fullStack.lang.ast.BinaryExpression;
import desu.fullStack.lang.ast.ConstantExpression;
import desu.fullStack.lang.ast.Expression;
import desu.fullStack.lang.ast.PrintStatement;
import desu.fullStack.lang.ast.Statement;
import desu.fullStack.lang.ast.UnaryExpression;
import desu.fullStack.lang.lexer.Token;
import desu.fullStack.lang.lexer.TokenType;

public class Parser {
	
	private List<Token> tokens;
	private int index;
	
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		index = 0;
	}
	
	public Statement parseStatement() {
		
		if(peek().type == TokenType.PRINT)
			return parsePrintStatement();
		
		throw new RuntimeException("Unexpected token " + peek());
		
	}
	private Statement parsePrintStatement() {
		must(TokenType.PRINT);
		Expression expression = parseExpression();
		must(TokenType.SEMI);
		return new PrintStatement(expression);
	}

	public Expression parseExpression() {
		
		return additive();
		
	}

	private Expression additive() {
		Expression acc = factor();
		while(peek().type == TokenType.PLUS || peek().type == TokenType.MINUS) 
			if(read().type == TokenType.PLUS)
				acc = new BinaryExpression(BinaryExpression.Type.ADD, acc, factor());
			else
				acc = new BinaryExpression(BinaryExpression.Type.SUB, acc, factor());
		
		return acc;
	}

	private Expression factor() {
		Expression acc = unary();
		while(peek().type == TokenType.STAR || peek().type == TokenType.SLASH) 
			if(read().type == TokenType.STAR)
				acc = new BinaryExpression(BinaryExpression.Type.MUL, acc, unary());
			else
				acc = new BinaryExpression(BinaryExpression.Type.DIV, acc, unary());
		
		return acc;
	}
	private Expression unary() {
		if(peek().type == TokenType.MINUS) {
			read();
			return new UnaryExpression(UnaryExpression.Type.MINUS, number());
		}
		return number();
	}

	private Expression number() {
		return new ConstantExpression(Integer.parseInt(must(TokenType.NUMBER).text));
	}
	
	
	
	
	
	private Token must(TokenType type) {
		if(peek().type == type) {
			
			return read();
		}
		else
			throw new RuntimeException(String.format("Expected %s , has %s", type.name(), peek().type.name()));
	}
	
	private Token peek() {
		return peek(0);
	}
	private Token peek(int i) {
		if(index + i >= tokens.size()) return new Token(TokenType.EOF);
		return tokens.get(index + i);
	}
	
	private Token read() {
		if(index >= tokens.size()) throw new RuntimeException("Unexpected eof");
		return tokens.get(index++);
	}
}
