package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.runtime.Scope;
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
        scope.getVariable(name).set(scope, value);
    }

    @Override
    public String toString() {
        return "ReassignVariableInstruction{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
