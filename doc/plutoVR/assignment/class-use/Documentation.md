# Documentation

## `public class Calculator`

This Class will calculate an arithmetic expression. It respects order of operations according to PEDMAS.

 * **Author:** Monika

     <p>

## `private double result`

Variable which stores the last equations output.

## `public double getResult()`

This method is getter for the previous result.

 * **Returns:** It returns the previous expression's result.

## `public void setResult(double result)`

This method saves the value of arithmetic expression.

 * **Parameters:** `result` — Value of arithmetic expression to be saved.

## `public double calculation(String input)`

This method will call the methods for evaluation of string and return the result to calc method.

 * **Parameters:** `input` — Arithmetic expression which we want to calculate.
 * **Returns:** It returns the result of the expression.

## `public String negative(String input)`

This method takes care of negative numbers and previous result. This method converts the negative numbers to <em> _num </em> for further evaluation. And it also checks if we have <em> = </em> for evaluation.

 * **Parameters:** `input` — Arithmetic expression to be evaluated.
 * **Returns:** It returns the string with negative numbers handled 

     and also appends the previous result if expression requires.

     It returns <em> WRONG </em> if = sign is not used for evaluation.

## `public boolean notation(String input)`

This method checks if the expression is a valid notation or not. It checks if all the braces which were opened are closed or not. And it also checks if all the operators are binary. For e.g. use of <em> 4+++5</em> is not allowed.

 * **Parameters:** `input` — It takes the input returned from negative method. 

     That is all the negatives numbers are converted to _num.
 * **Returns:** It returns true if it's a valid notation and false if it's not a valid notation.

## `public double evaluate(String input)`

This method evaluates the string in the postfix expression and gives the result.

 * **Parameters:** `input` — It takes input as post fix expression.
 * **Returns:** It returns the value evaluated for the postfix expression.

     <p>

## `public String postfix(String input)`

This method converts the expression to postfix expression.

 * **Parameters:** `input` — This method takes a valid arithmetic expression.
 * **Returns:** It returns the postfix expression of the arithmetic expression.

     and returns <em> WRONG </em> if there is something wrong with arithmetic expression.

## `public boolean oper(char c)`

It checks if the character is an operator or not

 * **Parameters:** `c` — The character to be checked if it's an operator or not.
 * **Returns:** Returns true if the given character is an operator and 

     false if it's not an operator.

## `public int prec(char c)`

This method applies PEDMAS. It does not apply to braces as braces are handled separately.

 * **Parameters:** `c` — Character to be checked for the order of expression.
 * **Returns:** It returns the order for operations.

     4 - for exponential

     3 - for multiply and division.

     2 - for addition and subtraction.

     1 - for braces.

     0 - if it's not an operand.

     <p>

## `public void calculator()`

This method is to be called to test the class. and it prints the final results. This method also saves the results and checks if user want to continue or not.