package jupiterpi.pilang.values.properties;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.Wrapper;

public class PropertyWrapper extends Wrapper {
    private String propertyName;

    public PropertyWrapper(Value source, String propertyName) {
        super(source);
        this.propertyName = propertyName;
    }

    @Override
    public DataType getType(Scope scope) {
        return PropertiesCollection.getProperty(propertyName, source.getType(scope)).getReturnType();
    }

    @Override
    public DataType getTechnicalType(Scope scope) {
        return getType(scope);
    }

    @Override
    public String get(Scope scope) {
        return PropertiesCollection.getProperty(propertyName, source.getType(scope)).readProperty(source, scope).get(scope);
    }

    @Override
    public String toString() {
        return "PropertyWrapper{" +
                "propertyName=" + propertyName +
                ", source='" + source + '\'' +
                '}';
    }
}
