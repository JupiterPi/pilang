package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

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
    public String get(Scope scope) {
        checkTypes(scope);
        return Integer.toString(calculate(scope));
    }

    @Override
    public DataType getType(Scope scope) {
        checkTypes(scope);
        return a.getType(scope);
    }

    private void checkTypes(Scope scope) {
        if (!(a.getType(scope).equals(b.getType(scope)))) new Exception("mismatching types: a: " + a.getType(scope) + "b: " + b.getType(scope)).printStackTrace();
    }

    private int calculate(Scope scope) {
        int a = this.a.getInteger(scope);
        int b = this.b.getInteger(scope);
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
