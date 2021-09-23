package jupiterpi.pilang.values.properties.collections;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.properties.PropertiesCollection;

public class PrimitiveArrayProperties extends PropertiesCollection {
    public PrimitiveArrayProperties() {
        // length

        // int[]
        addProperty("length", new DataType(DataType.BaseType.INT, DataType.Specification.ARRAY), new DataType(DataType.BaseType.INT), (source, scope) -> {
            return FinalValue.fromInt(source.getArray(scope).size());
        });

        // float[]
        addProperty("length", new DataType(DataType.BaseType.FLOAT, DataType.Specification.ARRAY), new DataType(DataType.BaseType.INT), (source, scope) -> {
            return FinalValue.fromInt(source.getArray(scope).size());
        });

        // bool[]
        addProperty("length", new DataType(DataType.BaseType.BOOL, DataType.Specification.ARRAY), new DataType(DataType.BaseType.INT), (source, scope) -> {
            return FinalValue.fromInt(source.getArray(scope).size());
        });

        // char[]
        addProperty("length", new DataType(DataType.BaseType.CHAR, DataType.Specification.ARRAY), new DataType(DataType.BaseType.INT), (source, scope) -> {
            return FinalValue.fromInt(source.getArray(scope).size());
        });
    }
}
