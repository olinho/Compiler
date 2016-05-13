package ParserPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import ParserPackage.Tokenizer.Token;

/**
 * This class produce tree including expression and values
 * @author Olek
 *
 */
public class CodeGenerator {
	
	private Tokenizer tokenizer;
	private Node tree;
	
	
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
	
	public void ShowTree()
	{
		System.out.println(tree.NodeToString());
	}
	
	public String ReadExpression() throws IOException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		return scanner.readLine();
	}
	
	public static void main(String[] args) {
		CodeGenerator cg = new CodeGenerator();
		cg.CreateSampleTree();
		cg.ShowTree();
	}
}
