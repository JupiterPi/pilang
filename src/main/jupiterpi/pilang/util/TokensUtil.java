package jupiterpi.pilang.util;

import jupiterpi.pilang.script.parser.tokens.Token;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;

public class TokensUtil {
    public static TokenSequence tokenTypeSequence(Token.Type... types) {
        TokenSequence tokenSequence = new TokenSequence();
        for (Token.Type type : types) {
            tokenSequence.add(new Token(type));
        }
        return tokenSequence;
    }
}
