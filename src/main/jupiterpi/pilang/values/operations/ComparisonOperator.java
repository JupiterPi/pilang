package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.Value;

public class ComparisonOperator extends Operator {
    public static final StringSet comparisonOperators = new StringSet("==", "!=");

    private boolean inverted = false;

    public ComparisonOperator(String operator) {
        super(PrecedenceLevel.BUNDLE);
        if (comparisonOperators.contains(operator)) {
            inverted = operator.equals("!=");
        } else new Exception("invalid operator: " + operator).printStackTrace();
    }

    @Override
    public Value apply(Value a, Value b) {
        return null; // TODO implement apply()
    }
}
