package jupiterpi.pilang.values.functions;

import jupiterpi.pilang.script.runtime.Function;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.List;

public class FunctionCallWrapper extends Value {
    private Value function;
    private List<Value> parameters;

    public FunctionCallWrapper(Value function, List<Value> parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    @Override
    public DataType getType(Scope scope) {
        return function.getType(scope).sp_of();
    }

    @Override
    public String get(Scope scope) {
        Function function = this.function.getFunction(scope);
        return function.executeFunction(parameters, scope).get(scope);
    }
}
