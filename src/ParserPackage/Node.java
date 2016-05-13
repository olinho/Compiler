package ParserPackage;

public class Node {
	private String expression;
	private boolean hasChildren;
	private Node child1;
	private Node child2;
	private int value;	// if unset it's 0
	
	public Node()
	{
		SetExpression("");
		hasChildren = false;
		SetChild1(null);
		SetChild2(null);
	}
	
	public Node(int val) 
	{
		this();
		value = val;
	}

	
	public Node(String expr, Node n1, Node n2)
	{
		SetExpression(expr);
		hasChildren = true;
		SetChild1(n1);
		SetChild2(n2);
	}
	
	public Node(String expr, Node n1)
	{
		this(expr, n1, null);
	}
	
	/**
	 * check if we have any blank leafs
	 * @return
	 */
	public boolean DoesNodeNeedsValue()
	{
		if (!hasChildren)
		{
			if (value == 0)
			{
				return true;
			}
		}
		else	// has Children
		{
			if (child2 == null)
			{
				return true;
			}
		}
		return false; 	// in other way
	}
	
	public String NodeToString()
	{
		if (value != 0)
		{
			return "Node("+value+")";
		}
		else 
		{
			// child1 != null
			if (child2 != null)
			{
				return "Node(" + expression.toString() + ", " + child1.NodeToString() + ", " + child2.NodeToString() + ")";
			}
			else
			{
				return "Node(" + expression.toString() + ", " + child1.NodeToString() + ", null)";
			}
		}
	}
	
	
	public void SetFirstChild(Node n1)
	{
		SetChild1(n1);
		value = 0;
		hasChildren = true;
	}

	public void SetSecondChild(Node n2)
	{
		SetChild2(n2);
		value = 0;
		hasChildren = true;
	}
	
	public boolean HasChildren()
	{
		return hasChildren;
	}
	
	public void SetValue(int v)
	{
		value = v;
	}
	
	public int GetValue()
	{
		return value;
	}

	public Node GetChild1() {
		return child1;
	}

	public void SetChild1(Node child1) {
		this.child1 = child1;
	}

	public Node GetChild2() {
		return child2;
	}

	public void SetChild2(Node child2) {
		this.child2 = child2;
	}

	public String GetExpression() {
		return expression;
	}

	public void SetExpression(String expression) {
		this.expression = expression;
	}
}
