package jupiterpi.pilang.script;

import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.WrongPathTypeException;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private List<Script> scripts = new ArrayList<>();

    public Application(List<Path> files) {
        for (Path file : files) {
            scripts.add(Script.readFromFile(file));
        }
    }

    @Deprecated
    public static Application fromDirectory(Path dir) {
        try {
            return new Application(dir.subfiles());
        } catch (WrongPathTypeException e) {
            new Exception("must be a directory with script files: " + dir).printStackTrace();
            return null;
        }
    }

    /* execute */

    private List<Script> availableScripts = new ArrayList<>();

    public void run() {
        for (Script script : scripts) {
            script.run(availableScripts);
            availableScripts.add(script);
        }
    }
}
