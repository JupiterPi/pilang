package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.script.instructions.DeclareVariableInstruction;
import jupiterpi.pilang.script.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Scope {
    private List<Variable> variables = new ArrayList<>();

    public void execute(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            if (instruction instanceof DeclareVariableInstruction) {
                DeclareVariableInstruction instr = (DeclareVariableInstruction) instruction;
                Variable variable = new Variable(instr.getName(), instr.getValue());
                variables.add(variable);
            }
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
