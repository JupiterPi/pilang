package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public class Variable extends Value {
    private String name;
    private DataType type;
    private String value;

    public Variable(String name, Value value) {
        this.name = name;
        this.type = value.getType();
        this.value = value.get();
    }

    @Override
    public DataType getType() {
        return type;
    }

    @Override
    public String get() {
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
