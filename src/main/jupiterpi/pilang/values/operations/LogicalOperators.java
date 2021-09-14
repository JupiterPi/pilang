package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.Value;

public class LogicalOperators extends Operator {
    public static final StringSet logicalOperators = new StringSet("&&", "||");

    private String operator;

    public LogicalOperators(String operator) {
        super(PrecedenceLevel.BUNDLE_LARGER);
        if (logicalOperators.contains(operator)) {
            this.operator = operator;
        } else new Exception("invalid operator: " + operator).printStackTrace();
    }

    @Override
    public Value apply(Value a, Value b) {
        return null; // TODO implement apply()
    }
}
