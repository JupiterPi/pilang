package jupiterpi.pilang.values.arrays;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.Wrapper;

import java.util.List;

public class ArrayCallWrapper extends Wrapper {
    private Value index;

    public ArrayCallWrapper(Value array, Value index) {
        super(array);
        this.index = index;
    }

    @Override
    public DataType getType(Scope scope) {
        return source.getType(scope).sp_of();
    }

    @Override
    public String get(Scope scope) {
        List<Value> array = this.source.getArray(scope);
        int index = this.index.getInteger(scope);
        try {
            return array.get(index).get(scope);
        } catch (IndexOutOfBoundsException e) {
            new Exception("array index out of bounds: " + index + " in array " + array).printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "ArrayCallWrapper{" +
                "index=" + index +
                ", source=" + source +
                '}';
    }
}
