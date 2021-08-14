package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

public class Debug extends NativeScript {
    public Debug() {
        super("debug");

        addVariable(makeDebugVariable("int"));
        addVariable(makeDebugVariable("float"));
        addVariable(makeDebugVariable("bool"));
        addVariable(makeDebugVariable("char"));
        addVariable(makeDebugVariable("str"));
    }

    private Variable makeDebugVariable(String type) {
        String name = "out_" + type;
        Value value;
        switch (type) {
            case "int": value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.INT);
                }

                @Override
                public String get(Scope scope) {
                    return "0";
                }
            }; break;
            case "float": value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.FLOAT);
                }

                @Override
                public String get(Scope scope) {
                    return "0.0";
                }
            }; break;
            case "bool": value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.BOOL);
                }

                @Override
                public String get(Scope scope) {
                    return "false";
                }
            }; break;
            case "char": value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.CHAR);
                }

                @Override
                public String get(Scope scope) {
                    return "'-'";
                }
            }; break;
            case "str": value = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.CHAR).sp_asArray();
                }

                @Override
                public String get(Scope scope) {
                    return "['-']";
                }
            }; break;
            default: return null;
        }

        return new Variable(this, name, value) {
            @Override
            public void set(Scope scope, Value value) {
                if (value.getType(scope).equals(new DataType(DataType.BaseType.CHAR).sp_asArray())) {
                    String str = "";
                    for (Value v : value.getArray(scope)) {
                        str += v.getChar(scope);
                    }
                    System.out.printf("[DEBUG] \"%s\"%n", str);
                }
                System.out.println("[DEBUG] " + value.getType(scope) + " " + value.get(scope));
            }
        };
    }
}
