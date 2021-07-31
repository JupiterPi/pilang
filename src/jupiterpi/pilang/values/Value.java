package jupiterpi.pilang.values;

public abstract class Value {
    public abstract DataType getType();
    public abstract String get();

    public int getInteger() {
        return Integer.parseInt(get());
    }
}
