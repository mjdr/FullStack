package desu.fullStack.vm;

public abstract class VMOpCodes {
	public static final int HALT = 0;
	public static final int PUSH = 1;
	public static final int POP = 2;
	public static final int ADDI = 3;
	public static final int SUBI = 4;
	public static final int MULI = 5;
	public static final int DIVI = 6;
	public static final int GOTO = 7;
	public static final int BR_TRUE = 8;
	public static final int PRINTI = 9;
	public static final int SCOPY = 10;
	public static final int LTI = 11;
	public static final int GTI = 12;
	public static final int EQI = 13;
	public static final int RET = 14;
	public static final int CALL = 15;
	public static final int ARG = 16;
	public static final int LOCAL_VARS = 17;
	public static final int LVAR_GET = 18;
	public static final int LVAR_SET = 19;
	
	
	public static final int[] argsCount = {
			0, //HALT 
			1, //PUSH
			0, //POP
			0, //ADDI
			0, //SUBI
			0, //MULI
			0, //DIVI
			1, //GOTO
			1, //BR_TRUE
			0, //PRINTI
			1, //SCOPY	
			0, //LTI
			0, //GTI	
			0, //EQI
			0, //RET	
			2, //CALL
			1, //ARG
			1, //LOCAL_VARS
			1, //LVAR_GET
			1, //LVAR_SET
	};
	
	public static final String[] names = {
			"HALT", 
			"PUSH",
			"POP",
			"ADDI",
			"SUBI",
			"MULI",
			"DIVI",
			"GOTO",
			"BR_TRUE",
			"PRINTI",
			"SCOPY",
			"LTI",
			"GTI",
			"EQI",
			"RET",
			"CALL",
			"ARG",
			"LOCAL_VARS",
			"LVAR_GET",
			"LVAR_SET",
	};
	
	public static int toIndex(String name) {
		for(int i = 0;i < names.length;i++)
			if(names[i].equals(name)) return i;
		return -1;
	}
	
}
