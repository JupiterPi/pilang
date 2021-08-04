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
        if (isSameType(scope)) {
            if (operator.equals("==")) {
                String a = this.a.get(scope);
                String b = this.b.get(scope);
                return Boolean.toString(a.equals(b));
            } else {
                switch (getType(scope).toString()) {
                    case "int": return Integer.toString((int) calculateNumber(scope));
                    case "float": return Float.toString(calculateNumber(scope));
                    case "bool": return Boolean.toString(calculateLogical(scope));
                }
            }
            new Exception("unknown data type: " + a.getType(scope)).printStackTrace();
            return null;
        } else {
            if (operator.equals("==")) return "false";
            else {
                new Exception("cannot perform operation " + operator + " on unequal data types: " + a.getType(scope) + " and " + b.getType(scope)).printStackTrace();
                return null;
            }
        }
    }

    @Override
    public DataType getType(Scope scope) {
        if (operator.equals("==")) return new DataType(DataType.BaseType.BOOL);
        if (isSameType(scope)) {
            return a.getType(scope);
        } else {
            new Exception("cannot perform operation " + operator + " on unequal data types: " + a.getType(scope) + " and " + b.getType(scope)).printStackTrace();
            return null;
        }
    }
    private boolean isSameType(Scope scope) {
        return a.getType(scope).equals(b.getType(scope));
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

    private boolean calculateLogical(Scope scope) {
        boolean a = Boolean.parseBoolean(this.a.get(scope));
        boolean b = Boolean.parseBoolean(this.b.get(scope));
        switch (operator) {
            case "&&": return a && b;
            case "||": return a || b;
        }
        new Exception("invalid operator: " + operator).printStackTrace();
        return false;
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
