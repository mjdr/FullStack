package desu.fullStack.lang.lexer;

public class Token {
	public TokenType type;
	public String text;

	public Token(TokenType type, String text) {
		this.type = type;
		this.text = text;
	}

	public Token(TokenType type) {
		this(type, "");
	}
	
	@Override
	public String toString() {
		return type.name() + " : " + text;
	}
	
}
