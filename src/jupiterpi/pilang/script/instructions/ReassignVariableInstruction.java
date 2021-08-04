package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.Value;

public class ReassignVariableInstruction extends Instruction {
    private String name;
    private Value value;

    public ReassignVariableInstruction(String name, Value value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(Scope scope) {
        Variable variable = scope.getVariable(name);
        if (variable.getType(scope).equals(value.getType(scope))) {
            variable.set(scope, value);
        } else {
            new Exception("mismatching types: variable " + variable.getName() + ": " + variable.getType(scope) + ", new value: (" + value.getType(scope) + ") " + value).printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ReassignVariableInstruction{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
