package jupiterpi.pilang.values.operations;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.Value;

public abstract class Operator {
    protected PrecedenceLevel precedenceLevel;

    public Operator(PrecedenceLevel precedenceLevel) {
        this.precedenceLevel = precedenceLevel;
    }

    public abstract Value apply(Value a, Value b, Scope scope);

    public enum PrecedenceLevel {
        NONE, SINGLE, BUNDLE, BUNDLE_LARGER
    }
}
