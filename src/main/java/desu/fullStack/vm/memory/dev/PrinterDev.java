package desu.fullStack.vm.memory.dev;

import desu.fullStack.vm.memory.Device;

public class PrinterDev implements Device {

	private int mode;
	
	@Override
	public int getBase() {
		return 1024;
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public int get(int address) {
		return 0;
	}

	@Override
	public void set(int address, int value) {
		if(address == 0)
			mode = (int) Float.intBitsToFloat(value);
		if(address == 1) 
			print(value);
	}

	private void print(int value) {
		System.out.printf("Dev printer: %f\n", Float.intBitsToFloat(value));
	}

}
