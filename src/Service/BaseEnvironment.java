package Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Stack;


/**
 * Funkcja realizująca punkt 1. z wymagań
 * Środowisko uruchomieniowe
 * @author Olek
 *
 */
public class BaseEnvironment {
	
	private Stack<Integer> stack = new Stack<Integer>();
	BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
	LinkedList<String> expressions = new LinkedList<String>();
	
	public BaseEnvironment() {
		CreateExpressions();
	}
	
	public void CreateExpressions()
	{
		expressions.add("add");
		expressions.add("mul");
		expressions.add("sub");
		expressions.add("div");
		expressions.add("end");
		expressions.add("put");
	}
	
	
	public void ExpressionService() throws Exception
	{
		String expr;
		while (true)
		{
			expr = scanner.readLine();
			if (!IsCorrectExpr(expr)) 
				throw new Exception("Exception: unavailable expression ");
			
			if (IsPutExpr(expr))
			{
				AddToStack(GetValueFromPutExpr(expr));
			}
			else if (expr.equals("end"))
			{
				EndExprService();
				break;
			}
			else
			{
				ExecuteExpression(expr);
				
			}
			System.out.println("Stack content: " + stack);
		}
	}
	
	public void ExpressionService(String str) throws Exception
	{	
		int it = 0;
		int amountOfElements = str.split(" | ").length;
		System.out.println(str);
		String expr = str.split(" \\| ")[it];
		
		while (it < amountOfElements)
		{
			System.out.println(expr);
			if (!IsCorrectExpr(expr)) 
				throw new Exception("Exception: unavailable expression ");
			
			if (IsPutExpr(expr))
			{
				AddToStack(GetValueFromPutExpr(expr));
			}
			else if (expr.equals("end"))
			{
				EndExprService();
				System.out.println("Correct expression");
				break;
			}
			else
			{
				if (it-1 == amountOfElements)
				{
					System.out.println("Unavailable expression");
					throw new Exception("Exception: unavailable expression");
				}
				else
				{
					ExecuteExpression(expr);
				}
			}
			System.out.println("Stack content: " + stack);
			it++;
			expr = str.split(" \\| ")[it];
		}
		
	}
	
	
	/**
	 * return the value from put expression
	 */
	public int GetValueFromPutExpr(String putExpr)
	{
		return Integer.parseInt(putExpr.split(" ")[1]);
	}
	
	/**
	 * action after end expression read
	 */
	public void EndExprService()
	{
		if (stack.size() == 1)
		{
			System.out.println(GetFromStack());
		}
		else if (stack.size() == 0) 
			throw new StackOverflowError("Stack is empty.");
		else
			throw new StackOverflowError("Too many elements in stack.");
	}
	
	
	public void ExecuteExpression(String expr)
	{
		int [] pair = GetTwoElementsFromStack();
		int result;
		switch (expr) {
		case "add":
			result = pair[0] + pair[1];
			AddToStack(result);
			break;
		case "sub":
			result = pair[0] - pair[1];
			AddToStack(result);
			break;
		case "mul":
			result = pair[0] * pair[1];
			AddToStack(result);
			break;
		case "div":
			result = pair[0] / pair[1];
			AddToStack(result);
			break;
		default:
			break;
		}
		
	}
	
	public int[] GetTwoElementsFromStack()
	{
		int [] pair = new int[2];
		if (stack.size() < 2)
		{
			throw new StackOverflowError("Not enough elements");
		}
		else
		{
			pair[0] = GetFromStack();
			pair[1] = GetFromStack();
		}
		
		
		return pair;
	}
	
	public boolean IsCorrectExpr(String expr)
	{
		if (IsPutExpr(expr))
			return true;
		return expressions.contains(expr);
	}
	
	public boolean IsPutExpr(String s)
	{
		if (s.matches("put [0-9][0-9]*"))
		{
			return true;
		}
		return false;
	}

	public void AddToStack(int val){
		stack.push(val);
	}
	public int GetFromStack(){
		return stack.pop();
	}
	
}
