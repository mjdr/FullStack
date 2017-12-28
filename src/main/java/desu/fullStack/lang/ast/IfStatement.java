package desu.fullStack.lang.ast;

public class IfStatement implements Statement {
	private Expression condition;
	private Statement trueStatement;
	private Statement falseStatement;
	
	public IfStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = falseStatement;
	}
	
	

}
