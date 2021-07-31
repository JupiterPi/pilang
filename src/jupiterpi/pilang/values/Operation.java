package jupiterpi.pilang.values;

public class Operation extends Value {
    private String operator;
    private Value a;
    private Value b;
    private DataType type;

    public Operation(Value a, String operator, Value b) {
        this.operator = operator;
        if (a.getType().equals(b.getType())) {
            this.a = a;
            this.b = b;
            type = a.getType();
        } else new Exception("mismatching types: a: " + a.getType() + "b: " + b.getType()).printStackTrace();
    }

    @Override
    public String get() {
        return Integer.toString(calculate());
    }

    @Override
    public DataType getType() {
        return type;
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
