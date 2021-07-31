package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.parsing.Token;

public class Operator implements Item {
    private Token token;
    private boolean hasPrecedence;

    public Operator(Token token) {
        this.token = token;
        String operator = token.getContent();
        this.hasPrecedence = operator.equals("*") || operator.equals("/");
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public boolean hasPrecedence() {
        return hasPrecedence;
    }
}
