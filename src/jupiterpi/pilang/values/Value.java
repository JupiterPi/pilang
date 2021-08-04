package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

public abstract class Value {
    public abstract DataType getType(Scope scope);
    public abstract String get(Scope scope);

    public int getInteger(Scope scope) {
        if (getType(scope).equals(new DataType(DataType.BaseType.INT))) {
            return Integer.parseInt(get(scope));
        } else {
            new Exception(String.format("tried to get int value of %s %s", getType(scope), get(scope))).printStackTrace();
            return 0;
        }
    }

    public float getFloat(Scope scope) {
        if (getType(scope).equals(new DataType(DataType.BaseType.FLOAT))) {
            return Float.parseFloat(get(scope));
        } else {
            new Exception(String.format("tried to get float value of %s %s", getType(scope), get(scope))).printStackTrace();
            return 0.0f;
        }
    }
}
