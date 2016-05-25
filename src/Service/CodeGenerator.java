package Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Model.Node;
import Model.Tokenizer;

/**
 * This class produce tree including expression and values
 * @author Olek
 *
 */
public class CodeGenerator {
	
	private Tokenizer tokenizer;
	private Node tree;
	private BaseEnvironment baseEnv = new BaseEnvironment();
	
	
	public CodeGenerator() 
	{
		tokenizer = new Tokenizer();
		tree = new Node();
	}
	
	
	/**
	 * method working on private variable tree, adding new value
	 * @param expr
	 */
	public void AddValueToTree(int value)
	{
		tree = AddValue(tree, value);
	}
	
	/**
	 * method working on private variable tree, adding new expression
	 * @param expr
	 */
	public void AddExpressionToTree(String expr)
	{
		tree = AddExpression(tree, expr);
	}
	
	
	/**
	 * method adding new expression to the tree and returning udpate node
	 * @param node - current node
	 * @param expr - inserting expression
	 * @return created node
	 */
	public Node AddExpression(Node node, String expr)
	{
		Node child1 = node.GetChild1();
		Node child2 = node.GetChild2();
		int value = node.GetValue();
		
		if (child1 == null)
		{
			node.SetChild1(new Node(value));
			node.SetExpression(expr);
		}
		else
		{
			if (expr.equals("add"))
			{
				Node newNode = new Node();
				newNode.SetChild1(node);
				newNode.SetExpression(expr);
				return newNode;
			}
			else if (expr.equals("sub"))
			{
				Node newNode = new Node();
				newNode.SetChild1(node);
				newNode.SetExpression(expr);
				return newNode;
			}
			else if (expr.equals("mul"))
			{
				if (child2.HasChildren())
				{
					node.SetChild2(AddExpression(node.GetChild2(), expr));
				}
				else // child2 doesn't have any children 
				{
					node.SetChild2(new Node(expr, child2.GetValue()));
				}
			}
			else if (expr.equals("div"))
			{
				if (child2.HasChildren())
				{
					node.SetChild2(AddExpression(node.GetChild2(), expr));
				}
				else // child2 doesn't have any children 
				{
					node.SetChild2(new Node(expr, child2.GetValue()));
				}
			}
		}
		return node;
		
	}
	
	
	/**
	 * method adds new value to Node and returns updated node
	 * @param node - current node
	 * @param val - inserting value
	 * @return
	 */
	public Node AddValue(Node node, int val) 
	{
		Node child1 = node.GetChild1();
		Node child2 = node.GetChild2();
		String expression = node.GetExpression();
		
		if (child1 == null)
		{
			if (expression.equals("")) 
			{
				node.SetValue(val);; 	// return Node(val)
			}
			else
			{
				node.SetChild1(new Node(val));
			}
		}
		else	// child1 != null    for example Node(add, Node(2), null)
		{
			if (child1.DoesNodeNeedsValue())
			{
				node.SetChild1(AddValue(child1, val));
			}
			else
			{
				if (child2 == null)
				{
					node.SetChild2(new Node(val));
				}
				else
				{
					node.SetChild2(AddValue(child2, val));
				}
			}
		}
		return node;
	}
	
	
	public void CreateSampleTree()
	{
		AddValueToTree(2);
		System.out.println(tree.NodeToString());
		
		AddExpressionToTree("add");
		System.out.println(tree.NodeToString());
		
		AddValueToTree(5);
		System.out.println(tree.NodeToString());
		
		AddExpressionToTree("add");
		System.out.println(tree.NodeToString());
		
		AddValueToTree(20);
		System.out.println(tree.NodeToString());
		
		AddExpressionToTree("mul");
		System.out.println(tree.NodeToString());

		AddValueToTree(1);
		System.out.println(tree.NodeToString());
	}
	
	public String RecursiveRead()
	{
		return RecursiveRead(tree);
	}
	
	public String RecursiveRead(Node node)
	{
		if (node.HasChildren())
		{
			return  RecursiveRead(node.GetChild1()) + " | " + RecursiveRead(node.GetChild2()) + " | " + node.GetExpression();
		}
		else
			return "put " + String.valueOf(node.GetValue());
	}
	
	public int CalculateTree()
	{
		return CalculateTree(tree);
	}
	
	public int CalculateTree(Node node)
	{
		if (node.HasChildren())
		{
			if (node.GetExpression().equals("add"))
				return CalculateTree(node.GetChild1()) + CalculateTree(node.GetChild2());
			else if(node.GetExpression().equals("sub"))
				return CalculateTree(node.GetChild1()) - CalculateTree(node.GetChild2());
			else if (node.GetExpression().equals("mul"))
				return CalculateTree(node.GetChild1()) * CalculateTree(node.GetChild2());
			else if (node.GetExpression().equals("div"))
				return CalculateTree(node.GetChild1()) / CalculateTree(node.GetChild2());
			else
				return 0;
		}
		else
			return node.GetValue();
	}
	public void ExecuteTree() throws Exception
	{
		String strExpr = RecursiveRead(tree) + " | end";
		System.out.println(strExpr);
		baseEnv.ExpressionService(strExpr);
	}
	
	public void ShowTree()
	{
		System.out.println(tree.NodeToString());
	}
	
	public Node GetTree()
	{
		return tree;
	}
	
	public String ReadExpression() throws IOException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		return scanner.readLine();
	}
	

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
		
		System.out.println("INFO from ExecuteTree method:");
		try {
			gen2.ExecuteTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
