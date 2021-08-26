package jupiterpi.pilang.script.instructions.structures;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.Value;

import java.util.List;

public class ForInstruction extends StructureInstruction {
    private Instruction initialization;
    private Value condition;
    private Instruction counter;
    private List<Instruction> body;

    public ForInstruction(Instruction initialization, Value condition, Instruction counter, List<Instruction> body) {
        this.initialization = initialization;
        this.condition = condition;
        this.counter = counter;
        this.body = body;
    }

    @Override
    public void execute(Scope parentScope) {
        Scope forScope = new Scope(parentScope);
        forScope.execute(initialization);
        while (evaluate(condition, forScope)) {
            Scope scope = new Scope(forScope);
            scope.execute(body);
            forScope.execute(counter);
        }
    }
}
