package jupiterpi.pilang.values.properties.collections;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.properties.PropertiesCollection;

public class FloatProperties extends PropertiesCollection {
    public FloatProperties() {
        setDefaultRequiredType(new DataType(DataType.BaseType.FLOAT));

        // same type
        addProperty("float", new DataType(DataType.BaseType.FLOAT), (source, scope) -> {
            return source;
        });

        // type conversion
        addProperty("int", new DataType(DataType.BaseType.INT), (source, scope) -> {
            return FinalValue.fromFloat((int) source.getFloat(scope));
        });

        // to string
        addProperty("str", new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), (source, scope) -> {
            return FinalValue.fromString(source.get(scope));
        });
    }
}
