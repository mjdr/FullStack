package desu.fullStack.vm;

import static desu.fullStack.vm.VMOpCodes.*;

import java.util.Arrays;

import desu.fullStack.vm.memory.Memory;

public class VM {
	//Stats
	private long steps;
	//Registers
	private int sp;
	private int ip;
	private int fp;
	private boolean halt;
	
	private int[] stack;
	private int[] prog;
	
	private Memory memory;
	
	public void execute(BytecodeProgram program) {
		memory = new Memory(10000);
		sp = -1;
		fp = 0;
		halt = false;
		ip = program.startAddress;
		prog = program.program;
		stack = new int[program.stackSize];
		
		steps = 0;
		
		System.out.println(Arrays.toString(prog));
		
		while(!halt) {
			steps++;
			/*System.out.print('[');
			for(int i = 0;i <= sp;i++)
				System.out.print(stack[i] + " ");
			System.out.print("]\n");*/
			step();
		}
		
		System.out.println("VM Steps: " + steps);
		
	}
	
	private void step() {
		int instruction = prog[ip++];

		int[] tmp = new int[5]; 
		float[] ftmp = new float[5]; 
		
		switch (instruction) {
			case PUSH:
				tmp[0] = prog[ip++];
				stack[++sp] = tmp[0];
				break;
			case HALT:
				halt = true;
				break;
			case ADD:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = i(ftmp[1] + ftmp[0]);
				break;
			case SUB:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = i(ftmp[1] - ftmp[0]);
				break;
			case MUL:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = i(ftmp[1] * ftmp[0]);
				break;
			case DIV:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = i(ftmp[1] + ftmp[0]);
				break;
			case LT:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = ftmp[1] < ftmp[0] ? 1 : 0;
				break;
			case GT:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = ftmp[1] > ftmp[0] ? 1 : 0;
				break;
			case EQ:
				ftmp[0] = f(stack[sp--]);
				ftmp[1] = f(stack[sp--]);
				stack[++sp] = ftmp[1] == ftmp[0] ? 1 : 0;
				break;
			case SCOPY:
				tmp[0] = stack[sp - prog[ip++]];
				stack[++sp] = tmp[0];
				break;
			case GOTO:
				ip = prog[ip];
				break;
			case POP:
				sp--;
				break;
			case BR_TRUE:
				if(stack[sp--] != 0)
					ip = prog[ip];
				else ip++;
				break;
			case CALL:
				tmp[0] = prog[ip++]; //addr
				tmp[1] = prog[ip++]; //argc
				stack[++sp] = tmp[1];
				stack[++sp] = fp;
				stack[++sp] = ip;
				fp = sp;
				ip = tmp[0];
				break;
			case RET:	
				tmp[0] = stack[sp--];
				sp = fp;
				ip = stack[sp--];
				fp = stack[sp--];
				sp -= stack[sp] + 1;
				stack[++sp] = tmp[0];
				break;
			case ARG:
				tmp[0] = stack[fp - (prog[ip++] + 3)];
				stack[++sp] = tmp[0];
				break;
			case LOCAL_VARS:
				sp += prog[ip++];
				break;
			case LVAR_GET:
				tmp[0] = prog[ip++];
				stack[++sp] = stack[fp + 1 + tmp[0]];
				break;
			case LVAR_SET:
				tmp[0] = prog[ip++];
				tmp[1] = stack[sp--];
				stack[fp + tmp[0] + 1] = tmp[1];
				break;
				
			case MEM_GET:
				tmp[0] = fi(stack[sp--]);
				stack[++sp] = memory.get(tmp[0]);
				break;
			case MEM_SET:
				tmp[0] = stack[sp--];
				tmp[1] = fi(stack[sp--]);
				memory.set(tmp[1], tmp[0]);
				break;
			

		default:
			throw new RuntimeException(String.format("Instruction not implemented: %d", instruction));
			
		}
		
	}
	
	private static float f(int i) {
		return Float.intBitsToFloat(i);
	}
	private static int fi(int i) {
		return (int)Float.intBitsToFloat(i);
	}
	private static int i(float f) {
		return Float.floatToIntBits(f);
	}
	
	
	
}
