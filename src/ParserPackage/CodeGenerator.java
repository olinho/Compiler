package ParserPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import ParserPackage.Tokenizer.Token;

public class CodeGenerator {
	
	private Tokenizer tokenizer;
	private Node tree;
	
	
	public CodeGenerator() 
	{
		tokenizer = new Tokenizer();
		tree = new Node();
	}
	
	
	/**
	 * method adds new value to Node and returns updated node
	 * @param node - current node
	 * @param val - inserting value
	 * @return
	 */
	public Node AddValue(Node node, int val) {
		Node child1 = node.GetChild1();
		Node child2 = node.GetChild2();
		String expression = node.GetExpression();
		int value = node.GetValue();
		
		if (child1 == null)
		{
			if (expression.equals("")) 
			{
				value = val; 	// return Node(val)
			}
			else
			{
//				child1 = new Node(val);		// return Node(expr, Node(val), null)
				node.SetChild1(new Node(val));
			}
		}
		else	// child1 != null    for example Node(+, Node(2), null)
		{
			if (child1.DoesNodeNeedsValue())
			{
				child1 = AddValue(child1, val);
			}
			else
			{
				if (child2 == null)
				{
//					child2 = new Node(val);
					node.SetChild2(new Node(val));
				}
				else
				{
					if (child2.DoesNodeNeedsValue())
					{
						child2 = AddValue(child2, value);
					}
				}
			}
		}
		return node;
	}
	
	/**
	 * check if node needs value, for example: Node(+, Node(2), null)
	 * @param node
	 * @return
	 */
//	public boolean IfNodeNeedsValue(Node node)
//	{
//		Node child1 = node.GetChild1();
//		Node child2 = node.GetChild2();
//		String expression = node.GetExpression();
//		int value = node.GetValue();
//		
//		if (!expression.equals("")) 	// if expression is not set
//		{
//			return false;
//		}
//		// expression is set
//		
//		if (child1 == null)
//		{
//			return true;
//		}
//		else if (child1 != null)
//		{
//			if (child2 == null)
//			{
//				return true;
//			}
//		}
//	}
	
	public String ReadExpression() throws IOException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		return scanner.readLine();
	}
	
	public static void main(String[] args) {
		CodeGenerator cg = new CodeGenerator();
		Node tree = new Node();
		tree.SetExpression("add");
		tree.SetChild1(new Node("+", new Node(2)));
		System.out.println(tree.NodeToString());
		Node tree2 = cg.AddValue(tree, 5);
		System.out.println(tree2.NodeToString());
	}
}
