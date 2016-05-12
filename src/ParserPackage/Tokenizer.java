package ParserPackage;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.ParserException;

/**
 * Parser
 * @author Olek
 *
 */
public class Tokenizer {

	private LinkedList<TokenInfo> tokenInfos;
	private LinkedList<Token> tokens;
	
	public Tokenizer()
	{
		tokenInfos = new LinkedList<TokenInfo>();
		tokens = new LinkedList<Token>();
		createTokens();
	}
	
	/**
	 * in this method we can tokenize the string what means 
	 * to assign a number to regex
	 */
	public void tokenize(String str)
	{
		String s = new String(str);
		tokens.clear();
		
		boolean match = false;
		
		while (!s.equals(""))
		{
			for (TokenInfo info : tokenInfos)
			{
				Matcher m = info.regex.matcher(s);
				if (m.find())
				{
					match = true;
					
					String tok = m.group().trim();
					tokens.add(new Token(info.token, tok));
					
					s = m.replaceFirst("");
					break;
				}
			}
		}
		if (!match) throw new ParserException("Unexpected character in input: "+s);
	}
	
	
	public void createTokens()
	{
		add("sin|cos|sqrt", 1);
		add("\\(", 2);
		add("\\)", 3);
		add("[+-]", 4);
		add("[*/]", 5);
		add("\\^", 6);
		add("[0-9]", 7);
		add("[a-zA-Z][a-zA-Z0-9_]*", 8);	// variable
	}
	
	public class Token 
	{
		public final int token;
		public final String sequence;
		
		public Token(int token, String seq)
		{
			super();
			this.token = token;
			sequence = seq;
		}
	}
	
	
	private class TokenInfo
	{
		public final Pattern regex;
		public final int token;
		
		public TokenInfo(Pattern pat, int token){
			super();
			regex = pat;
			this.token = token;
		}
	}
	
	public void add(String regex, int token) {
		tokenInfos.add(
				new TokenInfo(
						Pattern.compile("^("+regex+")"), token));
	}
	
	
	public LinkedList<Token> getTokens() {
		return tokens;
	}
	
	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer();
		try
		{
			tokenizer.tokenize(" sin(y) + (23 - 15) * 2 / var_n ");
			
			for (Tokenizer.Token tok : tokenizer.getTokens())
			{
				System.out.println("" + tok.token + " " + tok.sequence);
			}
		}
		catch (ParserException p){
			System.out.println(p.getMessage());
		}
	}
}
