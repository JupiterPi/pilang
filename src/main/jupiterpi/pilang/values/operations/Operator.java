package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.values.Value;

public abstract class Operator {
    protected PrecedenceLevel precedenceLevel;

    protected Operator() {
        this.precedenceLevel = null;
    }

    public Operator(PrecedenceLevel precedenceLevel) {
        this.precedenceLevel = precedenceLevel;
    }

    public abstract Value apply(Value a, Value b);

    public enum PrecedenceLevel {
        NONE, SINGLE, BUNDLE, BUNDLE_LARGER
    }
}
