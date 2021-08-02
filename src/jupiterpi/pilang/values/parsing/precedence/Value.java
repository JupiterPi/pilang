package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.script.lexer.Token;

public class Value implements Item {
    private Token token;
    private boolean hasPrecedence;

    public Value(Token token) {
        this.token = token;
        this.hasPrecedence = false;
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public boolean hasPrecedence() {
        return hasPrecedence;
    }

    public void setPrecedence(boolean hasPrecedence) {
        this.hasPrecedence = hasPrecedence;
    }
}
