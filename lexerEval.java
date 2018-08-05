import java.util.*;

public class lexerEval extends lexer
{	
	public lexerEval(TokenType tt, String lexeme) {
		super(tt, lexeme);
		// TODO Auto-generated constructor stub
	}

	enum LexState { BEGIN, ININT, INDOUB };
	static LexState lexstate = LexState.BEGIN;
	
	public static lexer getToken(Deque<String> br)
	{
		String lexeme = "";
		for(;;) 
		{
			if( br.peek() == null )
				break;
			
			switch( lexstate ) 
			{
				case BEGIN:
					String ch = br.remove();
					lexeme = ch;
					if( isDigit(ch) ) {
						if( br.peek() == null)
							return new lexer(TokenType.T_ICONST, lexeme);
						else if( br.peek().equals(".")){
							lexeme += br.remove();
							lexstate = LexState.INDOUB;
						}
						else
							lexstate = LexState.ININT;
					}
					else if( ch.equals(".") ){
						lexeme = "0" + lexeme;
						lexstate = LexState.INDOUB;
					}
					else{
						TokenType tt = TokenType.T_ERROR;
						switch( ch ) {
						case "+":
							tt = TokenType.T_PLUS;
							break;
						case "-":
							tt = TokenType.T_MINUS;
							break;
						case "*":
							tt = TokenType.T_STAR;
							break;
						case "/":
							tt = TokenType.T_SLASH;
							break;
						}
						return new lexer(tt, lexeme);
					}
					break;
					
				case ININT:
					if( isDigit(br.peek()) ) {
						lexeme += br.remove();
						if( br.peek() == null)
							return new lexer(TokenType.T_ICONST, lexeme);
					}
					else{
						lexstate = LexState.BEGIN;
						return new lexer(TokenType.T_ICONST, lexeme);
					}
					break;
				case INDOUB:
					if( isDigit(br.peek()) ){
						lexeme += br.remove();
						if( br.peek() == null)
							return new lexer(TokenType.T_DCONST, lexeme);
					}
					else{
						lexstate = LexState.BEGIN;
						return new lexer(TokenType.T_DCONST, lexeme);
					}
			}
		}
			return new lexer(TokenType.T_DONE);
}
	private static boolean isDigit(String lexeme) 
	{
		try {
	        Integer.parseInt(lexeme);
	    } catch (Exception e) {
	        return false;
	    }
	    return true;
	}
}