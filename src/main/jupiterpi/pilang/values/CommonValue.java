package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

public abstract class CommonValue extends Value {
    @Override
    public DataType getTechnicalType(Scope scope) {
        return getType(scope);
    }
}
