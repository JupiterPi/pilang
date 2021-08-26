package jupiterpi.pilang.script.instructions.structures;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public abstract class StructureInstruction extends Instruction {
    protected boolean evaluate(Value condition, Scope scope) {
        DataType type = condition.getType(scope);
        if (type.equals(new DataType(DataType.BaseType.BOOL))) {
            return condition.getBool(scope);
        } else {
            new Exception("condition has to be of type bool, instead found: " + condition.getType(scope)).printStackTrace();
            return false;
        }
    }
}
