package jupiterpi.pilang;

import jupiterpi.pilang.script.Application;
import jupiterpi.tools.files.Path;

public class Main {
    public static void main(String[] args) {
        Application app = new Application(Path.getRunningDirectory().subdir("scripts"), "test3.pm");
        app.run();
    }
}
