package desu.fullStack.lang.ast;

import java.util.List;

public class FunctionCallStatement implements Statement {
	private String name;
	private List<Expression> args;
	
	
	public FunctionCallStatement(String name, List<Expression> args) {
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
