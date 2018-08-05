import java.util.LinkedList;
import java.util.*;
import java.util.Scanner;
public class Begin extends lexer{

	public Begin(TokenType tt, String lexeme) {
		super(tt, lexeme);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to my calculator"
				+ "\nYou may enter any mathematical operation consisting of +, -, *, or /"
				+ "\nTo exit the calulator just type $");
		String s = "";
		while( true )
		{
			s = scan.next();
			if( s.equals("!") )
			{
				System.out.println("GoodBye :)" );
				System.exit(0);
				break;
			}
			
			Deque<String> q = new LinkedList<>();;
			for( int i = 0; i < s.length(); i++)
			{
				char ch = s.charAt(i);
				if(ch == '\n')
					continue;
				String string = Character.toString(ch);
				q.add(string);
			}

			ParserToken p = new ParserToken();
			ParseTree tree = p.Prog( q );
			if( tree != null )
			{
				NodeTraverse(tree);
			}
			else{
				System.out.println("ERROR");
			}
		}
		scan.close();
	}
	static void NodeTraverse(ParseTree t) 
	{
	        if( t.getLeft() != null ) 
	           	NodeTraverse(t.getLeft());
	       	if( t.getRight() != null )
	           	NodeTraverse(t.getRight());
	    	t.eval();
	}

}
