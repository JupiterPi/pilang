package jupiterpi.pilang;

import jupiterpi.pilang.script.Application;
import jupiterpi.tools.files.Path;

public class Main {
    public static void main(String[] args) {
        Application app = Application.newFromManifestFile(Path.getRunningDirectory().subdir("scripts"), "test1.pm");
        app.run();
    }
}
