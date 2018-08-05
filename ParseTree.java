enum TypeForNode { INT_TYPE, DOUBLE_TYPE, ERROR_TYPE };

class Value {
	TypeForNode type;
   	int i;
   	double d;
   	String s;
public Value(){
   		 type = TypeForNode.ERROR_TYPE;
   }
   public Value( int iv ){
   		 type = TypeForNode.INT_TYPE;
   		 i = iv;
   }
   public Value( double dv ){
 		 type = TypeForNode.DOUBLE_TYPE;
 		 d = dv;
   }
   public int add( Value v )
   {
	   int result = this.GetIntValue() + v.GetIntValue();
	   return result;
   }
   public double addD( Value v )
   {
	   if( this.GetDoubleValue() == 0.0 ){
		   double result = this.GetIntValue() + v.GetDoubleValue();
		   return result;
	   }
	   else if( v.GetDoubleValue() == 0.0 ){
		   double result = this.GetDoubleValue() + v.GetIntValue();
		   return result;
	   }
	   double result = this.GetDoubleValue() + v.GetDoubleValue();
	   return result;
   }
   public int sub( Value v)
   {
	   int result = this.GetIntValue() - v.GetIntValue();
	   return result;
   }
   public double subD( Value v )
   {
	   if( this.GetDoubleValue() == 0.0 ){
		   double result = this.GetIntValue() - v.GetDoubleValue();
		   return result;
	   }
	   else if( v.GetDoubleValue() == 0.0 ){
		   double result = this.GetDoubleValue() - v.GetIntValue();
		   return result;
	   }
	   double result = this.GetDoubleValue() - v.GetDoubleValue();
	   return result;
   }
   public int mul( Value v)
   {
	   int result = this.GetIntValue() * v.GetIntValue();
	   return result;
   }
   public double mulD( Value v )
   {
	   if( this.GetDoubleValue() == 0.0 ){
		   double result = this.GetIntValue() * v.GetDoubleValue();
		   return result;
	   }
	   else if( v.GetDoubleValue() == 0.0 ){
		   double result = this.GetDoubleValue() * v.GetIntValue();
		   return result;
	   }
	   double result = this.GetDoubleValue() * v.GetDoubleValue();
	   return result;
   }
   public double div( Value v )
   {
	   if( this.GetDoubleValue() == 0.0 && v.GetDoubleValue() == 0.0 ){
		   double left = this.GetIntValue();
		   double right = v.GetIntValue();
		   return left / right;
	   }
	   else if( this.GetDoubleValue() == 0.0 ){
		   double left = this.GetIntValue();
		   double result = left / v.GetDoubleValue();
		   return result;
	   }
	   else if( v.GetDoubleValue() == 0.0 ){
		   double right = v.GetIntValue();
		   double result = this.GetDoubleValue() / right;
		   return result;
	   }
	   
	   return this.GetDoubleValue() / v.GetDoubleValue();
   }
   TypeForNode GetType() { return type; }
   int GetIntValue() { return i; }
   double GetDoubleValue() { return d; }
   
}
public class ParseTree {
	private lexer		tok;
	private ParseTree	left;
	private ParseTree	right;

public ParseTree(ParseTree l, ParseTree r) {
	this.left = l;
	this.right = r;
}
public ParseTree(lexer t) {
	this.tok = t;
}
	public ParseTree getLeft() { return left; }
	public ParseTree getRight() { return right; }

   	public void NodeTraverse(ParseTree t) {}
	public TypeForNode GetType() { return TypeForNode.ERROR_TYPE; }
	public int GetIntValue() { /*throw "no integer value"*/return 0; }
	public double GetDoubleValue() { return 0.0; }
	public Value eval() { return null; }
	public boolean typeCheck() { return /*typeSafe*/ true; }
	public boolean isInt() { return false; }
	public boolean isDouble() { return false; }
	public int findSemanticErrors() { return 0; }
}
   
class StatementList extends ParseTree {
public	StatementList(ParseTree first, ParseTree rest) {
	super(first, rest);
	}
}

class Addition extends ParseTree {
   ParseTree num1;
   ParseTree num2;
   boolean i, d = false;
public Addition(ParseTree op1, ParseTree op2) {
	super(op1, op2);
   		num1 = op1;
   		num2 = op2;
   	}
   	public Value eval()
   	{ 
   		Value N1 = getLeft().eval();
   		Value N2 = getRight().eval();
   		if( getLeft().isInt() && getRight().isInt() ){
   			Value result = new Value(N1.add(N2));
   			setI(true);
   			return result;
   		}
   		else if( getLeft().isDouble() && getRight().isDouble() ){
   			Value result = new Value(N1.addD(N2));
   			setD(true);
   			return result;
   		}
   		else if( getLeft().isDouble() || getRight().isDouble() ){
   			Value result = new Value(N1.addD(N2));
   			setD(true);
   			return result;
   		}
		return N2;
   	}
   	public boolean isInt(){
   		if( getI() ) return true;
   		return false;
   	}
   	public boolean isDouble(){
   		if( getD() ) return true;
   		return false;
   	}
   	public boolean getI() { return i; }
   	public void setI( boolean v ) { i = v; }
   	public boolean getD() { return d; }
   	public void setD( boolean v ) { d = v; }
   	
   	public TypeForNode GetType() { return TypeForNode.INT_TYPE; }
   	public int findSemanticErrors() { return 0; }
   	public ParseTree getLeft() { return num1; }
   	public ParseTree getRight() { return num2; }
};

