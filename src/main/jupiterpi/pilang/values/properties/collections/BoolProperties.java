package jupiterpi.pilang.values.properties.collections;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.properties.PropertiesCollection;

public class BoolProperties extends PropertiesCollection {
    public BoolProperties() {
        setDefaultRequiredType(new DataType(DataType.BaseType.BOOL));

        // same type
        addProperty("bool", new DataType(DataType.BaseType.BOOL), (source, scope) -> {
            return source;
        });

        // to string
        addProperty("str", new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), (source, scope) -> {
            return FinalValue.fromString(source.get(scope));
        });
    }
}
