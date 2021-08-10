package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.runtime.Scope;

public abstract class Instruction {
    public abstract void execute(Scope scope);

    @Override
    public String toString() {
        return "Instruction{undefined}";
    }
}
