package com.kruth.symbol.instructions;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;

/**
 * Created by kruthar on 2/23/16.
 */
public class Println {
   public static void parse(String line, Boolean execute) {
       if (execute) {
           Expression expression = new Expression(line);
           Literal literal = expression.evaluate();
           System.out.println(literal.toString());
       }
    }
}
