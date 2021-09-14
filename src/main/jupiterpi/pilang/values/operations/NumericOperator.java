package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.Value;

public class NumericOperator extends Operator {
    public final StringSet numericOperators = new StringSet("+", "-", "*", "/");
    public final StringSet numericOperators_base = new StringSet("+", "-");
    public final StringSet numericOperators_precedence = new StringSet("*", "/");

    private String operator;

    public NumericOperator(String operator) {
        super();
        if (numericOperators.contains(operator)) {
            if (numericOperators_precedence.contains(operator)) {
                this.precedenceLevel = PrecedenceLevel.SINGLE;
            } else {
                this.precedenceLevel = PrecedenceLevel.NONE;
            }
        } else new Exception("invalid operator: " + operator).printStackTrace();

        this.operator = operator;
    }

    @Override
    public Value apply(Value a, Value b) {
        return null; // TODO implement apply()
    }
}
