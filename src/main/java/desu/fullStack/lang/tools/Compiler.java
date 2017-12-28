package desu.fullStack.lang.tools;

import desu.fullStack.lang.ast.BinaryExpression;
import desu.fullStack.lang.ast.ConstantExpression;
import desu.fullStack.lang.ast.Expression;
import desu.fullStack.lang.ast.PrintStatement;
import desu.fullStack.lang.ast.Statement;
import desu.fullStack.lang.ast.UnaryExpression;

public class Compiler {
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
				buffer.append("addi\n");
				break;
			case SUB:
				buffer.append("subi\n");
				break;
			case MUL:
				buffer.append("muli\n");
				break;
			case DIV:
				buffer.append("divi\n");
				break;
		}
		
		return buffer.toString();
	}

	private String compileUnaryExpression(UnaryExpression expression) {
		
		switch (expression.getType()) {
			case MINUS:
				return compileExpression(expression.getOp()) + "push -1\nmuli\n";
		}
		
		return null;
	}

	private String compileConstantExpression(ConstantExpression expression) {
		return "push " + expression.getValue() + "\n";
	}

	public String compileStatement(Statement statement) {
		
		if(statement instanceof PrintStatement)
			return compilePrintStatement((PrintStatement) statement);
		
		throw new RuntimeException("Compiling type " + statement.getClass().getName() + " not implemented!");
	}

	private String compilePrintStatement(PrintStatement statement) {
		
		return compileExpression(statement.getExpression()) + "printi\n";
	}
	
}
