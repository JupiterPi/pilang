package jupiterpi.pilang.script.instructions.structures;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.Value;

import java.util.List;

public class WhileInstruction extends StructureInstruction {
    private Value condition;
    private List<Instruction> body;

    public WhileInstruction(Value condition, List<Instruction> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Scope parentScope) {
        while (evaluate(condition, parentScope)) {
            Scope scope = new Scope(parentScope);
            scope.execute(body);
        }
    }
}
