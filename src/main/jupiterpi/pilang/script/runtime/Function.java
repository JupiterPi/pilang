package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.functions.VariableHead;

import java.util.List;

public class Function extends Scope {
    private String reference;

    private List<VariableHead> parameters;
    private DataType returnType;
    private List<Instruction> instructions;

    public Function(Scope parentScope, List<VariableHead> parameters, DataType returnType, List<Instruction> instructions) {
        super(parentScope);
        this.reference = parentScope.getRegistry().registerFunction(this);

        this.parameters = parameters;
        this.returnType = returnType;

        this.instructions = instructions;
    }

    public String getReference() {
        return reference;
    }

    public Value executeFunction(List<Value> parameters, Scope callingScope) {
        if (this.parameters.size() == parameters.size()) {
            for (int i = 0; i < this.parameters.size(); i++) {
                VariableHead definition = this.parameters.get(i);
                Value value = parameters.get(i);
                if (definition.getType().equals(value.getType(callingScope))) {
                    this.addVariable(new Variable(this, definition.getName(), value));
                } else new Exception("mismatching types in parameters").printStackTrace();
            }
        } else new Exception("mismatching parameters").printStackTrace();

        for (Instruction instruction : instructions) {
            instruction.execute(this);
        }

        if (returnValue == null) {
            return new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.VOID);
                }
                @Override
                public String get(Scope scope) {
                    return "";
                }
            };
        } else {
            return returnValue.asFinal(this);
        }
    }

    private Value returnValue = null;

    @Override
    public void returnValue(Value value) {
        returnValue = value;
    }

    @Override
    public String toString() {
        return "Function{" +
                "reference='" + reference + '\'' +
                ", parameters=" + parameters +
                ", instructions=" + instructions +
                '}';
    }
}
