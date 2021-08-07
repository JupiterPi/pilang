package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.Script;

import java.util.List;

public class NativeScript extends Script {
    public NativeScript(String name) {
        super(name);
    }

    public static Script getNativeScript(String name) {
        switch (name) {
            case "debug": return new Debug();
        }
        return null;
    }

    @Override
    public void run(List<Script> availableScripts) {}


}
