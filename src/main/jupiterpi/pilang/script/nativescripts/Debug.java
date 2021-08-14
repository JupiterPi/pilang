package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public class Debug extends NativeScript {
    public Debug() {
        super("debug");

        addVariable(makeDebugVariable("out_int", DataType.BaseType.INT));
        addVariable(makeDebugVariable("out_float", DataType.BaseType.FLOAT));
        addVariable(makeDebugVariable("out_bool", DataType.BaseType.BOOL));
        addVariable(makeDebugVariable("out_char", DataType.BaseType.CHAR));
    }

    private Variable makeDebugVariable(String name, DataType.BaseType type) {
        Value value;
        switch (type) {
            case INT: value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.INT);
                }

                @Override
                public String get(Scope scope) {
                    return "0";
                }
            }; break;
            case FLOAT: value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.FLOAT);
                }

                @Override
                public String get(Scope scope) {
                    return "0.0";
                }
            }; break;
            case BOOL: value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.BOOL);
                }

                @Override
                public String get(Scope scope) {
                    return "false";
                }
            }; break;
            case CHAR: value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.CHAR);
                }

                @Override
                public String get(Scope scope) {
                    return "'-'";
                }
            }; break;
            default: return null;
        }

        return new Variable(this, name, value) {
            @Override
            public void set(Scope scope, Value value) {
                System.out.println("[DEBUG] " + value.getType(scope) + " " + value.get(scope));
            }
        };
    }
}
