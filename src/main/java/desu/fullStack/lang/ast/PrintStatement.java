package desu.fullStack.lang.ast;

public class PrintStatement implements Statement{
	private Expression expression;

	public PrintStatement(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return expression;
	}
}
