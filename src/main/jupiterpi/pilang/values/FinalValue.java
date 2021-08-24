package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

public class FinalValue extends Value {
    protected DataType type;
    protected String value;

    public FinalValue(DataType type, String value) {
        this.type = type;
        this.value = value;
    }

    public FinalValue(Value original, Scope scope) {
        this.type = original.getType(scope);
        this.value = original.get(scope);
    }

    protected FinalValue() {}

    /* runtime getters */

    @Override
    public DataType getType(Scope scope) {
        return type;
    }

    @Override
    public String get(Scope scope) {
        return value;
    }

    /* final getters */

    public DataType getType() {
        return type;
    }

    public String get() {
        return value;
    }

    /* others */

    @Override
    public String toString() {
        return "Final{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
