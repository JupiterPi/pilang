package jupiterpi.pilang.script.parser.tokens;

import jupiterpi.tools.util.AppendingList;

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
        if (!buffer.isEmpty()) subsequences.add(buffer);
        return subsequences;
    }

    public int firstIndexOf(Token of) {
        for (int i = 0; i < this.size(); i++) {
            Token token = this.get(i);
            if (token.equals(of)) return i;
        }
        return -1;
    }

    public TokenSequence subsequence(int fromIndex, int toIndex) {
        TokenSequence tokens = new TokenSequence();
        for (int i = fromIndex; i < toIndex; i++) {
            tokens.add(this.get(i));
        }
        return tokens;
    }

    public TokenSequence subsequence(int fromIndex) {
        return subsequence(fromIndex, this.size());
    }

    public String backToString() {
        AppendingList str = new AppendingList();
        for (Token token : this) {
            str.add(token.backToString());
        }
        return str.render(" ");
    }
}
