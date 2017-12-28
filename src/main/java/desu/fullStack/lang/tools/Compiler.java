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

public class Compiler {
	private static long ID = 0;
	
	public String compileExpression(Expression expression) {
		
		if(expression instanceof ConstantExpression)
			return compileConstantExpression((ConstantExpression) expression);
		if(expression instanceof UnaryExpression)
			return compileUnaryExpression((UnaryExpression) expression);
		if(expression instanceof BinaryExpression)
			return compileBinaryExpression((BinaryExpression) expression);
		
		throw new RuntimeException("Compiling type " + expression.getClass().getName() + " not implemented!");
		
	}

	private String compileBinaryExpression(BinaryExpression expression) {
		
		StringBuffer buffer = new StringBuffer();

		buffer.append(compileExpression(expression.getOp1()));
		buffer.append(compileExpression(expression.getOp2()));
		
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

	private String compileUnaryExpression(UnaryExpression expression) {
		
		switch (expression.getType()) {
			case MINUS:
				return compileExpression(expression.getOp()) + "push -1\nmul\n";
		}
		
		return null;
	}

	private String compileConstantExpression(ConstantExpression expression) {
		return "push " + expression.getValue() + "\n";
	}

	public String compileStatement(Statement statement) {
		
		if(statement instanceof PrintStatement)
			return compilePrintStatement((PrintStatement) statement);
		if(statement instanceof IfStatement)
			return compileIfStatement((IfStatement) statement);
		if(statement instanceof StatementBlock)
			return compileStatementBlock((StatementBlock) statement);
		if(statement instanceof FunctionStatement)
			return compileFunctionStatement((FunctionStatement) statement);
		
		throw new RuntimeException("Compiling type " + statement.getClass().getName() + " not implemented!");
	}

	private String compileFunctionStatement(FunctionStatement s) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("#").append(s.getName()).append('\n');
		buffer.append(compileStatement(s.getBody()));
		buffer.append("push 0\n");
		buffer.append("ret\n");
		
		return buffer.toString();
	}

	private String compileStatementBlock(StatementBlock s) {
		StringBuffer buffer = new StringBuffer();
		for(Statement st : s)
			buffer.append(compileStatement(st));
		
		return buffer.toString();
	}

	private String compileIfStatement(IfStatement s) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(compileExpression(s.getCondition()));
		long id = ID;
		buffer.append("br_true #if_true_" + id + "\n");
		buffer.append(compileStatement(s.getFalseStatement()));
		buffer.append("goto #if_end_" + id + "\n");
		buffer.append("#if_true_" + id + "\n");
		buffer.append(compileStatement(s.getTrueStatement()));
		buffer.append("#if_end_" + id + "\n");
		ID++;
		
		return buffer.toString();
	}

	private String compilePrintStatement(PrintStatement statement) {
		return compileExpression(statement.getExpression()) + "print\n";
	}
	
}
