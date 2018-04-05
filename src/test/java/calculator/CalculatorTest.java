package calculator;

import static org.junit.Assert.*;

import org.junit.Test;

import calculator.Calculator;

public class CalculatorTest
{

    @Test
    public void postfixTest()
    {
        String str = "((15.2/(7.5-(1.2+1.4)))*3.3)-(2.2+(1.6+1.4))";
        Calculator cal = new Calculator();
        str = cal.postfix(str);
        String str2 = "15.2 7.5 1.2 1.4 +-/3.3 *2.2 1.6 1.4 ++-";
        assertEquals(str,str2);
    }
    
    @Test
    public void operTest()
    {
        Calculator cal = new Calculator();
        boolean res = cal.oper('^');
        assertTrue(res);
        res = cal.oper('$');
        assertFalse(res);
        res = cal.oper('/');
        assertTrue(res);
        res = cal.oper('*');
        assertTrue(res);
        res = cal.oper('+');
        assertTrue(res);
        res = cal.oper('-');
        assertTrue(res);
        res = cal.oper('(');
        assertFalse(res);
        res = cal.oper(' ');
        assertFalse(res);
        res = cal.oper('1');
        assertFalse(res);
     }
    
    @Test
    public void precTest()
    {
        Calculator cal = new Calculator();
        int num2;
        int num = cal.prec('(');
        assertEquals(num,1);
        num2 = cal.prec('(');
        assertEquals(num2,1);
        assertEquals(num,num2);
        num = cal.prec('}');
        assertEquals(num,0);
        num = cal.prec('[');
        assertEquals(num,0);
        num = cal.prec('*');
        assertEquals(num,3);
        num2 = cal.prec('/');
        assertEquals(num2,3);
        assertEquals(num,num2);
        num = cal.prec('+');
        assertEquals(num,2);
        num2 = cal.prec('-');
        assertEquals(num2,2);
        assertEquals(num, num2);
        num = cal.prec('^');
        assertEquals(num,4);
    }
    
    @Test
    public void evalTest()
    {
        Calculator cal = new Calculator();
        String str = "15.2 7.5 1.2 1.4 +-/3.3 *2.2 1.6 1.4 ++-";
        double num = cal.evaluate(str);
        double num2 = 5.036734693877549;
        assertEquals(num, num2, 0.0001);
        str = "15.2 _7.5 1.2 1.4 +-/3.3 *2.2 1.6 _1.4 +^-";
        num = cal.evaluate(str);
        num2 = -6.137141546628;
        assertEquals(num,num2,0.0001);
        str = "15/0=";
       num = cal.evaluate(str);
        str = "15.2 _7.5 1.2 1.4 A-/3.3 *2.2 1.6 _1.4 +^-";
        num = cal.evaluate(str);
        assertEquals(num,Double.MAX_VALUE,0.0001);
    }
    
    @Test
    public void notationTest()
    {
        String str = "((15.2/(7.5-(1.2+1.4)))*3.3)-(2.2^(1.6+1.4))";
        Calculator cal = new Calculator();
        boolean res = cal.notation(str);
        assertTrue(res);
        str = "((15.2/(7.5-(1.2+1.4)))*3.3)-(2.2^(1.6+1.4)";
        res = cal.notation(str);
        assertFalse(res);
        str = "((15.2/(_7.5-(1.2+1.4)))*3.3)-(2.2^(1.6+_1.4))";
        res = cal.notation(str);
        assertTrue(res);
        str = "((15.2/(_7.5-(1.2++1.4)))*3.3)-(2.2^(1.6+_1.4))";
        res = cal.notation(str);
        assertFalse(res);
        str = "((15.2/(_7.5@(1.2%+1.4)))*3.3)&(2.2^(1.6+_1.4))";
        res = cal.notation(str);
        assertFalse(res);
       
    }
    
    @Test
    public void negativeTest()
    {
        String str = "((-15.2/(7.5-(-1.2+1.4)))*3.3)-(2.2^(1.6+-1.4))=";
        String str1 = "((_15.2/(7.5-(_1.2+1.4)))*3.3)-(2.2^(1.6+_1.4))";
        Calculator cal = new Calculator();
        String str2 = cal.negative(str);
        assertEquals(str2,str1);
        
        str1 = "WRONG";
        str = "((-15.2/(7.5-(-1.2+1.4)))*3.3)-(2.2^(1.6+-1.4))";
        str2 = cal.negative(str);
        assertEquals(str2,str1);
        cal.setResult(5.5);
        str = "+2.0=";
        str1 = "5.5000+2.0";
        str2 = cal.negative(str);
        assertEquals(str1,str2);
        str = "+2.5+3.4=";
        str2 = cal.negative(str);
        str1 = "5.5000+2.5+3.4";
        assertEquals(str1,str2);
        str = "--2.5+3.4=";
        str1 = "5.5000-_2.5+3.4";
        str2 = cal.negative(str);
        assertEquals(str1,str2);
        str = "1      -1=";
        str1 = "1-1";
        str2 = cal.negative(str);
        assertEquals(str1,str2);
    }
    
    @Test
    public void calcTest()
    {
        String str = "((-15.2/(7.5-(-1.2+1.4)))*3.3)-(2.2^(1.6+-1.4))=";
        double res = -8.042037789;
        Calculator cal = new Calculator();
        double res2 = cal.calculation(str);
        assertEquals(res,res2,0.00001);
        str = "(-15.2/(7.5-(-1.2+1.4)))*3.3)-(2.2^(1.6+-1.4))=";
        res2 = cal.calculation(str);
        assertEquals(Double.MAX_VALUE,res2,0.0001);
        str = "15+++=";
        res2 = cal.calculation(str);
        assertEquals(Double.MAX_VALUE,res2,0.0001);
    }
    
//    @Test
//    public void calculationTest()
//    {
//        Calculator cal = new Calculator();
//        cal.calculation();
//    }

}
