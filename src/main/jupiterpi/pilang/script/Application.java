package jupiterpi.pilang.script;

import jupiterpi.pilang.script.nativescripts.NativeScript;
import jupiterpi.pilang.script.runtime.ReferenceRegistry;
import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.TextFile;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private List<Script> scripts = new ArrayList<>();
    private ReferenceRegistry registry = new ReferenceRegistry();

    public Application(Path dir, String manifestFile) {
        TextFile manifest = new TextFile(dir.copy().file(manifestFile));
        for (String line : manifest.getFile()) {
            if (line.isEmpty()) continue;

            Path file = dir.copy().file(line);
            String filename = file.getFileName();
            if (filename.endsWith(".pi")) filename = filename.substring(0, filename.length() - ".pi".length());
            try {
                String content = new TextFile(file, false).getFileForOutput();
                scripts.add(new Script(filename, content, registry));
            } catch (TextFile.DoesNotExistException ignored) {
                scripts.add(NativeScript.getNativeScript(line, registry));
            }
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
