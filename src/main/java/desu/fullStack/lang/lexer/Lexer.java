package desu.fullStack.lang.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

public class Lexer {
	
	private static final String oneChar = "+-*/();";
	private static final TokenType[] oneCharT = {
			TokenType.PLUS,
			TokenType.MINUS,
			TokenType.STAR,
			TokenType.SLASH,
			TokenType.LBR,
			TokenType.RBR,
			TokenType.SEMI,
	}; 
	private final Map<String, TokenType> keyWords = new HashMap<String, TokenType>();
	
	
	private String source;
	private int index;
	private List<Token> tokens;
	private int row;
	
	
	public Lexer() {
		keyWords.put("print", TokenType.PRINT);
	}
	
	public List<Token> tokenize(String source){
		tokens = new ArrayList<Token>();
		index = 0;
		row = 0;
		this.source = source;
		
		while(index < source.length()) 
			process();
		
		
		return tokens;
	}
	
	
	private void process() {
		if(peek() == '\n' || peek() == '\t' || peek() == ' ') {
			read();
			return;
		}
		
		if(Character.isDigit(peek())) {
			tokenizeNumber();
			return;
		}
		
		if(keyWords.keySet().stream().filter(k->isNext(k)).count() > 0) {
			tokenizeKeyword();
			return;
		}
		
		if(oneChar.indexOf(peek()) != -1) {
			tokens.add(new Token(oneCharT[oneChar.indexOf(read())]));
			return;
		}
		
		throw new RuntimeException("Unknow char: '" + peek() + "'");
	}

	private void tokenizeKeyword() {
		for(Entry<String, TokenType> en : keyWords.entrySet()) {
			if(isNext(en.getKey())){
				index += en.getKey().length();
				tokens.add(new Token(en.getValue()));
				return;
			}
		}
		
	}

	private void tokenizeNumber() {
		StringBuffer buffer = new StringBuffer();
		
		while(Character.isDigit(peek()))
			buffer.append(read());
		
		tokens.add(new Token(TokenType.NUMBER, buffer.toString()));
	}


	private boolean isNext(String str) {
		for(int i = 0;i < str.length();i++)
			if(str.charAt(i) != peek(i))
				return false;
		return true;
	}
	private char peek() {
		return peek(0);
	}
	private char peek(int i) {
		if(index + i >= source.length()) return '\0';
		return source.charAt(index + i);
	}
	
	private char read() {
		if(index >= source.length()) throw new RuntimeException("Unexpected eof");
		char c = source.charAt(index++);
		if(c == '\n') row++;
		return c;
	}

}
