package desu.fullStack.lang.ast;

import java.util.List;

public class FunctionCallExpression implements Expression {
	private String name;
	private List<Expression> args;
	
	
	public FunctionCallExpression(String name, List<Expression> args) {
		this.name = name;
		this.args = args;
	}
	
	public List<Expression> getArgs() {
		return args;
	}
	
	public String getName() {
		return name;
	}
}
