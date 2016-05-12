package CompilerPackage;

import java.io.IOException;
import java.util.Stack;

import OwnExceptions.NoDigitException;

public class ExpressionParser {
	private Cradle base;
	public ExpressionParser() throws IOException{
		base = new Cradle();
	}
	public ExpressionParser(Cradle c) {
		base = c;
	}
	
	/**
	 * Parse and translate an Math expression
	 */
	public void Term() throws NoDigitException, IOException {
		base.EmitLn("MOVE #" + CharToString(base.GetNum()) + ",D0");
	}
	
	public void Expression() throws NoDigitException, IOException
	{
		Term();	
		base.EmitLn("MOVE D0,D1");
		switch (base.look) {
		case '+':
			Add();
			break;
		case '-':
			Subtract();
			break;
		default:
			base.Expected("Addop");
			break;
		}	
	}
	
	/**
	 * add procedure
	 * @throws IOException 
	 * @throws NoDigitException 
	 */
	public void Add() throws IOException, NoDigitException {
		base.Match('+');
		Term();
		base.EmitLn("ADD D1,D0");
	}
	
	/**
	 * subtract procedure
	 * @return
	 * @throws IOException 
	 * @throws NoDigitException 
	 */
	public void Subtract() throws IOException, NoDigitException {
		base.Match('-');
		Term();
		base.EmitLn("SUBTRACT D1,D0");
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
		
		parser.Expression();
	}
}
