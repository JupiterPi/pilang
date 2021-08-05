package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.script.parser.Token;

public interface Item {
    Token getToken();
    boolean hasPrecedence();
}
