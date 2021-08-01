package jupiterpi.pilang.script;

import jupiterpi.pilang.script.lexer.Lexer;
import jupiterpi.pilang.script.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class Snippet {
    public static void main(String[] args) {
        Snippet snippet = new Snippet("int a = 5 + 3");
        for (List<Token> line : snippet.tokens) {
            System.out.println(line);
        }
    }

    private List<List<Token>> tokens = new ArrayList<>();

    public Snippet(String content) {
        generateTokens(content);
    }

    /* lexer */

    private void generateTokens(String content) {
        for (String line : content.split(";")) {
            Lexer lexer = new Lexer(line);
            List<Token> tokens = lexer.getTokens();
            if (tokens.size() == 0) continue;
            this.tokens.add(tokens);
        }
    }
}
