package ParserPackage;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.ParserException;

/**
 * Scaner
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
		s = RemoveSpaces(s);
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
		Add("sin|cos|sqrt", 1);
		Add("\\(", 2);
		Add("\\)", 3);
		Add("[+-]", 4);
		Add("[*/]", 5);
		Add("\\^", 6);
		Add("[0-9]+", 7);
		Add("[a-zA-Z][a-zA-Z0-9_]*", 8);	// variable
		Add(" ", 9);
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
	
	/**
	 * add token 
	 */
	public void Add(String regex, int token) {
		tokenInfos.add(
				new TokenInfo(
						Pattern.compile("^("+regex+")"), token));
	}
	
	/**
	 * remove spaces from expression 
	 */
	public String RemoveSpaces(String str)
	{
		return str.replaceAll(" ", "");
	}
	
	public LinkedList<Token> getTokens() {
		return tokens;
	}
	
	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer();
		try
		{
			tokenizer.tokenize(" 23 + 12*5 + 1");
			
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
