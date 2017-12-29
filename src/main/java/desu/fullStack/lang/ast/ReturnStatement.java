package desu.fullStack.lang.ast;

public class ReturnStatement implements Statement {
	private Expression expression;

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return expression;
	}
}
