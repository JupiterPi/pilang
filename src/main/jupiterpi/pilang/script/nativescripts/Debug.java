package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.Main;
import jupiterpi.pilang.script.runtime.ReferenceRegistry;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                print(str);
                return null;
            }
        });

        addFunction("println", "void", new NativeFunction(this) {
            @Override
            protected Value run(List<Value> parameters) {
                print("");
                return null;
            }
        });

        addFunction("readln", "char[]", new NativeFunction(this) {
            @Override
            protected Value run(List<Value> parameters) {
                System.out.print("[DEBUG] > ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    return FinalValue.fromString(reader.readLine());
                } catch (IOException e) {
                    return FinalValue.fromString("");
                }
            }
        });
    }

    private void registerDebugFunction(String type) {
        String name = "print_" + type;
        addFunction(name, "void", new NativeFunction(this, type) {
            @Override
            protected Value run(List<Value> parameters) {
                String str = parameters.get(0).get(this);
                print(str);
                return null;
            }
        });
    }

    private void print(String text) {
        String str = text;
        if (Main.displayDebugPrefix) str = "[DEBUG] " + text;
        System.out.println(str);
    }
}
