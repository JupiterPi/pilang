package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public class Variable extends Value {
    private String name;
    private DataType type;
    private String value;

    public Variable(Scope scope, String name, Value value) {
        this.name = name;
        this.type = value.getType(scope);
        this.value = value.get(scope);
    }

    public String getName() {
        return name;
    }

    @Override
    public DataType getType(Scope scope) {
        return type;
    }

    @Override
    public String get(Scope scope) {
        return value;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
