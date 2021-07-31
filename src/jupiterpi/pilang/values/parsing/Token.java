package jupiterpi.pilang.values.parsing;

public class Token {
    public enum Type {
        LITERAL, OPERATOR, EXPRESSION
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
}
