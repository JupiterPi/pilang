package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;

import java.util.List;

public class ArrayCallWrapper extends Value {
    private Value array;
    private Value index;

    public ArrayCallWrapper(Value array, Value index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public DataType getType(Scope scope) {
        return array.getType(scope).sp_of();
    }

    @Override
    public String get(Scope scope) {
        List<Value> array = this.array.getArray(scope);
        int index = this.index.getInteger(scope);
        try {
            return array.get(index).get(scope);
        } catch (IndexOutOfBoundsException e) {
            new Exception("array index out of bounds: " + index + " in array " + array).printStackTrace();
            return null;
        }
    }
}
