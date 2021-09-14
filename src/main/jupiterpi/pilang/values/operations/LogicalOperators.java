package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
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
    public Value apply(Value a, Value b, Scope scope) {
        if (a.getType(scope).equals(new DataType(DataType.BaseType.BOOL)) && b.getType(scope).equals(new DataType(DataType.BaseType.BOOL))) {
            boolean av = a.getBool(scope);
            boolean bv = b.getBool(scope);
            boolean result = false;
            switch (operator) {
                case "&&": result = av && bv; break;
                case "||": result = av || bv; break;
                default: new Exception("invalid operator: " + operator).printStackTrace();
            }
            return FinalValue.fromBool(result);
        } else {
            new Exception("values in " + operator + " operation have to be of bool type!").printStackTrace();
        }
        return null;
    }
}
