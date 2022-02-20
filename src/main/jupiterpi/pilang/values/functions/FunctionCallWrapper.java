package jupiterpi.pilang.values.functions;

import jupiterpi.pilang.script.runtime.Function;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.Wrapper;

import java.util.List;

public class FunctionCallWrapper extends Wrapper {
    private List<Value> parameters;

    public FunctionCallWrapper(Value function, List<Value> parameters) {
        super(function);
        this.parameters = parameters;
    }

    @Override
    public DataType getType(Scope scope) {
        return source.getType(scope).sp_of();
    }

    @Override
    public DataType getTechnicalType(Scope scope) {
        return source.getTechnicalType(scope).sp_of();
    }

    @Override
    public String get(Scope scope) {
        Function function = this.source.getFunction(scope);
        return function.executeFunction(parameters, scope).get(scope);
    }

    @Override
    public String toString() {
        return "FunctionCallWrapper{" +
                "parameters=" + parameters +
                ", source=" + source +
                '}';
    }
}
