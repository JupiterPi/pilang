package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

public abstract class Wrapper extends Value {
    protected Value source;

    protected Wrapper(Value source) {
        this.source = source;
    }

    public Value getSource() {
        return source;
    }

    @Override
    public abstract DataType getType(Scope scope);

    @Override
    public abstract String get(Scope scope);

    @Override
    public String toString() {
        return "wrapper{" + source + "}";
    }
}
