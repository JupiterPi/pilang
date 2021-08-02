package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.script.lexer.Token;

public interface Item {
    Token getToken();
    boolean hasPrecedence();
}
