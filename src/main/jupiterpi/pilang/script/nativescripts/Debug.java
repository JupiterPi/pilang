package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.runtime.ReferenceRegistry;
import jupiterpi.pilang.values.Value;

import java.util.List;

public class Debug extends NativeScript {
    public Debug(ReferenceRegistry registry) {
        super("debug", registry);

        registerDebugFunction("int");
        registerDebugFunction("float");
        registerDebugFunction("bool");
        registerDebugFunction("char");

        addFunction("print", "void", new NativeFunction(this, "char[]") {
            @Override
            protected Value run(List<Value> parameters) {
                String str = "";
                for (Value character : parameters.get(0).getArray(this)) {
                    str += character.getChar(this);
                }
                System.out.printf("[DEBUG] %s%n", str);
                return null;
            }
        });
    }

    private void registerDebugFunction(String type) {
        String name = "print_" + type;
        addFunction(name, "void", new NativeFunction(this, type) {
            @Override
            protected Value run(List<Value> parameters) {
                String str = parameters.get(0).get(this);
                System.out.printf("[DEBUG] %s%n", str);
                return null;
            }
        });
    }
}
