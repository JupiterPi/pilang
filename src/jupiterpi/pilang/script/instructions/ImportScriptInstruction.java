package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.Script;
import jupiterpi.pilang.script.runtime.Scope;

public class ImportScriptInstruction extends Instruction {
    private boolean integrate;
    private String scriptName;

    public ImportScriptInstruction(boolean integrate, String scriptName) {
        this.integrate = integrate;
        this.scriptName = scriptName;
    }

    @Override
    public void execute(Scope scope) {
        if (scope instanceof Script) {
            Script script = (Script) scope;
            script.importScript(integrate, scriptName);
        } else {
            new Exception("cannot import script to non-script scope!").printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ImportScriptInstruction{" +
                "integrate=" + integrate +
                ", scriptName='" + scriptName + '\'' +
                '}';
    }
}
