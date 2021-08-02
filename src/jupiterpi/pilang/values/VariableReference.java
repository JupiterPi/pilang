package jupiterpi.pilang.values;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;

public class VariableReference extends Value {
    private String variableName;

    public VariableReference(String variableName) {
        this.variableName = variableName;
    }

    private Variable getVariable(Scope scope) {
        for (Variable variable : scope.getVariables()) {
            if (variable.getName().equals(variableName)) return variable;
        }
        new Exception("variable not found: " + variableName).printStackTrace();
        return null;
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
