package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.lexer.Token;

import java.util.ArrayList;
import java.util.Collection;

public class TokenSequence extends ArrayList<Token> {
    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) { super(c); }

    @Override
    public boolean contains(Object o) {
        Token t = (Token) o;
        Token.Type type = t.getType();
        String content = t.getContent();
        for (Token token : this) {
            if (token.getType() == type) {
                if (content != null) {
                    if (token.getContent().equals(content)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
