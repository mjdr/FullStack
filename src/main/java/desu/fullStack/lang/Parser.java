package desu.fullStack.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import desu.fullStack.lang.ast.BinaryExpression;
import desu.fullStack.lang.ast.ConstantExpression;
import desu.fullStack.lang.ast.Expression;
import desu.fullStack.lang.ast.FunctionStatement;
import desu.fullStack.lang.ast.IfStatement;
import desu.fullStack.lang.ast.PrintStatement;
import desu.fullStack.lang.ast.Statement;
import desu.fullStack.lang.ast.StatementBlock;
import desu.fullStack.lang.ast.UnaryExpression;
import desu.fullStack.lang.ast.VariableAssignStatement;
import desu.fullStack.lang.ast.VariableExpression;
import desu.fullStack.lang.ast.VariableInitStatement;
import desu.fullStack.lang.lexer.Token;
import desu.fullStack.lang.lexer.TokenType;

public class Parser {
	
	private List<Token> tokens;
	private int index;
	
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		index = 0;
	}
	
	public Statement parse() {
		return parseFunctionStatement();
	}
	private Statement parseStatement() {
		
		if(peek().type == TokenType.PRINT)
			return parsePrintStatement();
		if(peek().type == TokenType.IF)
			return parseIfStatement();
		if(peek().type == TokenType.LCBR)
			return parseStatementBlock();
		
		if(
				peek(0).type == TokenType.ID &&
				peek(1).type == TokenType.ID &&
				peek(2).type == TokenType.SEMI
		 ) return parseVariableInitStatement();
		
		if(
				peek(0).type == TokenType.ID &&
				peek(1).type == TokenType.EQ
		 ) return parseVariableAssignStatement();
		
		 
		
		throw new RuntimeException("Unexpected token " + peek());
		
	}
	private Statement parseVariableAssignStatement() {
		String name = must(TokenType.ID).text;
		must(TokenType.EQ);
		Expression expression = parseExpression();
		must(TokenType.SEMI);
		return new VariableAssignStatement(name, expression);
	}

	private Statement parseVariableInitStatement() {
		String type = must(TokenType.ID).text;
		String name = must(TokenType.ID).text;
		must(TokenType.SEMI);
		return new VariableInitStatement(type, name);
	}

	private Statement parseFunctionStatement() {
		String retType = must(TokenType.ID).text;
		String name = must(TokenType.ID).text;
		Map<String, String> args = new HashMap<String, String>();
		must(TokenType.LBR);
		
		while(peek().type != TokenType.RBR) {
			String aType = must(TokenType.ID).text;
			String aName = must(TokenType.ID).text;
			args.put(aName, aType);
			if(peek().type != TokenType.RBR)
				must(TokenType.COLOM);
		}
		must(TokenType.RBR);
		Statement body = parseStatement();
		
		return new FunctionStatement(retType, name, args, body);
	}
	private Statement parseStatementBlock() {
		StatementBlock block = new StatementBlock();
		
		must(TokenType.LCBR);
		while(peek().type != TokenType.RCBR) {
			block.add(parseStatement());
		}
		must(TokenType.RCBR);
		
		return block;
	}

	private Statement parseIfStatement() {
		must(TokenType.IF);
		must(TokenType.LBR);
		Expression cond = parseExpression();
		must(TokenType.RBR);
		Statement trueStatement = parseStatement();
		Statement falseStatement = new StatementBlock();
		if(peek().type == TokenType.ELSE) {
			read();
			falseStatement = parseStatement();
		}
		
		return new IfStatement(cond, trueStatement, falseStatement);
	}

	private Statement parsePrintStatement() {
		must(TokenType.PRINT);
		Expression expression = parseExpression();
		must(TokenType.SEMI);
		return new PrintStatement(expression);
	}

	public Expression parseExpression() {
		
		return bool();
		
	}
	
	private Expression bool() {
		Expression acc = additive();
		while(
				peek().type == TokenType.EQEQ || 
				peek().type == TokenType.LT ||
				peek().type == TokenType.GT 
			) 
			
			switch(read().type) {
				case EQEQ:
					acc = new BinaryExpression(BinaryExpression.Type.EQEQ, acc, additive());
					break;
					
				case LT:
					acc = new BinaryExpression(BinaryExpression.Type.LESS, acc, additive());
					break;
					
				case GT:
					acc = new BinaryExpression(BinaryExpression.Type.GREATER, acc, additive());
					break;
					
				default:
					break;
			}
		return acc;
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
		if(peek().type == TokenType.NUMBER)
			return new ConstantExpression(Float.parseFloat(must(TokenType.NUMBER).text));
		if(peek().type == TokenType.ID)
			return new VariableExpression(must(TokenType.ID).text);
		if(peek().type == TokenType.LBR) {
			must(TokenType.LBR);
			Expression expression = parseExpression();
			must(TokenType.RBR);
			return expression;
		}
		must(null);
		return null;
	}
	
	
	
	
	
	private Token must(TokenType type) {
		if(peek().type == type) {
			
			return read();
		}
		else
			throw new RuntimeException(String.format("Expected %s , has %s [%s]", type.name(), peek().type.name(),peek().text));
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
