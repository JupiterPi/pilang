package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.Script;
import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.runtime.ReferenceRegistry;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.List;

public class NativeScript extends Script {
    public NativeScript(String name, ReferenceRegistry registry) {
        super(name, registry);
    }

    public static Script getNativeScript(String name, ReferenceRegistry registry) {
        switch (name) {
            case "debug": return new Debug(registry);
        }
        return null;
    }

    @Override
    public void run(List<Script> availableScripts) {}

    /* util for NativeScripts */

    protected void addVariable(String name, Value value) {
        Variable variable = new Variable(this, name, value);
        addVariable(variable);
    }

    protected void addFunction(String name, String returnType, NativeFunction function) {
        DataType returnTypeType = DataType.fromTokenSequence(new Lexer(returnType).getTokens());
        final DataType type = returnTypeType.sp_asFunction();
        Variable variable = new Variable(this, name, new Value() {
            @Override
            public DataType getType(Scope scope) {
                return type;
            }

            @Override
            public String get(Scope scope) {
                return "{" + function.getReference() + "}";
            }
        });
        addVariable(variable);
    }
}
