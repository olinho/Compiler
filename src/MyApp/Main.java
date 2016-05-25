package MyApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import Model.Tokenizer;
import Service.CodeGenerator;

public class Main {

	public static void main(String[] args) throws IOException {
		
		CodeGenerator cg = new CodeGenerator();
		Tokenizer tokenizer = new Tokenizer();
		
		cg.CreateSampleTree();
		cg.ShowTree();
		
		System.out.println(cg.RecursiveRead());
		
		System.out.println(cg.CalculateTree());
		
		CodeGenerator gen2 = new CodeGenerator();
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String str = scanner.readLine();
		
		tokenizer.tokenize(str);
		for (Tokenizer.Token tok : tokenizer.getTokens())
		{
			if (tok.token == 7)
			{
				gen2.AddValueToTree(Integer.parseInt(tok.sequence));
			}
			else if (tok.token == 4)
			{
				if (tok.sequence.equals("+")){
					gen2.AddExpressionToTree("add");
				}
				else {
					gen2.AddExpressionToTree("sub");
				}
			}
			else if (tok.token == 5)
			{
				if (tok.sequence.equals("*")){
					gen2.AddExpressionToTree("mul");
				}
				else {
					gen2.AddExpressionToTree("div");
				}
			}
		}
		gen2.ShowTree();
		
		System.out.println(gen2.RecursiveRead());
		
		System.out.println(gen2.CalculateTree());
	}
}