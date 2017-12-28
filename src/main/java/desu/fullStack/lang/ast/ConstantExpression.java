package desu.fullStack.lang.ast;

public class ConstantExpression implements Expression {
	private float value;

	public ConstantExpression(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value+"";
	}
	
}
