package desu.fullStack.vm;

public abstract class VMOpCodes {
	public static final int HALT = 0;
	public static final int PUSH = 1;
	public static final int POP = 2;
	public static final int ADD = 3;
	public static final int SUB = 4;
	public static final int MUL = 5;
	public static final int DIV = 6;
	public static final int GOTO = 7;
	public static final int BR_TRUE = 8;
	public static final int SCOPY = 9;
	public static final int LT = 10;
	public static final int GT = 11;
	public static final int EQ = 12;
	public static final int RET = 13;
	public static final int CALL = 14;
	public static final int ARG = 15;
	public static final int LOCAL_VARS = 16;
	public static final int LVAR_GET = 17;
	public static final int LVAR_SET = 18;
	public static final int MEM_GET = 19;
	public static final int MEM_SET = 20;
	
	
	public static final int[] argsCount = {
			0, //HALT 
			1, //PUSH
			0, //POP
			0, //ADD
			0, //SUB
			0, //MUL
			0, //DIV
			1, //GOTO
			1, //BR_TRUE
			1, //SCOPY	
			0, //LT
			0, //GT	
			0, //EQ
			0, //RET	
			2, //CALL
			1, //ARG
			1, //LOCAL_VARS
			1, //LVAR_GET
			1, //LVAR_SET
			0, //MEM_GET
			0, //MEM_SET
	};
	
	public static final String[] names = {
			"HALT", 
			"PUSH",
			"POP",
			"ADD",
			"SUB",
			"MUL",
			"DIV",
			"GOTO",
			"BR_TRUE",
			"SCOPY",
			"LT",
			"GT",
			"EQ",
			"RET",
			"CALL",
			"ARG",
			"LOCAL_VARS",
			"LVAR_GET",
			"LVAR_SET",
			"MEM_GET",
			"MEM_SET"
	};
	
	public static int toIndex(String name) {
		for(int i = 0;i < names.length;i++)
			if(names[i].equals(name)) return i;
		return -1;
	}
	
}
