package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.Value;

public class NumericComparisonOperator extends Operator {
    public static final StringSet numericComparisonOperators = new StringSet("<", ">", "<=", ">=");

    private String operator;

    public NumericComparisonOperator(String operator) {
        super(PrecedenceLevel.BUNDLE);
        if (numericComparisonOperators.contains(operator)) {
            this.operator = operator;
        } else new Exception("invalid operator: " + operator).printStackTrace();
    }

    @Override
    public Value apply(Value a, Value b, Scope scope) {
        DataType type = a.getType(scope);
        if (!type.equals(b.getType(scope))) new Exception("values in " + operator + " operation have to be of equal type!").printStackTrace();
        if (type.equals(new DataType(DataType.BaseType.INT)) || type.equals(new DataType(DataType.BaseType.FLOAT))) {
            float av = Float.parseFloat(a.get(scope));
            float bv = Float.parseFloat(b.get(scope));
            boolean result = false;
            switch (operator) {
                case "<": result = av < bv; break;
                case ">": result = av > bv; break;
                case "<=": result = av <= bv; break;
                case ">=": result = av >= bv; break;
                default: new Exception("invalid operator: " + operator).printStackTrace();
            }
            return FinalValue.fromBool(result);
        } else {
            new Exception("values in " + operator + " operation have to be of int or float type!").printStackTrace();
        }
        return null;
    }
}
