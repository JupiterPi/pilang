package jupiterpi.pilang.values.properties;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.function.BiFunction;

public class Property {
    private String propertyName;
    private DataType requiredType;
    private BiFunction<Value, Scope, Value> definition;
    private DataType returnType;

    public Property(String propertyName, DataType requiredType, BiFunction<Value, Scope, Value> definition, DataType returnType) {
        this.propertyName = propertyName;
        this.requiredType = requiredType;
        this.definition = definition;
        this.returnType = returnType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean suitsType(DataType type) {
        return type.equals(requiredType);
    }

    public Value readProperty(Value source, Scope scope) {
        return definition.apply(source, scope);
    }

    public DataType getReturnType() {
        return returnType;
    }
}