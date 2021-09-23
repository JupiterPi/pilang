package jupiterpi.pilang.values.properties.collections;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.properties.PropertiesCollection;

public class IntProperties extends PropertiesCollection {
    public IntProperties() {
        setDefaultRequiredType(new DataType(DataType.BaseType.INT));

        // same type
        addProperty("int", new DataType(DataType.BaseType.INT), (source, scope) -> {
            return source;
        });

        // type conversion
        addProperty("float", new DataType(DataType.BaseType.FLOAT), (source, scope) -> {
            return FinalValue.fromFloat((float) source.getInteger(scope));
        });

        // to string
        addProperty("str", new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), (source, scope) -> {
            return FinalValue.fromString(source.get(scope));
        });
    }
}
