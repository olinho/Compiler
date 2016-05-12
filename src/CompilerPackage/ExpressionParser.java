package CompilerPackage;

import java.io.IOException;

import OwnExceptions.NoDigitException;

public class ExpressionParser {
	private Cradle base;
	public ExpressionParser(){
		base = new Cradle();
	}
	public ExpressionParser(Cradle c) {
		base = c;
	}
	public void Expression() throws NoDigitException, IOException {
		base.EmitLn("MOVE #" + CharToString(base.GetNum()) + ",D0");
	}
	
	
	// ***********************
	// own methods
	public String CharToString(char c){
		return String.valueOf(c);
	}
	
	// ***********************
	public static void main(String[] args) throws IOException, NoDigitException {
		ExpressionParser parser = new ExpressionParser();
		Cradle cradle = parser.base;
		
		cradle.Init();
		parser.Expression();
	}
}
