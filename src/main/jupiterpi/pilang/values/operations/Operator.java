package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public abstract class Operator {
    protected PrecedenceLevel precedenceLevel;
    protected String operator;

    protected Operator(String operator, StringSet allowedOperators, PrecedenceLevel precedenceLevel) {
        if (allowedOperators.contains(operator)) {
            this.operator = operator;
        } else new Exception("invalid operator: " + operator).printStackTrace();
        this.precedenceLevel = precedenceLevel;
    }

    public static Operator makeOperator(String operator) {
        if (NumericOperator.numericOperators.contains(operator)) return new NumericOperator(operator);
        if (ComparisonOperator.comparisonOperators.contains(operator)) return new ComparisonOperator(operator);
        if (NumericComparisonOperator.numericComparisonOperators.contains(operator)) return new NumericComparisonOperator(operator);
        if (LogicalOperators.logicalOperators.contains(operator)) return new LogicalOperators(operator);

        new Exception("invalid operator: " + operator).printStackTrace();
        return null;
    }

    public PrecedenceLevel getPrecedenceLevel() {
        return precedenceLevel;
    }
    public String getOperator() {
        return operator;
    }

    public abstract Value apply(Value a, Value b, Scope scope);
    public abstract DataType getType(Value a, Value b, Scope scope);

    @Override
    public String toString() {
        return operator;
    }

    public enum PrecedenceLevel {
        NONE, SINGLE, BUNDLE, BUNDLE_LARGER
    }
}
