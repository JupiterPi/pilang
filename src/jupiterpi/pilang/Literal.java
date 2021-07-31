package jupiterpi.pilang;

public class Literal extends Value {
    private String value;

    public Literal(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "value='" + value + '\'' +
                '}';
    }
}
