package desu.fullStack.lang.ast;

public class VariableInitStatement implements Statement {
	private String type;
	private String name;
	
	public VariableInitStatement(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
}
