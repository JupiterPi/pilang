package jupiterpi.pilang.values.parsing.signs;

import jupiterpi.pilang.values.Value;

public class ValueSign implements Sign {
    private Value value;

    public ValueSign(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
