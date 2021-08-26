package jupiterpi.pilang.script.instructions.structures;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.Value;

import java.util.ArrayList;
import java.util.List;

public class IfInstruction extends StructureInstruction {
    private Value condition;
    private List<Instruction> positiveBody;
    private List<Instruction> negativeBody;

    public IfInstruction(Value condition, List<Instruction> positiveBody, List<Instruction> negativeBody) {
        this.condition = condition;
        this.positiveBody = positiveBody;
        this.negativeBody = negativeBody;
        if (negativeBody == null) this.negativeBody = new ArrayList<>();
    }

    @Override
    public void execute(Scope parentScope) {
        List<Instruction> body = evaluate(condition, parentScope) ? positiveBody : negativeBody;
        Scope scope = new Scope(parentScope);
        scope.execute(body);
    }
}
