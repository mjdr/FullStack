package desu.fullStack.lang.tools;

import java.util.ArrayList;
import java.util.List;

import desu.fullStack.lang.ast.FunctionStatement;
import desu.fullStack.lang.ast.VariableInitStatement;

public class CompilerEnv {
	
	public FunctionStatement current;
	public List<VariableInitStatement> localVars;
	
	private CompilerEnv() {
		localVars = new ArrayList<VariableInitStatement>();
	}
	
	
	public static CompilerEnv create() {
		return new CompilerEnv();
	}


	public int getLocalVarId(String name) {
		for(int i = 0;i < localVars.size();i++)
			if(localVars.get(i).getName().equals(name))
				return i;
		return -1;
	}
}
