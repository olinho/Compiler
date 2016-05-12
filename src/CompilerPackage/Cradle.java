package CompilerPackage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import OwnExceptions.NoNameException;
import OwnExceptions.NoDigitException;

public class Cradle {
	public char look;
	private BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
	
	public Cradle() throws IOException {
		Init();
	}
	
	public void GetChar() throws IOException{
		look = (char) scanner.read();
	}
	
	public void Error(String s){
		System.out.println("error: " + s);
	}
	
	/**
	 * write an error and halt program
	 */
	public void Abort(String s){
		Error(s);
		System.exit(1);		// halt program
	}
	
	/**
	 * report what was expected 
	 */
	public void Expected(String s){
		Abort(s + " expected");
	}
	
	/**
	 * check if input char is equal to `look` char
	 */
	public void Match(char x) throws IOException{
		if (look == x) GetChar();
		else Expected(CharToString(x));
	}
			
	public boolean IsAlpha(char c) {
		String s = String.valueOf(c);
		return s.matches("[a-zA-Z]");
	}
	
	public boolean IsDigit(char c) {
		String s = String.valueOf(c);
		return s.matches("[0-9]");
	}
	
	
	/**
	 * get an identifier
	 * @throws IOException 
	 */
	public char GetName() throws NoNameException, IOException 
	{
		if (!IsAlpha(look)) {
			Expected("Name");
			throw new NoNameException("MyException: Expecting name");
		}
		char lastChar = Character.toUpperCase(look);
		GetChar(); 	// read next char
		return lastChar;
	}
	
	/**
	 * get a number
	 * @throws IOException 
	 */
	public char GetNum() throws NoDigitException, IOException
	{
		if (!IsDigit(look))
		{
			Expected("Digit");
			throw new NoDigitException("MyException: Expecting digit");
		}
		char lastChar = look;
		GetChar(); 	// read next char
		return lastChar;
	}
	
	/**
	 * output a string with tab
	 */
	public void Emit(String s) {
		System.out.print("\t"+s);
	}
	
	/**
	 * output a string with CRLF
	 */
	public void EmitLn(String s){
		System.out.println(s);
	}
	
	
	public void Init() throws IOException{
		GetChar();
	}
	
	// **********************
	// my own methods
	public char ShowLook() {
		return look;
	}
	
	public String CharToString(char c){
		return String.valueOf(c);
	}
	// **********************

}
