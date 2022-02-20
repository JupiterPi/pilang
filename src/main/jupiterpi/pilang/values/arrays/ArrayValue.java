package jupiterpi.pilang.values.arrays;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.CommonValue;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.tools.util.AppendingList;

import java.util.ArrayList;
import java.util.List;

public class ArrayValue extends Value {
    protected List<Value> values;

    public ArrayValue() { this.values = new ArrayList<>(); }
    public ArrayValue(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValues() {
        return values;
    }

    public void addValue(Value value) {
        values.add(value);
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

        return type.sp_asArray();
    }

    @Override
    public DataType getTechnicalType(Scope scope) {
        getType(scope);
        return values.size() == 0 ? null : values.get(0).getTechnicalType(scope);
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
