package desu.fullStack.lang.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionStatement implements Statement {
	private String returnType;
	private String name;
	private Map<String, String> args; // <Name,Type>
	private List<String> argsNames;
	private Statement body;
	
	public FunctionStatement(String returnType, String name, Map<String, String> args, List<String> argsNames, Statement body) {
		this.returnType = returnType;
		this.name = name;
		this.args = args;
		this.body = body;
		this.argsNames = argsNames;
	}

	public String getReturnType() {
		return returnType;
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getArgs() {
		return args;
	}

	public Statement getBody() {
		return body;
	}
	
	public List<String> getArgsNames() {
		return argsNames;
	}
	
	@Override
	public String toString() {
		return String.format("Function: %s , args count: %d", name , args.size());
	}

}
