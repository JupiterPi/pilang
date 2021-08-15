package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.Script;
import jupiterpi.pilang.script.runtime.ReferenceRegistry;

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


}
