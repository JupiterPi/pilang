package jupiterpi.pilang;

import jupiterpi.pilang.script.Snippet;
import jupiterpi.tools.files.Path;

public class Main {
    public static void main(String[] args) {
        Snippet snippet = Snippet.readFromFile(Path.getRunningDirectory().subdir("scripts").file("test1.pi"));
        snippet.execute();
    }
}
