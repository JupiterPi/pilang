package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.script.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Scope {
    private List<Variable> variables = new ArrayList<>();

    public void execute(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            instruction.execute(this);
        }
    }

    public List<Variable> getVariables() {
        return new ArrayList<>(variables);
    }

    public Variable getVariable(String name) {
        for (Variable variable : variables) {
            if (variable.getName().equals(name)) return variable;
        }
        return null;
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }
}
