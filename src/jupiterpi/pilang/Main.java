package jupiterpi.pilang;

import jupiterpi.pilang.script.Application;
import jupiterpi.tools.files.Path;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Path getApplication(String name) {
        return Path.getRunningDirectory().subdir("scripts").subdir(name);
    }

    public static void main(String[] args) {
        List<Path> files = new ArrayList<>();
        files.add(getApplication("test1").file("test1a.pi"));
        files.add(getApplication("test1").file("test1b.pi"));
        //Application app = Application.fromDirectory(getApplication("test1"));
        Application app = new Application(files);
        app.run();
    }
}
