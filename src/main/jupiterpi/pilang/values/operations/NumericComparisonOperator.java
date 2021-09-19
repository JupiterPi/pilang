package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.precedence.PrecedenceLevel;

public class NumericComparisonOperator extends Operator {
    public static final StringSet numericComparisonOperators = new StringSet("<", ">", "<=", ">=");

    public NumericComparisonOperator(String operator) {
        super(operator, numericComparisonOperators, PrecedenceLevel.BUNDLE);
    }

    @Override
    public Value apply(Value a, Value b, Scope scope) {
        DataType type = a.getType(scope);
        if (!type.equals(b.getType(scope))) new Exception("values in " + operator + " operation have to be of equal type (currently " + a.getType(scope) + " and " + b.getType(scope) + ")!").printStackTrace();
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
            new Exception("values in " + operator + " operation have to be of int or float type (currently " + a.getType(scope) + " and " + b.getType(scope) + ")!").printStackTrace();
        }
        return null;
    }

    @Override
    public DataType getType(Value a, Value b, Scope scope) {
        return new DataType(DataType.BaseType.BOOL);
    }
}
