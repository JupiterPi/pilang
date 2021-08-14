package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.values.Value;

public class ValueItem implements Item {
    private Value value;

    public ValueItem(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
