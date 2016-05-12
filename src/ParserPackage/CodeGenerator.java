package ParserPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class CodeGenerator {
	
	private Tokenizer tokenizer;
	private Stack<Object> stack;
	
	
	public CodeGenerator() {
		tokenizer = new Tokenizer();
		stack = new Stack<Object>();
	}
	
	
	public String ReadExpression() throws IOException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		return scanner.readLine();
	}
}
