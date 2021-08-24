package jupiterpi.pilang.values.other;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;

import java.util.Arrays;
import java.util.List;

public class Literal extends FinalValue {
    private final List<String> numbers = Arrays.asList("0123456789".split(""));

    public Literal(String value) {
        if (value.startsWith("'")) {
            if (value.length() == 3) {
                type = new DataType(DataType.BaseType.CHAR);
            } else new Exception("invalid char: " + value).printStackTrace();
        } else {
            if (listContains(numbers, value.substring(0, 1))) {
                if (value.contains(".")) {
                    type = new DataType(DataType.BaseType.FLOAT);
                } else {
                    type = new DataType(DataType.BaseType.INT);
                }
            } else {
                type = new DataType(DataType.BaseType.BOOL);
            }
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
    public String toString() {
        return "Literal{" + value + "}";
    }
}
