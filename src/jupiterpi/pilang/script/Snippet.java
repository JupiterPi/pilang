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

public class Snippet {
    public static void main(String[] args) {
        Snippet snippet = Snippet.readFromFile(Path.getRunningDirectory().subdir("scripts").file("test1.pi"));
        snippet.execute();
    }

    public static Snippet readFromFile(Path file) {
        String content = new TextFile(file).getFileForOutput();
        return new Snippet(content);
    }

    private List<Instruction> instructions;

    /* generate instructions */

    public Snippet(String content) {
        List<TokenSequence> lines = applyLexer(content);
        instructions = applyParser(lines);

        System.out.println("parsed instructions: ");
        for (Instruction instruction : instructions) {
            System.out.println(instruction);
        }
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

    private Scope scope;

    public void execute() {
        scope = new Scope();
        scope.execute(instructions);

        System.out.println("variables: ");
        for (Variable variable : scope.getVariables()) {
            System.out.println(variable);
        }
    }
}
