package desu.fullStack.vm;

public class BytecodeProgram {
	public int[] program;
	public int stackSize;
	public int startAddress;
	
	public BytecodeProgram(int[] program, int stackSize, int startAddress) {
		this.program = program;
		this.stackSize = stackSize;
		this.startAddress = startAddress;
	}
	
	
	
}
