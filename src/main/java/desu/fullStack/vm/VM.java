package desu.fullStack.vm;

import static desu.fullStack.vm.VMOpCodes.*;

import java.util.Arrays;

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
	
	public void execute(BytecodeProgram program) {
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
		
		switch (instruction) {
			case PUSH:
				tmp[0] = prog[ip++];
				stack[++sp] = tmp[0];
				break;
			case PRINTI:
				System.out.printf("VM: %d\n", stack[sp--]);
				break;
			case HALT:
				halt = true;
				break;
			case ADDI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] + tmp[0];
				break;
			case SUBI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] - tmp[0];
				break;
			case MULI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] * tmp[0];
				break;
			case DIVI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] / tmp[0];
				break;
			case LTI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] < tmp[0] ? 1 : 0;
				break;
			case GTI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] > tmp[0] ? 1 : 0;
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
			case EQI:
				tmp[0] = stack[sp--];
				tmp[1] = stack[sp--];
				stack[++sp] = tmp[1] == tmp[0] ? 1 : 0;
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

		default:
			throw new RuntimeException(String.format("Instruction not implemented: %d", instruction));
			
		}
		
	}
	
	
	
	
}
