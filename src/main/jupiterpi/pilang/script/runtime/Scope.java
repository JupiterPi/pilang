package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.values.Value;

import java.util.ArrayList;
import java.util.List;

public class Scope {
    private List<Variable> variables = new ArrayList<>();
    private Scope parentScope = null;

    protected Scope() {}
    protected Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

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
        if (parentScope != null) return parentScope.getVariable(name);
        return null;
    }

    public ReferenceRegistry getRegistry() {
        return parentScope.getRegistry();
    }

    /* interaction from inside */

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    public void returnValue(Value value) {
        parentScope.returnValue(value);
    }
}
