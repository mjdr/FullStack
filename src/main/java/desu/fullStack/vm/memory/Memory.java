package desu.fullStack.vm.memory;

import java.util.ArrayList;
import java.util.List;

import desu.fullStack.vm.memory.dev.PrinterDev;

public class Memory {

	private int size;
	private int[] memory;
	private List<Device> devices;
	
	public Memory(int size) {
		this.size = size;
		memory = new int[this.size];
		devices = new ArrayList<Device>();
		
		devices.add(new PrinterDev());
	}
	
	public int get(int address) {
		for (Device device : devices) 
			if(inRange(device, address))
				return device.get(address - device.getBase());
		return memory[address];
	}
	public void set(int address, int value) {
		for (Device device : devices) 
			if(inRange(device, address)) {
				device.set(address - device.getBase(), value);
				return;
			}
		memory[address] = value;
	}
	
	private boolean inRange(Device dev, int address) {
		return address >= dev.getBase() && address <= dev.getBase() + dev.getSize();
	}
}
