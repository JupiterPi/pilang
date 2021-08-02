package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

public abstract class Value {
    public abstract DataType getType(Scope scope);
    public abstract String get(Scope scope);

    public int getInteger(Scope scope) {
        return Integer.parseInt(get(scope));
    }
}
