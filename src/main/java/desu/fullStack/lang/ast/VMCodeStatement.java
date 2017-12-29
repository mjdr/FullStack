package desu.fullStack.lang.ast;

public class VMCodeStatement implements Statement {
	private String code;

	public VMCodeStatement(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

}
