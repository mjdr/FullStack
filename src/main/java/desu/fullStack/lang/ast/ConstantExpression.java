package desu.fullStack.lang.ast;

public class ConstantExpression implements Expression {
	private int value;

	public ConstantExpression(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value+"";
	}
	
}
