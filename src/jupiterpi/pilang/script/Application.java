package jupiterpi.pilang.script;

import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.TextFile;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private List<Script> scripts = new ArrayList<>();

    public Application(List<Path> files) {
        for (Path file : files) {
            scripts.add(Script.newFromFile(file));
        }
    }

    public static Application newFromManifestFile(Path dir, String manifestFile) {
        TextFile file = new TextFile(dir.copy().file(manifestFile));
        List<Path> files = new ArrayList<>();
        for (String line : file.getFile()) {
            if (line.isEmpty()) continue;
            files.add(dir.copy().file(line));
        }
        return new Application(files);
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
