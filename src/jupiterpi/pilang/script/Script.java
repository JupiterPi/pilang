package jupiterpi.pilang.script;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.lexer.Lexer;
import jupiterpi.pilang.script.parser.Parser;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.TextFile;

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

    public static Script readFromFile(Path file) {
        String filename = file.getFileName();
        if (filename.endsWith(".pi")) filename = filename.substring(0, filename.length() - ".pi".length());
        String content = new TextFile(file).getFileForOutput();
        return new Script(filename, content);
    }

    /* generate instructions */

    private void generateInstructions(String content) {
        List<TokenSequence> lines = applyLexer(content);
        instructions = applyParser(lines);
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

    /* execute */

    private List<Instruction> instructions;

    private List<Script> availableScripts;

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
            for (Script script : availableScripts) {
                if (script.getName().equals(parts[0])) {
                    return script.getVariable(parts[1]);
                }
            }
        }
        return super.getVariable(name);
    }
}
