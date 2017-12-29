package desu.fullStack.lang.lexer;

public enum TokenType {
	PLUS,
	MINUS,
	STAR,
	SLASH,
	NUMBER,
	LBR, // (
	RBR, // )
	LCBR, // {
	RCBR, // }
	SEMI, //;
	LT, // <
	GT, // >
	EQ, // =
	COLOM, //,
	EOF,
	
	//KEYWORDS
	IF,
	ELSE,
	EQEQ, // ==
	RETURN,
	
	
	ID,
	VM_CODE,
	
	
	
}
