package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

import java.util.Arrays;
import java.util.List;

public class Literal extends Value {
    private final List<String> numbers = Arrays.asList("0123456789".split(""));

    private DataType type;
    private String value;

    public Literal(String value) {
        if (value.contains(".")) {
            type = new DataType(DataType.BaseType.FLOAT);
        } else {
            type = new DataType(DataType.BaseType.INT);
        }
        this.value = value;
    }

    private boolean listContains(List<String> list, String c) {
        for (String lc : list) {
            if (lc.equals(c)) return true;
        }
        return false;
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
        return "Literal{" +
                "value='" + value + '\'' +
                '}';
    }
}
