package jupiterpi.pilang.values;

public abstract class Wrapper extends Value {
    protected Value source;

    protected Wrapper(Value source) {
        this.source = source;
    }

    public Value getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "wrapper{" + source + "}";
    }
}
