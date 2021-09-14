package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.Value;

public class NumericOperator extends Operator {
    public static final StringSet numericOperators = new StringSet("+", "-", "*", "/");
    public static final StringSet numericOperators_base = new StringSet("+", "-");
    public static final StringSet numericOperators_precedence = new StringSet("*", "/");

    public NumericOperator(String operator) {
        super(operator, numericOperators, null);
        if (numericOperators_precedence.contains(operator)) {
            this.precedenceLevel = PrecedenceLevel.SINGLE;
        } else {
            this.precedenceLevel = PrecedenceLevel.NONE;
        }
    }

    @Override
    public Value apply(Value a, Value b, Scope scope) {
        DataType type = a.getType(scope);
        if (!type.equals(b.getType(scope))) new Exception("values in " + operator + " operation have to be of equal type (currently " + a.getType(scope) + " and " + b.getType(scope) + ")!").printStackTrace();
        if (type.equals(new DataType(DataType.BaseType.INT)) || type.equals(new DataType(DataType.BaseType.FLOAT))) {
            float av = Float.parseFloat(a.get(scope));
            float bv = Float.parseFloat(b.get(scope));
            float result = 0.0f;
            switch (operator) {
                case "+": result = av + bv; break;
                case "-": result = av - bv; break;
                case "*": result = av * bv; break;
                case "/": result = av / bv; break;
                default: new Exception("invalid operator: " + operator).printStackTrace();
            }
            if (type.equals(new DataType(DataType.BaseType.INT))) return FinalValue.fromInt((int) result);
            else return FinalValue.fromFloat(result);
        } else {
            new Exception("values in " + operator + " operation have to be of int or float type (currently " + a.getType(scope) + " and " + b.getType(scope) + ")!").printStackTrace();
        }
        return null;
    }

    @Override
    public DataType getType(Value a, Value b, Scope scope) {
        return a.getType(scope);
    }
}
