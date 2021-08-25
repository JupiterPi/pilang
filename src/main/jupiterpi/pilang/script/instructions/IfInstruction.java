package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.ArrayList;
import java.util.List;

public class IfInstruction extends Instruction {
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
        List<Instruction> body = condition(parentScope) ? positiveBody : negativeBody;
        Scope scope = new Scope(parentScope);
        scope.execute(body);
    }

    private boolean condition(Scope scope) {
        DataType type = condition.getType(scope);
        if (type.equals(new DataType(DataType.BaseType.BOOL))) {
            return condition.getBool(scope);
        } else {
            new Exception("condition in if instruction has to be of type bool, instead found: " + condition.getType(scope)).printStackTrace();
            return false;
        }
    }
}
