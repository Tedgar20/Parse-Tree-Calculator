import java.util.*;

public class ParserToken {
	private	lexer	tok;
	private boolean	pushedBack;

public	ParserToken() {
	pushedBack = false;
}

	public lexer getToken(Deque<String> in) {
		if( pushedBack ) {
			pushedBack = false;
			return tok;
		}

		return getToken(in);
	}
	void pushbackToken(lexer t) {
		if( pushedBack ) {
			//throw "you cannot push back two tokens!";
		}
		tok = t;
		pushedBack = true;
	}
 ParserToken p;

public ParseTree Prog(Deque<String> in)
{
	return StmtList(in);
}

public ParseTree StmtList(Deque<String> in)
{
	ParseTree stmt = Stmt(in);
	if( stmt == null )
		return null;

	return new StatementList( stmt, StmtList(in) );
}

public ParseTree Stmt(Deque<String> in) {
	ParseTree pr = Print(in);

	if( pr == null )
		return null;

	return pr;
}

public ParseTree Print(Deque<String> in) {
	ParseTree ex = Expr(in);
	if( ex == null ) {
		return null;
	}

	return new PrintStatement(ex);
}

public ParseTree Expr(Deque<String> in) {
	ParseTree t1 = Term(in);
	if( t1 == null )
		return null;

	for(;;) {
		lexer op = lexerEval.getToken(in);
		if( op.GetTokenType() != TokenType.T_PLUS && op.GetTokenType() != TokenType.T_MINUS ) {
			in.push(op.GetLexeme());
			return t1;
		}

		ParseTree t2 = Term(in);
		if( t2 == null ) {
			return null;
		}

		// combine t1 and t2 together
		if( op.GetTokenType() == TokenType.T_PLUS )
			t1 = new Addition(t1, t2);
		else
			t1 = new Subtraction(t1, t2);
	}
}

public ParseTree Term(Deque<String> in) {
	ParseTree t1 = Primary(in);
	if( t1 == null )
		return null;

	for(;;) {
		lexer op = lexerEval.getToken(in);
		if( op.GetTokenType() != TokenType.T_STAR && op.GetTokenType() != TokenType.T_SLASH ) {
			in.push(op.GetLexeme());
			return t1;
		}

		ParseTree t2 = Primary(in);
		if( t2 == null ) {
			return null;
		}

		// combine t1 and t2 together
		if( op.GetTokenType() == TokenType.T_STAR )
			t1 = new Multiplication(t1, t2);
		else
			t1 = new Division(t1, t2);
	}
}

public ParseTree Primary(Deque<String> in) {
	lexer tok = lexerEval.getToken(in);
	try{
		if( tok.GetTokenType() == TokenType.T_ICONST )
			return new IntegerConstant(tok);
		
		else if( tok.GetTokenType() == TokenType.T_DCONST )
			return new DoubleConstant(tok);
	}catch( NumberFormatException e){
		return null;
	}
	return null;
	}
}