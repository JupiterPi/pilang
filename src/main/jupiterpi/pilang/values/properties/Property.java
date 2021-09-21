package jupiterpi.pilang.values.properties;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.Wrapper;

public class Property extends Wrapper {
    public Property(Value source, String propertyName) {
        super(source);
    }

    @Override
    public DataType getType(Scope scope) {
        return null;
    }

    @Override
    public String get(Scope scope) {
        return null;
    }
}
