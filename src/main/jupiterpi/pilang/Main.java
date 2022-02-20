package jupiterpi.pilang;

import jupiterpi.pilang.script.Application;
import jupiterpi.tools.files.Path;

public class Main {
    public static boolean displayDebugPrefix = false;
    public static boolean printVariables = false;

    public static void main(String[] args) {
        Path rootDir = Path.getRunningDirectory();
        String manifestFileName = "test4.pm";

        if (args.length > 0) {
            if (args[0].equals("--debug")) {
                rootDir = Path.getRunningDirectory().subdir("scripts");
                // and leave manifest file name as is
                displayDebugPrefix = true;
                printVariables = true;
            } else {
                manifestFileName = args[0];
                for (int i = 1; i < args.length; i++) {
                    switch (args[i]) {
                        case "--debug-prefix": displayDebugPrefix = true; break;
                        case "--variables": printVariables = true; break;
                        default:
                            System.out.println("Invalid flag: " + args[i]);
                            System.exit(-1);
                    }
                }
            }
        } else {
            System.out.println("You have to at least specify the script!");
            System.exit(-1);
        }

        Application app = new Application(rootDir, manifestFileName);
        app.run();
    }
}
