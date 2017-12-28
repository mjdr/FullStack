package desu.fullStack.lang.ast;

public class VariableAssignStatement implements Statement {
	private String name;
	private Expression expression;
	
	public VariableAssignStatement(String name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return expression;
	}
	public String getName() {
		return name;
	}
	
}
