package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void allDataTypes() {
        assertEquals("[{type=LITERAL, content='5'}, {type=LITERAL, content='1.0'}, {type=LITERAL, content='true'}]", generatedTokens("5 1.0 true"));
    }

    @Test
    void withOperators() {
        assertEquals("[{type=LITERAL, content='5'}, {type=OPERATOR, content='+'}, {type=LITERAL, content='3'}, {type=OPERATOR, content='+'}, {type=LITERAL, content='1.0'}, {type=OPERATOR, content='+'}, {type=LITERAL, content='true'}]", generatedTokens("5+3+ 1.0 +true"));
    }

    private String generatedTokens(String str) {
        Lexer lexer = new Lexer(str);
        TokenSequence tokens = lexer.getTokens();
        return tokens.toString();
    }
}
