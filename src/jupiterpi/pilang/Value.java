package jupiterpi.pilang;

public abstract class Value {
    public abstract String get();

    public int getInteger() {
        return Integer.parseInt(get());
    }
}
