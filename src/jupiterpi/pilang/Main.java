package jupiterpi.pilang;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression("5 + 3 - 4");
        System.out.println(expression.getOperation().toString());
        System.out.println(expression.get());
    }
}
