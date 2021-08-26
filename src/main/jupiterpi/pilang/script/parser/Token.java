package jupiterpi.pilang.script.parser;

import static jupiterpi.pilang.script.parser.Token.Type.*;

public class Token {
    public enum Type {
        LITERAL, OPERATOR, EXPRESSION, BRACKET_EXPRESSION, BRACES_EXPRESSION,
        TYPE, ASSIGN, IDENTIFIER,
        NOTICE, INTEGRATE,
        COMMA, SEMICOLON,
        RETURN,
        IF, ELSE, WHILE, FOR
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
        String str = getContent();
        if (type == EXPRESSION) str = String.format("(%s)", str);
        if (type == BRACKET_EXPRESSION) str = String.format("[%s]", str);
        if (type == BRACES_EXPRESSION) str = String.format("{%s}", str);
        return str;
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
