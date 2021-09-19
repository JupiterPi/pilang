package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.precedence.PrecedenceLevel;

public class ComparisonOperator extends Operator {
    public static final StringSet comparisonOperators = new StringSet("==", "!=");

    public ComparisonOperator(String operator) {
        super(operator, comparisonOperators, PrecedenceLevel.BUNDLE);
    }

    @Override
    public Value apply(Value a, Value b, Scope scope) {
        boolean result = calculate(a, b, scope);
        if (operator.equals("!=")) result = !result;
        return FinalValue.fromBool(result);
    }
    private boolean calculate(Value a, Value b, Scope scope) {
        if (!a.getType(scope).equals(b.getType(scope))) return false;
        return a.get(scope).equals(b.get(scope));
    }

    @Override
    public DataType getType(Value a, Value b, Scope scope) {
        return new DataType(DataType.BaseType.BOOL);
    }
}
