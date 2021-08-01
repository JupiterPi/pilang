package jupiterpi.pilang.script.lexer;

import jupiterpi.pilang.script.parser.TokenSequence;

import static jupiterpi.pilang.script.lexer.Token.Type.EXPRESSION;

public class Token {
    public enum Type {
        LITERAL, OPERATOR, EXPRESSION,
        TYPE, ASSIGN, IDENTIFIER
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

    /* other */

    public static Token expressionFromTokens(TokenSequence tokens) {
        String expr = "";
        for (Token token : tokens) {
            expr += token.getContent();
        }
        return new Token(EXPRESSION, expr);
    }
}
