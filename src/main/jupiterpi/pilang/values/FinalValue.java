package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

import java.util.ArrayList;
import java.util.List;

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

    /* typed constructors */

    public static FinalValue fromInt(int value) {
        return new FinalValue(new DataType(DataType.BaseType.INT), Integer.toString(value));
    }

    public static FinalValue fromFloat(float value) {
        return new FinalValue(new DataType(DataType.BaseType.INT), Float.toString(value));
    }

    public static FinalValue fromBool(boolean value) {
        return new FinalValue(new DataType(DataType.BaseType.INT), Boolean.toString(value));
    }

    public static FinalValue fromChar(char value) {
        return new FinalValue(new DataType(DataType.BaseType.INT), String.format("'%s'", value));
    }

    public static FinalValue formChar(String value) {
        return new FinalValue(new DataType(DataType.BaseType.INT), String.format("'%s'", value));
    }

    public static FinalValue fromString(String value) {
        return new FinalValue(new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), value);
    }

    public static FinalValue fromVoid() {
        return new FinalValue(new DataType(DataType.BaseType.VOID), "");
    }

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
