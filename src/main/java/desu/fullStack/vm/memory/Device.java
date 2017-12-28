package desu.fullStack.vm.memory;

public interface Device {
	int getBase();
	int getSize();
	int get(int address);
	void set(int address, int value);
}
