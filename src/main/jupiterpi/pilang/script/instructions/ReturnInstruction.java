package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.Value;

public class ReturnInstruction extends Instruction {
    private Value returnValue;

    public ReturnInstruction(Value returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void execute(Scope scope) {
        scope.returnValue(returnValue.makeFinal(scope));
    }

    @Override
    public String toString() {
        return "ReturnInstruction{" +
                "returnValue=" + returnValue +
                '}';
    }
}
