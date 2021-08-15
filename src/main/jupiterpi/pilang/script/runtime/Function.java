package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.functions.VariableHead;

import java.util.List;

public class Function extends Scope {
    private String reference;

    private List<VariableHead> parameters;
    private List<Instruction> instructions;

    public Function(Scope parentScope, List<VariableHead> parameters, List<Instruction> instructions) {
        super(parentScope);
        this.parameters = parameters;
        this.instructions = instructions;

        this.reference = parentScope.getRegistry().registerFunction(this);
    }

    public String getReference() {
        return reference;
    }

    public Value execute() {
        for (Instruction instruction : instructions) {
            instruction.execute(this);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Function{" +
                "reference='" + reference + '\'' +
                ", parameters=" + parameters +
                ", instructions=" + instructions +
                '}';
    }
}
