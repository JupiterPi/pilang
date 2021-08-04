package jupiterpi.pilang.script.lexer;

import jupiterpi.pilang.script.parser.TokenSequence;

import static jupiterpi.pilang.script.lexer.Token.Type.EXPRESSION;

public class Token {
    public enum Type {
        LITERAL, OPERATOR, EXPRESSION,
        TYPE, ASSIGN, IDENTIFIER,
        NOTICE, INTEGRATE
    }

    private Type type;
    private String content;

    public Token(Type type) {
        this.type = type;
        this.content = null;
    }

    public Token(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }

    public String backToString() {
        return getContent();
    }

    @Override
    public boolean equals(Object obj) {
        Token t = (Token) obj;
        Token.Type type = t.getType();
        String content = t.getContent();
        if (getType() == type) {
            if (content != null) {
                if (getContent().equals(content)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /* other */

    public static Token expressionFromTokens(TokenSequence tokens) {
        String expr = "";
        for (Token token : tokens) {
            expr += token.getContent();
        }
        return new Token(EXPRESSION, expr);
    }
}
