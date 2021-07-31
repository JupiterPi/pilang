package jupiterpi.pilang;

public class Main {
    public static void main(String[] args) {
        String expr = "5 + (5 - (2 + 1))";
        System.out.println(expr);
        Expression expression = new Expression(expr);
        System.out.println(expression.getOperation().toString());
        System.out.println(expression.get());
    }
}
