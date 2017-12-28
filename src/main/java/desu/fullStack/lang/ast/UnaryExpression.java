package desu.fullStack.lang.ast;

public class UnaryExpression implements Expression {
	public enum Type {
		MINUS
	}
	
	private Type type;
	private Expression op;
	public UnaryExpression(Type type, Expression op) {
		this.type = type;
		this.op = op;
	}
	
	public Type getType() {
		return type;
	}
	
	public Expression getOp() {
		return op;
	}
	
	@Override
	public String toString() {
		return "-("+op.toString()+")";
	}	

}
