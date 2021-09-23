package jupiterpi.pilang.values.properties.collections;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.properties.PropertiesCollection;

public class CharProperties extends PropertiesCollection {
    public CharProperties() {
        setDefaultRequiredType(new DataType(DataType.BaseType.CHAR));

        // same type
        addProperty("char", new DataType(DataType.BaseType.CHAR), (source, scope) -> {
            return source;
        });

        // to string
        addProperty("str", new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), (source, scope) -> {
            return FinalValue.fromString("'" + source.get(scope) + "'");
        });
    }
}
