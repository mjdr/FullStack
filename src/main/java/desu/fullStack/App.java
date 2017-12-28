package desu.fullStack;

import desu.fullStack.vm.BytecodeProgram;
import desu.fullStack.vm.Parser;
import desu.fullStack.vm.VM;
import static desu.fullStack.vm.VMOpCodes.*;

import java.io.File;
import java.io.IOException;


public class App {
    public static void main( String[] args ){
    	VM vm = new VM();
    	Parser parser = new Parser();
    	
    	
    	try {
			BytecodeProgram program = parser.parse(new File("res/prog.vm"));
	    	vm.execute(program);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
    	
    }
}
