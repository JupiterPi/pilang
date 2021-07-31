package jupiterpi.pilang;

import jupiterpi.pilang.values.parsing.Expression;

public class Main {
    public static void main(String[] args) {
        String expr = "2 + 5 * 3";
        System.out.println(expr);
        Expression expression = new Expression(expr);
        System.out.println(expression.getOperation().toString());
        System.out.println(expression.get());
    }
}
