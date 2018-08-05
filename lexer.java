enum TokenType {
		// A number
	T_ICONST,
	T_DCONST,
		// the operators
	T_PLUS,
	T_MINUS,
	T_STAR,
	T_SLASH,

		// any error returns this token
	T_ERROR,

		// when completed (EOF), return this token
	T_DONE
};

public class lexer {
	
	private TokenType	tt;
	private String		lexeme;
	private int			lnum;

	public final static int LINENUMBER = 0;
	
	public lexer(TokenType tt, String lexeme) {
		this.tt = tt;
		this.lexeme = lexeme;
		lnum = LINENUMBER;
	}

	public lexer(TokenType tt) {
		this.tt = tt;
		// TODO Auto-generated constructor stub
	}

	TokenType	GetTokenType() { return tt; }
	String		GetLexeme() { return lexeme; }
	int			GetLinenum() { return lnum; }
};