package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.Script;
import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.runtime.Function;
import jupiterpi.pilang.script.runtime.ReferenceRegistry;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.functions.VariableHead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

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

    /*protected static class FunctionBuilder {
        private String name;
        private List<String> parameters;
        private BiFunction<List<String>, Scope, Value> executable;

        public static FunctionBuilder name(String name) {
            FunctionBuilder builder = new FunctionBuilder();
            builder.name = name;
            return builder;
        }

        public FunctionBuilder parameters(String... parameters) {
            this.parameters = Arrays.asList(parameters);
            return this;
        }



        public FunctionBuilder executable(BiFunction<List<String>, Scope, Value> executable) {
            this.executable = executable;

            List<VariableHead> heads = new ArrayList<>();
            for (String parameter : parameters) {
                heads.add(new VariableHead(new Lexer(parameter).getTokens()));
            }

            return new Function(this, heads, ){

            };
        }
    }*/
}
