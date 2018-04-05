package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * This Class will calculate an arithmetic expression.
 * It respects order of operations according to PEDMAS.
 *  @author Monika
 *
 */
public class Calculator
{

    /**
     * Variable which stores the last equations output.
     *  
     */
    private double result;
    /**
     * This method is gets the previous result.
     * @return It returns the previous expression's result.
     */
    public double getResult()
    {
        return result;
    }


    /**
     * This method saves the value of arithmetic expression.
     * @param result Value of arithmetic expression to be saved.
     */
    public void setResult(double result)
    {
        this.result = result;
    }



    /**
     * This method will call the methods for evaluation of string and 
     * return the result to calc method.
     * @param input Arithmetic expression which we want to calculate.
     * @return It returns the result of the expression.
     */
    public double calculation(String input)
    {
        String s = negative(input);
        if(s.equalsIgnoreCase("WRONG"))
            return Double.MAX_VALUE;
        boolean result = notation(s);
        if(result)
            s =   postfix(s);
        else
            return Double.MAX_VALUE;
        if(s.equalsIgnoreCase("WRONG"))
            return Double.MAX_VALUE;
        Double res = evaluate(s);
        return res;
    }


    /**
     * This method takes care of negative numbers and previous result.
     * This method converts the negative numbers to <em> _num </em> for further evaluation.
     * And it also checks if we have <em> = </em> for evaluation.
     * @param input Arithmetic expression to be evaluated.
     * @return It returns the string with negative numbers handled 
     * and also appends the previous result if expression requires.
     * It returns <em> WRONG </em> if = sign is not used for evaluation.
     */
    public String negative(String input)
    {
        String s = input;
        char prev;
        String temp;
        if(input.length()>0 && input.charAt(input.length()-1) != '=' )
        {
            System.out.println("Please use = to evaluate the expression");
            return "WRONG";
        }
        else
        {
            char c = input.charAt(0);
            double res;
            StringBuffer str = new StringBuffer("");
            if(oper(c))
            {
                if(c == '-')
                {
                    if(input.charAt(1) == '-')
                    {
                        res = this.getResult();
                        if(res % 1 == 0)
                        {
                            str.append((int)res);
                        }
                        else
                        {
                            res = this.getResult();
                            temp = String.format("%.4f", res);
                            str.append(temp);
                        }
                    }
                }
                else
                {
                    res = this.getResult();
                    if(res % 1 == 0)
                    {
                        str.append((int)res);
                    }
                    else
                    {
                        temp = String.format("%.4f", res);
                        str.append(temp);
                    }

                }
                str.append(input);
                s = str.toString();
                str.setLength(0);
                prev = s.charAt(0);

            }
            prev = s.charAt(0);
            for(int i=0;i<s.length()-1;i++)
            {
                c = s.charAt(i);
                if(c == '-')
                {
                    if(i+1<s.length() && s.charAt(i+1) == '(')
                    {
                        if(i-1>=0 && s.charAt(i-1) == '(')
                        {
                            str.append("_1*");
                        }
                        else
                        {
                            prev = c;
                            str.append(c);
                        }
                    }
                    else if((i-1>=0  || i==0) && ( oper(prev) || prev == '('))
                    {
                        str.append("_");
                    }
                    else
                    {
                        str.append(c);
                    }
                    prev = c;
                }
                else if(c == ' ')
                    //nothing
                    ;
                else
                {
                    prev = c;
                    str.append(c);
                }
            }
            return str.toString();
        }
    }


    /**
     * This method checks if the expression is a valid notation or not.
     * It checks if all the braces which were opened are closed or not.
     * And it also checks if all the operators are binary.
     * For e.g. use of <em> 4+++5</em> is not allowed. 
     * @param input It takes the input returned from negative method. 
     * That is all the negatives numbers are converted to _num.
     * @return It returns true if it's a valid notation and false if it's not a valid notation.
     */
    public boolean notation(String input)
    {
        Stack<Character> stack = new Stack<Character>();
        char c;
        boolean prevNum = false;
        for(int i=0; i< input.length(); i++)
        {
            c = input.charAt(i);
            if(!stack.empty())
                ;
            if(c == '(' )
            {
                stack.push(c);
                //System.out.println(input.charAt(i)+ " pushed to the stack");
            }
            else if(c == ')' && !stack.empty() && stack.peek() == '(' )
            {
                stack.pop(); 

            }
            else 
            {
                if(c == '.' || c == ' ' || c == '_')
                    // ignore
                    ;
                else if(oper(c))
                {
                    if(!prevNum)
                        return false;
                    else
                    {
                        prevNum = false;
                    }

                }
                else if(Character.isDigit(c))
                {
                    prevNum = true;

                }
                else
                {
                    return false;
                }

            }
        }
        return stack.empty();
    }



