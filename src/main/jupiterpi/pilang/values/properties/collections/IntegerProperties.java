package jupiterpi.pilang.values.properties.collections;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.properties.PropertiesCollection;

public class IntegerProperties extends PropertiesCollection {
    public IntegerProperties() {
        setDefaultRequiredType(new DataType(DataType.BaseType.INT));

        addProperty("str", new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), (source, scope) -> {
            return FinalValue.fromString(source.get(scope));
        });
    }
}