class Subtraction extends ParseTree {
   ParseTree num1;
   ParseTree num2;
   boolean i, d = false;
public Subtraction(ParseTree op1, ParseTree op2) {
	super(op1, op2);
   		num1 = op1;
   		num2 = op2;
   	}
   	public Value eval() 
   	{ 
   		Value N1 = getLeft().eval();
   		Value N2 = getRight().eval();
   		if( getLeft().isInt() && getRight().isInt() ){
   			Value result = new Value(N1.sub(N2));
   			setI(true);
   			return result;
   		}
   		else if( getLeft().isDouble() && getRight().isDouble() ){
   			Value result = new Value(N1.subD(N2));
   			setD(true);
   			return result;
   		}
   		else if( getLeft().isDouble() || getRight().isDouble() ){
   			Value result = new Value(N1.subD(N2));
   			setD(true);
   			return result;
   		}
   		return N2;
   	}
   	public boolean isInt(){
   		if( getI() ) return true;
   		return false;
   	}
   	public boolean isDouble(){
   		if( getD() ) return true;
   		return false;
   	}
   	public boolean getI() { return i; }
   	public void setI( boolean v ) { i = v; }
   	public boolean getD() { return d; }
   	public void setD( boolean v ) { d = v; }
   	
   	public TypeForNode GetType() { return TypeForNode.INT_TYPE; }
   	public int findSemanticErrors() { return 0; }
   	public ParseTree getLeft() { return num1; }
   	public ParseTree getRight() { return num2; }
};

class Multiplication extends ParseTree {
   ParseTree num1;
   ParseTree num2;
   boolean i, d = false;
public	Multiplication(ParseTree op1, ParseTree op2) {
		super(op1, op2);
   		num1 = op1;
   		num2 = op2;
   	}
	public Value eval()
   	{ 
		Value N1 = getLeft().eval();
   		Value N2 = getRight().eval();
   		if( getLeft().isInt() && getRight().isInt() ){
   			Value result = new Value(N1.mul(N2));
   			setI(true);
   			return result;
   		}
   		else if( getLeft().isDouble() && getRight().isDouble() ){
   			Value result = new Value(N1.mulD(N2));
   			setD(true);
   			return result;
   		}
   		else if( getLeft().isDouble() || getRight().isDouble() ){
   			Value result = new Value(N1.mulD(N2));
   			setD(true);
   			return result;
   		}
   		return N2;
   	}
	public boolean isInt(){
   		if( getI() ) return true;
   		return false;
   	}
   	public boolean isDouble(){
   		if( getD() ) return true;
   		return false;
   	}
	public boolean getI() { return i; }
   	public void setI( boolean v ) { i = v; }
   	public boolean getD() { return d; }
   	public void setD( boolean v ) { d = v; }
	
   	public TypeForNode GetType() { return TypeForNode.INT_TYPE; }
   	public int findSemanticErrors() { return 0; }
	public ParseTree getLeft() { return num1; }
	public ParseTree getRight() { return num2; }
};
   
class Division extends ParseTree {
   ParseTree num1;
   ParseTree num2;
   boolean i, d = false;
public	Division(ParseTree op1, ParseTree op2) {
		super(op1, op2);
   		num1 = op1;
   		num2 = op2;
   	}
	public Value eval()
   	{ 
		Value N1 = getLeft().eval();
   		Value N2 = getRight().eval();
   		if( getLeft().isInt() && getRight().isInt() ){
   			Value result = new Value(N1.div(N2));
   			setD(true);
   			return result;
   		}
   		else if( getLeft().isDouble() && getRight().isDouble() ){
   			Value result = new Value(N1.div(N2));
   			setD(true);
   			return result;
   		}
   		else if( getLeft().isDouble() || getRight().isDouble() ){
   			Value result = new Value(N1.div(N2));
   			setD(true);
   			return result;
   		}
   		return N2;
   	}
	public boolean isInt(){
   		if( getI() ) return true;
   		return false;
   	}
   	public boolean isDouble(){
   		if( getD() ) return true;
   		return false;
   	}
	public boolean getI() { return i; }
   	public void setI( boolean v ) { i = v; }
   	public boolean getD() { return d; }
   	public void setD( boolean v ) { d = v; }
	
   	public TypeForNode GetType() { return TypeForNode.INT_TYPE; }
   	public int findSemanticErrors() { return 0; }
   	public ParseTree getLeft() { return num1; }
   	public ParseTree getRight() { return num2; }
};

class PrintStatement extends ParseTree {
	ParseTree num1;
public PrintStatement(ParseTree ex) {
		super(ex, null);
   		num1 = ex;
   	}
   	public Value eval()
   	{
   		ParseTree ex = getLeft();
   		Value prt = ex.eval();
   		if( ex.isInt() )
   			System.out.println(prt.GetIntValue());
   		else if( ex.isDouble() )
   			System.out.println(prt.GetDoubleValue());
   		
   		return prt;
   	}
   public ParseTree getLeft() { return num1; }
};
 
class IntegerConstant extends ParseTree {
	int	value;
public IntegerConstant(lexer tok) {
		super(tok);
		value = Integer.parseInt( tok.GetLexeme() );
	}
	public Value eval() 
	{
   		Value num = new Value(value);
   		return num; 
   	}
	public boolean isInt() { return true; }
	public TypeForNode GetType() { return TypeForNode.INT_TYPE; }
	public int GetIntValue() { return value; }
};

class DoubleConstant extends ParseTree {
	double	value;
public DoubleConstant(lexer tok) {
		super(tok);
		value = Double.parseDouble( tok.GetLexeme() );
	}
	public Value eval() 
	{
   		Value num = new Value(value);
   		return num; 
   	}
	public boolean isDouble() { return true; }
	public TypeForNode GetType() { return TypeForNode.DOUBLE_TYPE; }
	public double GetDoubleValue() { return value; }
};