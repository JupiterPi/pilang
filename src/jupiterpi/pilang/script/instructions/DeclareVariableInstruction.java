package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public class DeclareVariableInstruction extends Instruction {
    private DataType type;
    private String name;
    private Value value;

    public DeclareVariableInstruction(DataType type, String name, Value value) {
        System.out.println(type);
        System.out.println(name);
        System.out.println(value);

        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(Scope scope) {
        if (type.equals(value.getType(scope))) {
            Variable variable = new Variable(scope, name, value);
            scope.addVariable(variable);
        } else {
            new Exception("mismatching types: specified: " + type + ", value: (" + value.getType(scope) + ") " + value).printStackTrace();
        }
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
