package jupiterpi.pilang.values;

public class DataType {
    public enum BaseType {
        INT
    }

    private BaseType baseType;
    private boolean isArray = false;

    public DataType(BaseType baseType) {
        this.baseType = baseType;
    }

    public DataType(BaseType baseType, boolean isArray) {
        this.baseType = baseType;
        this.isArray = isArray;
    }

    /* getters */

    public BaseType getBaseType() {
        return baseType;
    }

    public boolean isArray() {
        return isArray;
    }

    /* modify */

    public DataType baseType(BaseType baseType) {
        return new DataType(baseType, isArray);
    }

    public DataType isArray(boolean isArray) {
        return new DataType(baseType, isArray);
    }

    public DataType clone() {
        return new DataType(baseType, isArray);
    }
    public DataType(DataType original) {
        this.baseType = original.getBaseType();
        this.isArray = original.isArray();
    }

    /* others */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataType dataType = (DataType) o;
        return isArray == dataType.isArray &&
                baseType == dataType.baseType;
    }

    @Override
    public String toString() {
        String str = this.baseType.toString().toLowerCase();
        if (isArray) str += "[]";
        return str;
    }
}
