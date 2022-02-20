package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.util.StringSet;
import jupiterpi.pilang.values.CommonValue;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.Arrays;
import java.util.List;

public class Operation extends CommonValue {
    private Operator operator;
    private Value a;
    private Value b;

    public Operation(Value a, Operator operator, Value b) {
        this.operator = operator;
        this.a = a;
        this.b = b;
    }

    @Override
    public String get(Scope scope) {
        return operator.apply(a, b, scope).get(scope);
    }

    @Override
    public DataType getType(Scope scope) {
        return operator.getType(a, b, scope);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operator='" + operator + '\'' +
                ", a=" + a.toString() +
                ", b=" + b.toString() +
                '}';
    }
}
