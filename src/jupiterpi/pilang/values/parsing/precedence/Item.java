package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.parsing.Token;

public interface Item {
    Token getToken();
    boolean hasPrecedence();
}
