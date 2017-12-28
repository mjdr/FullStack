package desu.fullStack.lang.ast;

public class BinaryExpression implements Expression {
	public enum Type {
		ADD,SUB,MUL,DIV,EQEQ,LESS,GREATER
	};
	
	private Type type;
	private Expression op1;
	private Expression op2;
	
	
	public BinaryExpression(Type type, Expression op1, Expression op2) {
		this.type = type;
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public Type getType() {
		return type;
	}
	
	public Expression getOp1() {
		return op1;
	}
	
	public Expression getOp2() {
		return op2;
	}
	
	@Override
	public String toString() {
		switch(type) {
		case ADD: return "( (" + op1 + ") + (" + op2 + ") )";
		case SUB: return "( (" + op1 + ") - (" + op2 + ") )";
		case MUL: return "( (" + op1 + ") * (" + op2 + ") )";
		case DIV: return "( (" + op1 + ") / (" + op2 + ") )";
		default: return "(nil)";
		}
	}

}
