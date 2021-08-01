package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.lexer.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenSequence extends ArrayList<Token> {
    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) { super(c); }

    @Override
    public boolean contains(Object o) {
        Token t = (Token) o;
        for (Token token : this) {
            if (token.equals(t)) return true;
        }
        return false;
    }

    public List<TokenSequence> split(Token at) {
        List<TokenSequence> subsequences = new ArrayList<>();
        TokenSequence buffer = new TokenSequence();
        for (Token token : this) {
            if (token.equals(at)) {
                subsequences.add(buffer);
                buffer = new TokenSequence();
            } else {
                buffer.add(token);
            }
        }
        subsequences.add(buffer);
        return subsequences;
    }

    public String backToString() {
        String str = "";
        for (Token token : this) {
            str += token.backToString();
        }
        return str;
    }
}
