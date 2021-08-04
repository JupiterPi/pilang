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
        if (getType(scope).equals(new DataType(DataType.BaseType.INT))) {
            return Integer.toString((int) calculateNumber(scope));
        }
        if (getType(scope).equals(new DataType(DataType.BaseType.FLOAT))) {
            return Float.toString(calculateNumber(scope));
        }
        new Exception("unknown data type: " + a.getType(scope)).printStackTrace();
        return null;
    }

    @Override
    public DataType getType(Scope scope) {
        checkTypes(scope);
        return a.getType(scope);
    }

    private void checkTypes(Scope scope) {
        if (!(a.getType(scope).equals(b.getType(scope)))) new Exception("mismatching types: a: " + a.getType(scope) + ", b: " + b.getType(scope)).printStackTrace();
    }

    private float calculateNumber(Scope scope) {
        float a = Float.parseFloat(this.a.get(scope));
        float b = Float.parseFloat(this.b.get(scope));
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
