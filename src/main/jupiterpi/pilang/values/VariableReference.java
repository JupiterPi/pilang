package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;

public class VariableReference extends Value {
    private String variableName;

    public VariableReference(String variableName) {
        this.variableName = variableName;
    }

    private Variable getVariable(Scope scope) {
        Variable variable = scope.getVariable(variableName);
        if (variable == null) new Exception("variable not found: " + variableName).printStackTrace();
        return variable;
    }

    @Override
    public DataType getType(Scope scope) {
        return getVariable(scope).getType(scope);
    }

    @Override
    public String get(Scope scope) {
        return getVariable(scope).get(scope);
    }

    @Override
    public String toString() {
        return "VariableReference{" +
                "variableName='" + variableName + '\'' +
                '}';
    }
}
