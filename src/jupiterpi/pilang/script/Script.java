package jupiterpi.pilang.script;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Parser;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.TextFile;
import jupiterpi.tools.util.AppendingList;

import java.util.ArrayList;
import java.util.List;

public class Script extends Scope {
    private String name;

    public String getName() {
        return name;
    }

    /* constructor */

    public Script(String name, String content) {
        this.name = name;
        generateInstructions(content);
    }

    public static Script newFromFile(Path file) {
        String filename = file.getFileName();
        if (filename.endsWith(".pi")) filename = filename.substring(0, filename.length() - ".pi".length());
        String content = new TextFile(file).getFileForOutput();
        return new Script(filename, content);
    }

    //region generate instructions

    private List<Instruction> instructions;

    private void generateInstructions(String content) {
        content = removeComments(content);
        List<TokenSequence> lines = applyLexer(content);
        instructions = applyParser(lines);
    }

    private String removeComments(String oldContent) {
        AppendingList content = new AppendingList();
        for (String line : oldContent.split("\n")) {
            int index = line.indexOf("//");
            if (index >= 0) {
                line = line.substring(0, index);
            }
            content.add(line);
        }
        return content.render("\n");
    }

    private List<TokenSequence> applyLexer(String content) {
        List<TokenSequence> lines = new ArrayList<>();
        for (String line : content.split(";")) {
            Lexer lexer = new Lexer(line);
            TokenSequence tokens = new TokenSequence(lexer.getTokens());
            if (tokens.size() == 0) continue;
            lines.add(tokens);
        }
        return lines;
    }

    private List<Instruction> applyParser(List<TokenSequence> lines) {
        Parser parser = new Parser(lines);
        return parser.getInstructions();
    }

    //endregion

    /* execute */

    private List<Script> availableScripts;
    private List<Script> noticedScripts = new ArrayList<>();
    private List<Script> integratedScripts = new ArrayList<>();

    public void run(List<Script> availableScripts) {
        this.availableScripts = new ArrayList<>(availableScripts);
        super.execute(instructions);

        System.out.println("variables in script " + name + ": ");
        for (Variable variable : getVariables()) {
            System.out.println(variable);
        }
    }

    @Override
    public Variable getVariable(String name) {
        if (name.contains(".")) {
            String[] parts = name.split("\\.");
            if (parts[0].equals("self")) {
                return getLocalVariable(parts[1]);
            }
            for (Script script : noticedScripts) {
                if (script.getName().equals(parts[0])) {
                    return script.getVariable(parts[1]);
                }
            }
        }
        Variable variable = getLocalVariable(name);
        if (variable != null) {
            return variable;
        } else {
            for (Script integratedScript : integratedScripts) {
                Variable v = integratedScript.getLocalVariable(name);
                if (v != null) return v;
            }
        }

        new Exception("couldn't find variable " + name).printStackTrace();
        return null;
    }
    private Variable getLocalVariable(String name) {
        return super.getVariable(name);
    }

    public void importScript(boolean integrate, String scriptName) {
        for (Script availableScript : availableScripts) {
            if (availableScript.getName().equals(scriptName)) {
                if (integrate) addScript(integratedScripts, "integrated", availableScript);
                addScript(noticedScripts, "noticed", availableScript);
                return;
            }
        }
        new Exception("tried to import script " + scriptName + ", but it doesn't exist!").printStackTrace();
    }
    private void addScript(List<Script> list, String listName, Script script) {
        for (Script s : list) {
            if (s.getName().equals(script.getName())) {
                new Exception("tried to add script " + script.getName() + " to " + listName + " scripts, but it's already there!").printStackTrace(); return;
            }
        }
        list.add(script);
    }
}
