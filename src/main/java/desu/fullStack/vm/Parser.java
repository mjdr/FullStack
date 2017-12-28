package desu.fullStack.vm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Parser {
	
	private static class Value{
		private enum Type {
			Label,
			Const
		}
		Type type;
		int intValue;
		String stringValue;
		
		public Value(int intValue) {
			this.type = Type.Const;
			this.intValue = intValue;
		}
		public Value(String stringValue) {
			this.type = Type.Label;
			this.stringValue = stringValue;
		}
		@Override
		public String toString() {
			return type.name() +" "+ (type == Type.Label ? stringValue : (""+intValue));
		}
		
	}
	
	private static class Command {
		String name;
		List<Value> values;
		
		public Command(String name, Value... values) {
			this.name = name;
			this.values = Arrays.asList(values);
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(name + "(\n");
			
			for(Value value : values)
				buffer.append("\t").append(value).append("\n");
			
			buffer.append(")");
			
			return buffer.toString();
		}
	}
	
	private List<Command> commands = new ArrayList<Parser.Command>();
	private Map<String, Integer> labels = new HashMap<String, Integer>();
	private int address;
	
	public BytecodeProgram parse(File file) throws IOException {
		
		commands.clear();
		labels.clear();
		address = 0;
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line;
		while((line = reader.readLine()) != null)
			processLine(line);
		reader.close();
		
		//commands.forEach(System.out::println);
		//System.out.println("------------------------------------");
		
		checkProgram();

		//commands.forEach(System.out::println);
		
		int[] codes = toBytecode(address);
		
		//System.out.println(Arrays.toString(codes));
		
		return new BytecodeProgram(codes, 1024, labels.get("#_start"));
	}

	private int[] toBytecode(int size) {
		int[] memory = new int[size];
		int ip = -1;
		for(Command command : commands) {
			int code = VMOpCodes.toIndex(command.name);
			memory[++ip] = code;
			for(Value value : command.values)
				memory[++ip] = value.intValue;
		}
		
		return memory;
	}

	private void checkProgram() {
		if(!labels.containsKey("#_start"))
			throw new RuntimeException("Can't find label _start");
		checkCommands();
	}

	private void checkCommands() {
		
		for(Command command : commands) {
			int code = VMOpCodes.toIndex(command.name);
			if(code == -1) throw new RuntimeException(String.format("Command %s not defiend", command.name));
			if(VMOpCodes.argsCount[code] != command.values.size()) {
				throw new RuntimeException(String.format("Command %s, expected args count %d , has %d", 
						command.name,
						VMOpCodes.argsCount[code],
						command.values.size()
				));
			}
			
			for(Value value : command.values) {
				if(value.type == Value.Type.Label) {
					if(!labels.containsKey(value.stringValue))
						throw new RuntimeException("Label not defined: " + value.stringValue);
					value.intValue = labels.get(value.stringValue);
					value.type = Value.Type.Const;
				}
			}
		}
		
	}

	private void processLine(String line) {
		line = line.trim();
		if(line.isEmpty()) return;
		String[] parts = line.split("\\s+");
		if(parts.length == 1 && parts[0].startsWith("#")) { //Push label
			labels.put(parts[0], address);
			return;
		}
		if(parts.length == 0) return;
		
		Value[] values = new Value[parts.length - 1];
		for(int i = 1;i < parts.length;i++) {
			if(parts[i].startsWith("#"))
				values[i - 1] = new Value(parts[i]);
			else
				values[i - 1] = new Value(Integer.parseInt(parts[i]));
		}
		address += parts.length;
		
		commands.add(new Command(parts[0].toUpperCase(), values));
			
		
	}
}
