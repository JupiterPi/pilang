package jupiterpi.pilang.script;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.lexer.Lexer;
import jupiterpi.pilang.script.parser.Parser;
import jupiterpi.pilang.script.parser.TokenSequence;

import java.util.ArrayList;
import java.util.List;

public class Snippet {
    public static void main(String[] args) {
        Snippet snippet = new Snippet("int a = 5 + 3");
    }

    private List<Instruction> instructions;

    public Snippet(String content) {
        List<TokenSequence> lines = applyLexer(content);
        instructions = applyParser(lines);
        for (Instruction instruction : instructions) {
            System.out.println(instruction);
        }
    }

    /* pipeline */

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
}
