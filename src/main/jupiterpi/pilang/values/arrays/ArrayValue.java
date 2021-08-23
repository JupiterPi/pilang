package jupiterpi.pilang.values.arrays;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.tools.util.AppendingList;

import java.util.ArrayList;
import java.util.List;

public class ArrayValue extends Value {
    protected List<Value> values;

    protected ArrayValue() { this.values = new ArrayList<>(); }
    protected ArrayValue(List<Value> values) {
        this.values = values;
    }

    @Override
    public DataType getType(Scope scope) {
        DataType type = null;
        for (Value value : values) {
            if (type == null) {
                type = value.getType(scope);
            } else {
                if (!type.equals(value.getType(scope))) {
                    new Exception("cannot have array of unequal types: " + type + " and " + value.getType(scope)).printStackTrace();
                }
            }
        }

        if (type == null) {
            type = new DataType(DataType.BaseType.ANY);
        }
        return type.sp_asArray();
    }

    @Override
    public String get(Scope scope) {
        getType(scope);

        AppendingList str = new AppendingList();
        for (Value value : values) {
            str.add(value.get(scope));
        }
        return "[" + str.render(",") + "]";
    }

    @Override
    public String toString() {
        AppendingList str = new AppendingList();
        for (Value value : values) {
            str.add(value.toString());
        }
        return "Array{value=[" + str.render(", ") + "]}";
    }
}