    /**
     * This method evaluates the string in the postfix expression and gives the result.
     * @param input It takes input as post fix expression.
     * @return It returns the value evaluated for the postfix expression.
     * 
     */
    public double evaluate(String input)
    {
        double res = 0;
        boolean var = false;
        Stack<Double> stack = new Stack<>();
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<input.length();i++)
        {
            char ch = input.charAt(i);
            if((Character.isDigit(ch)) || (ch == '.') || (ch == '_'))
            {
                if(ch == '_' )
                {
                    var = true;
                    i++;
                }
                while((i < input.length()) && 
                        ((Character.isDigit(input.charAt(i))) || (input.charAt(i) == '.')))
                {
                    buf.append(input.charAt(i++));
                }
                String a = buf.toString();
                double num = Double.parseDouble(a);
                if(var == true)
                {
                    num = num*-1;
                    var = false;
                }
                stack.push(num);
                buf.setLength(0);


            }
            else
            {
                double val1 = 0,val2 = 0;
                if(!stack.isEmpty())
                {
                    val2 = stack.pop();
                    if(!stack.isEmpty())
                    {
                        val1 = stack.pop();
                    }
                }
                if(ch == '^')
                    stack.push(Math.pow(val1, val2));
                else if(ch == '*')
                    stack.push(val2*val1);
                else if(ch == '/')
                {
                    if(val2 != 0.0)
                        stack.push(val1/val2);
                    else
                    {
                        System.out.println("you are trying to divide by 0");
                        return Double.MAX_VALUE;
                        // throw new IllegalArgumentException();
                    }

                }
                else if(ch == '+')
                    stack.push(val1+val2);
                else if(ch == '-')
                    stack.push(val1-val2);
                else
                {
                    return Double.MAX_VALUE;
                }
            }
        }
        if(!stack.isEmpty())
            res = stack.pop();
       // System.out.println("Going to return "+res);
        return res;
    }


    /**
     * This method converts the expression to postfix expression.
     * @param input This method takes a valid arithmetic expression.
     * @return It returns the postfix expression of the arithmetic expression.
     * and returns <em> WRONG </em> if there is something wrong with arithmetic expression.
     */
    public String postfix(String input)
    {
        Stack<Character> stack = new Stack<>();
        StringBuffer buf = new StringBuffer("");
        StringBuilder str = new StringBuilder("");
        for(int i=0; i<input.length(); i++)
        {
            char c = input.charAt(i);
            if(c == '(')
                stack.push(c);
            else if(c == ')')
            {
                while(!stack.isEmpty() && stack.peek() != '(')
                {
                    str.append(stack.pop());
                }
                if(!stack.isEmpty() && stack.peek() != '(')
                    return "WRONG";
                stack.pop();
            }
            else if(c == '=')
            {
                if(i != input.length()-1)
                {
                    return "WRONG";
                }
            }
            else if (c == ' ')
                //do nothing
                ;
            else if (oper(c))
            {
                while(!stack.isEmpty() && prec(c) <= prec(stack.peek()))
                {
                    if(prec(c) == 0)
                    {
                        return "WRONG";
                    }
                    else
                    {   
                        str.append(stack.pop());
                    }
                }
                stack.push(c);
            }
            else
            {
                if(Character.isDigit(c) || c == '.' || c == '_')
                {
                    while((i < input.length()) && 
                            ((Character.isDigit(input.charAt(i))) || (input.charAt(i) == '.') || (input.charAt(i) == '_')))
                    {
                        buf.append(input.charAt(i++));
                    }
                    String a = buf.toString();
                    str.append(a).append(" ");
                    buf.setLength(0);
                    i--;
                }

            }

        }
        while(!stack.isEmpty())
        {
            str.append(stack.pop());
        }
       // System.out.println("postfix is "+str.toString());
        return str.toString();
    }

    /**
     * It checks if the character is an operator or not
     * @param c The character to be checked if it's an operator or not.
     * @return Returns true if the given character is an operator and 
     * false if it's not an operator.
     */
    public boolean oper(char c)
    {
        if((c == '^') || (c == '*') || (c == '/') || (c == '+') || (c == '-'))
            return true;
        else
            return false;
    }

    /**
     * This method applies PEDMAS.
     * It does not apply to braces as braces are handled separately.
     * @param c Character to be checked for the order of expression.
     * @return It returns the order for operations.
     * 4 - for exponential
     * 3 - for multiply and division.
     * 2 - for addition and subtraction.
     * 1 - for braces.
     * 0 - if it's not an operand.
     * 
     */
    public int prec(char c)
    {
        int res;
        if(c == '(' || c == ')')
            res = 1;
        else if(c == '^')
            res = 4;
        else if(c == '/' || c == '*')
            res = 3;
        else if(c == '+' || c == '-')
            res  =2;
        else
            res = 0;        
        return res;
    }

    /**
     * This method is to be called to test the class.
     * and it prints the final results.
     * This method also saves the results and checks if user want to continue or not.
     * 
     */
    public void calculator()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        double res = 0;
        while(!(input.equalsIgnoreCase("Q")))
        {
            System.out.println("Enter Input");
            try
            {
                input = br.readLine();
                input = input.trim();
                if(input.equalsIgnoreCase("Q"))
                {
                    System.out.println("Have a good day");
                    break;
                }
                else
                {
                    res = calculation(input);
                    if(res == Double.MAX_VALUE)
                        System.out.println("Expression is wrong");
                    else if(res %1 == 0)
                    {
                        System.out.println((int)res);
                        this.setResult(res);

                    }
                    else
                    {
                        System.out.println(res);
                        this.setResult(res);
                    }
                }
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Instantiates a new calculator
     * Please end your sentence with = sign.
     * if the expression starts with a - sign then its considered as negative number.
     */
    public Calculator()
    {
        this.result = 0;
        calculator();
    }
}

