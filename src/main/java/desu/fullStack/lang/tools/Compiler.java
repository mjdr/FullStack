package desu.fullStack.lang.tools;

import desu.fullStack.lang.ast.BinaryExpression;
import desu.fullStack.lang.ast.ConstantExpression;
import desu.fullStack.lang.ast.Expression;
import desu.fullStack.lang.ast.FunctionStatement;
import desu.fullStack.lang.ast.IfStatement;
import desu.fullStack.lang.ast.PrintStatement;
import desu.fullStack.lang.ast.Statement;
import desu.fullStack.lang.ast.StatementBlock;
import desu.fullStack.lang.ast.UnaryExpression;
import desu.fullStack.lang.ast.VariableAssignStatement;
import desu.fullStack.lang.ast.VariableExpression;
import desu.fullStack.lang.ast.VariableInitStatement;

public class Compiler {
	private static long ID = 0;
	
	public String compileExpression(CompilerEnv env, Expression expression) {
		
		if(expression instanceof ConstantExpression)
			return compileConstantExpression(env, (ConstantExpression) expression);
		if(expression instanceof UnaryExpression)
			return compileUnaryExpression(env, (UnaryExpression) expression);
		if(expression instanceof BinaryExpression)
			return compileBinaryExpression(env, (BinaryExpression) expression);
		if(expression instanceof VariableExpression)
			return compileVariableExpression(env, (VariableExpression) expression);
		
		throw new RuntimeException("Compiling type " + expression.getClass().getName() + " not implemented!");
		
	}

	private String compileVariableExpression(CompilerEnv env, VariableExpression expression) {
		int id = env.getLocalVarId(expression.getName());
		if(id == -1) throw new RuntimeException(String.format("Local variable %s not defined in function %s", 
				expression.getName(),env.current.getName()));
		
		return "lvar_get " + id + "\n";
	}

	private String compileBinaryExpression(CompilerEnv env, BinaryExpression expression) {
		
		StringBuffer buffer = new StringBuffer();

		buffer.append(compileExpression(env, expression.getOp1()));
		buffer.append(compileExpression(env, expression.getOp2()));
		
		switch (expression.getType()) {
			case ADD:
				buffer.append("add\n");
				break;
			case SUB:
				buffer.append("sub\n");
				break;
			case MUL:
				buffer.append("mul\n");
				break;
			case DIV:
				buffer.append("div\n");
				break;
			case EQEQ:
				buffer.append("eq\n");
				break;
			case LESS:
				buffer.append("lt\n");
				break;
			case GREATER:
				buffer.append("gt\n");
				break;
				
		}
		
		return buffer.toString();
	}

	private String compileUnaryExpression(CompilerEnv env, UnaryExpression expression) {
		
		switch (expression.getType()) {
			case MINUS:
				return compileExpression(env, expression.getOp()) + "push -1\nmul\n";
		}
		
		return null;
	}

	private String compileConstantExpression(CompilerEnv env, ConstantExpression expression) {
		return "push " + expression.getValue() + "\n";
	}

	public String compileStatement(CompilerEnv env, Statement statement) {
		
		if(statement instanceof PrintStatement)
			return compilePrintStatement(env, (PrintStatement) statement);
		if(statement instanceof IfStatement)
			return compileIfStatement(env, (IfStatement) statement);
		if(statement instanceof StatementBlock)
			return compileStatementBlock(env, (StatementBlock) statement);
		if(statement instanceof FunctionStatement)
			return compileFunctionStatement(env, (FunctionStatement) statement);
		if(statement instanceof VariableInitStatement)
			return compileVariableInitStatement(env, (VariableInitStatement) statement);
		if(statement instanceof VariableAssignStatement)
			return compileVariableAssignStatement(env, (VariableAssignStatement) statement);
		
		throw new RuntimeException("Compiling type " + statement.getClass().getName() + " not implemented!");
	}

	private String compileVariableAssignStatement(CompilerEnv env, VariableAssignStatement statement) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(compileExpression(env, statement.getExpression()));
		int id = env.getLocalVarId(statement.getName());
		if(id == -1) throw new RuntimeException(String.format("Local variable %s not defined in function %s", 
				statement.getName(),env.current.getName()));
		
		buffer.append("lvar_set " + id + "\n");
		
		return buffer.toString();
	}

	private String compileVariableInitStatement(CompilerEnv env, VariableInitStatement statement) {
		env.localVars.add(statement);
		return "";
	}

	private String compileFunctionStatement(CompilerEnv env, FunctionStatement s) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("#").append(s.getName()).append('\n');
		env.current = s;
		String body = compileStatement(env, s.getBody());
		buffer.append("local_vars " + env.localVars.size() + "\n");
		buffer.append(body);
		env.localVars.clear();
		env.current = null;
		buffer.append("push 0\n");
		buffer.append("ret\n");
		
		return buffer.toString();
	}

	private String compileStatementBlock(CompilerEnv env, StatementBlock s) {
		StringBuffer buffer = new StringBuffer();
		for(Statement st : s)
			buffer.append(compileStatement(env, st));
		
		return buffer.toString();
	}

	private String compileIfStatement(CompilerEnv env, IfStatement s) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(compileExpression(env, s.getCondition()));
		long id = ID;
		buffer.append("br_true #if_true_" + id + "\n");
		buffer.append(compileStatement(env, s.getFalseStatement()));
		buffer.append("goto #if_end_" + id + "\n");
		buffer.append("#if_true_" + id + "\n");
		buffer.append(compileStatement(env, s.getTrueStatement()));
		buffer.append("#if_end_" + id + "\n");
		ID++;
		
		return buffer.toString();
	}

	private String compilePrintStatement(CompilerEnv env, PrintStatement statement) {
		return compileExpression(env, statement.getExpression()) + "print\n";
	}
	
}
