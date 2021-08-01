package jupiterpi.pilang.script;

import jupiterpi.pilang.script.lexer.Lexer;
import jupiterpi.pilang.script.parser.TokenSequence;

import java.util.ArrayList;
import java.util.List;

public class Snippet {
    public static void main(String[] args) {
        Snippet snippet = new Snippet("int a = 5 + 3");
        for (TokenSequence line : snippet.lines) {
            System.out.println(line);
        }
    }

    private List<TokenSequence> lines = new ArrayList<>();

    public Snippet(String content) {
        generateTokens(content);
    }

    /* lexer */

    private void generateTokens(String content) {
        for (String line : content.split(";")) {
            Lexer lexer = new Lexer(line);
            TokenSequence tokens = new TokenSequence(lexer.getTokens());
            if (tokens.size() == 0) continue;
            lines.add(tokens);
        }
    }
}
