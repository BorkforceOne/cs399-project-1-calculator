package tbgsinc.cs399_calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser {
	private Stack<String> operators = new Stack<String>();
	private String outStr;
	
	public Parser(){}
	
	private void foundOperator(String op){
		if(operators.empty()){
			operators.push(op);
			return;
		}
		String sTop = operators.peek();
		if(sTop.equals("(") && !op.equals(")")) 
			operators.push(op);
		else if(op.equals("("))
			operators.push(op);
		else if(op.equals(")")){
			String tempS = operators.pop();
			while (!tempS.equals("(")){
				outStr+=" "+tempS;
				tempS = operators.pop();
			}
		}
		else{
			while(true){
				if(operators.empty()){
					operators.push(op);
					break;
				}
				
				sTop = operators.peek();
				if(Operator.findPrecedence(op)<Operator.findPrecedence(sTop)){
					operators.push(op);
					break;
				}
				else if(Operator.findPrecedence(op)==Operator.findPrecedence(sTop)){
					//Determine Association
					if(op=="^"){//Right Associative
						operators.push(op);
					}
					else{//Left Associative
						outStr+=" "+operators.pop();
						operators.push(op);
					}
					break;
				}
				else if(Operator.findPrecedence(op)>Operator.findPrecedence(sTop)){
					outStr+=" "+operators.pop();
				}
			}
		}
	}
	public String translate(String eq){
		if(eq.charAt(eq.length()-1)!=')' && Operator.isOperator(eq.charAt(eq.length()-1)))
			return eq; //Already Postfix
		
		operators.clear();
		outStr = "";
		
		String op = "";
		for(int i=0;i<eq.length();i++){
			char c = eq.charAt(i);
			if(!(""+op).equals(Operator.SUBTRACTION.symbol()) && Operator.isOperator(op)){
				foundOperator(op);
				op="";
				i--; //Decrement i, since c will not be apart op, thus c's iteration is interrupted by op
			}
			else if(Operator.isOperator(c)){
				if(op.length()>0){
					outStr+=" "+op;
					op="";

					foundOperator(""+c);
				}
				else if(Operator.SUBTRACTION.symbol().equals(""+c) && op.length()==0)
					op+=c;
				else
					foundOperator(""+c);
			}
			else op+=c;
		}
		if(op.length()>0) outStr+=" "+op;
		
		while(!operators.empty()){
			outStr+=" "+operators.pop();
		}
		
		return outStr.trim();
	}
	public ArrayList<String> parseFile(File inputFile) throws IOException{
		ArrayList<String> lineList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = null;
		while((line = br.readLine()) != null) {
			lineList.add(line);
		}
		br.close();
		return lineList;
	}
}
