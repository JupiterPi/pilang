package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public class DeclareVariableInstruction extends Instruction {
    private DataType type;
    private String name;
    private Value value;

    public DeclareVariableInstruction(DataType type, String name, Value value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public DataType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DeclareVariableInstruction{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
