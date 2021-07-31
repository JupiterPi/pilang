package jupiterpi.pilang.values;

public class Operation extends Value {
    private String operator;
    private Value a;
    private Value b;

    public Operation(Value a, String operator, Value b) {
        this.operator = operator;
        this.a = a;
        this.b = b;
    }

    @Override
    public String get() {
        return Integer.toString(calculate());
    }

    private int calculate() {
        int a = this.a.getInteger();
        int b = this.b.getInteger();
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
        }
        new Exception("invalid operator: " + operator).printStackTrace();
        return 0;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operator='" + operator + '\'' +
                ", a=" + a.toString() +
                ", b=" + b.toString() +
                '}';
    }
}
