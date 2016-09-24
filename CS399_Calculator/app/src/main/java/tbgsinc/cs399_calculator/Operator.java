package tbgsinc.cs399_calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Operator {
	EXPONENTIATION ("^", 2),
	MULTIPLICATION ("*", 3),
	DIVISION ("/", 3),
	ADDITION ("+", 4),
	SUBTRACTION ("-", 4),
	L_PARENTHESIS ("(", 5),
	R_PARENTHESIS (")", 5),
	SIN ("sin", 1),
	COS ("cos", 1),
	TAN ("tan", 1),
	LOG ("log", 1),
	SQRT ("√", 2);
	
	private static final Map<String, Operator> operators;
	static{
		Map<String, Operator> ops = new HashMap<String, Operator>();
		ops.put(Operator.EXPONENTIATION.symbol(), Operator.EXPONENTIATION);
		ops.put(Operator.MULTIPLICATION.symbol(), Operator.MULTIPLICATION);
		ops.put(Operator.DIVISION.symbol(), Operator.DIVISION);
		ops.put(Operator.ADDITION.symbol(), Operator.ADDITION);
		ops.put(Operator.SUBTRACTION.symbol(), Operator.SUBTRACTION);
		ops.put(Operator.L_PARENTHESIS.symbol(), Operator.L_PARENTHESIS);
		ops.put(Operator.R_PARENTHESIS.symbol(), Operator.R_PARENTHESIS);
		ops.put(Operator.SIN.symbol(), Operator.SIN);
		ops.put(Operator.COS.symbol(), Operator.COS);
		ops.put(Operator.TAN.symbol(), Operator.TAN);
		ops.put(Operator.LOG.symbol(), Operator.LOG);
		ops.put(Operator.SQRT.symbol(), Operator.SQRT);
		operators = Collections.unmodifiableMap(ops);
	}
	
	private final String symbol;
	private final int precedence;
	Operator(String symbol, int precedence){
		this.symbol = symbol;
		this.precedence = precedence;
	}
	public String symbol(){return symbol;}
	public int precedence(){return precedence;}
	
	public static int findPrecedence(char c){
		return operators.get(String.valueOf(c)).precedence();
	}
	public static int findPrecedence(String c){
		return operators.get(c).precedence();
	}
	public static boolean isOperator(char c){
		return operators.containsKey(String.valueOf(c));
	}
	public static boolean isOperator(String c){
		return operators.containsKey(c);
	}
}
