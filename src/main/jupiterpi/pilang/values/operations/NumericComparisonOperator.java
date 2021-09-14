package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.util.StringSet;
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
    public Value apply(Value a, Value b) {
        return null; // TODO implement apply()
    }
}
