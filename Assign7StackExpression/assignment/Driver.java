package assignment;

public class Driver implements ExpressionEvaluatorInterface{
	public static int precedence(String operator) {
		int order = -1;
		if(operator.equals("*") || operator.equals("/")) {
			order = 2;
		}
		else if(operator.equals("-") || operator.equals("+")) {
			order = 1;
		}
		return order;
	}
	
	public String infixToPostfix(String infix) {
		String postFix = "";
		Stack<String> operands = new Stack<>();//no longer generic bc it's bound to String although Node CAN use generic's when being defined
		for(int i = 0; i < infix.length(); i++) {
			String index = infix.charAt(i) + "";
			if(Character.isWhitespace(index.charAt(0))){
				i++;
				index = infix.charAt(i) + "";
			}
			if(Character.isDigit(index.charAt(0))) {
				postFix += index + " ";
			}
			else if(index.equals("(")) {
				operands.push(index);
			}
			else if(index.equals(")")){//if we hit ")" once there is already operators in the stack, then we evaluate the operators
				while(operands.size() >= 1) {
					if(!operands.peek().equals("(")) {
						postFix += operands.peek() + " "; 
						//System.out.printf("peek: %s iteration: %d\n", operands.peek(), i);
						operands.pop();
					}
					else if(operands.peek().equals("(")){//once we hit the "("
						operands.pop();
					}
				}
			}
			else {
				while(!operands.isEmpty() && precedence(index) <= precedence(operands.peek())) {
					postFix+= operands.peek() + " ";
					operands.pop();
				}
				operands.push(index);
				}
			
			}
		return postFix;
	}
	
	public static int evaluate(String operator, int num1, int num2) {
		int result;
		if(operator.equals("*")) {
			result = num1*num2;
		}else if(operator.equals("/")) {
			result = num1/num2;
		}else if(operator.equals("+")) {
			result = num1+num2;
		}else if (operator.equals("-")){
			result = num1-num2;
		}
		else {
			result = -1000;
		}
		//System.out.println("result " + result);
		return result;
	}
	
	public int evaluatePostfix(String postfix) {
		int num1;
		int num2;
		int result = 0;
		Stack<Integer> numbers = new Stack<>(); 
		for(int i = 0; i < postfix.length(); i++) {
			if(Character.isWhitespace(postfix.charAt(i))) {
			}
			else if(Character.isDigit(postfix.charAt(i))) {
				numbers.push(Integer.parseInt(postfix.charAt(i) + ""));
			}
			else {
				if(numbers.size() > 1) {
					num2 = numbers.pop();
					num1 = numbers.pop();
					//System.out.printf("Evaluating -- %s %d %d\n", postfix.charAt(i) + "", num1, num2);
					result = evaluate(postfix.charAt(i) + "", num1, num2);
					if(i == postfix.length()-1) {
						break;
					}
					else {
						numbers.push(result);
					}
				}
				else if (numbers.size() == 1){
					num2 = numbers.pop();
					num1 = result;
					result = evaluate(postfix.charAt(i) + "", num1, num2);
					if(i == postfix.length()-1) {
						break;
					}
					else {
						numbers.push(result);
					}
				}
			}
		}
		
		return result;
	}
	
	public static void showEvaluation(String expression) {
		Driver d = new Driver();
		String postfix = d.infixToPostfix(expression);
		int result = d.evaluatePostfix(postfix);
		System.out.printf("--- Results ---\nInfixExpression:    %s\nPostfix Expression: %s\nEvaluated Result:   %d\n\n", expression, postfix, result);
	}
	
    public static void main(String[] args) {
        showEvaluation("(3 + 5) * 2)");
        showEvaluation("(5 + 3)/(2 + 2)");
        showEvaluation("(2 + 3) * 2 - (2 * 2)");
    }
}

