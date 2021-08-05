package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;

import java.util.ArrayList;
import java.util.List;

import static jupiterpi.pilang.script.parser.Token.Type.OPERATOR;

public class ExpressionPrecedencer {
    private TokenSequence tokens;

    public ExpressionPrecedencer(TokenSequence tokens) {
        generateItemsList(tokens);
        determinePrecedence();
        if (isOnlyPrecedence()) {
            this.tokens = tokens;
        } else {
            this.tokens = generateNewTokens();
        }
    }

    public TokenSequence getTokens() {
        return tokens;
    }

    /* precedencer */

    private List<Item> items = new ArrayList<>();

    private void generateItemsList(TokenSequence tokens) {
        for (Token token : tokens) {
            if (token.getType() == OPERATOR) {
                items.add(new Operator(token));
            } else {
                items.add(new Value(token));
            }
        }
    }

    private void determinePrecedence() {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (!(item instanceof Operator)) continue;

            if (item.hasPrecedence()) {
                applyPrecedence(i-1);
                applyPrecedence(i+1);
            }
        }
    }

    private void applyPrecedence(int i) {
        if (items.get(i) instanceof Value) {
            ((Value)items.get(i)).setPrecedence(true);
        } else {
            new Exception("invalid operator-value order").printStackTrace();
        }
    }

    private boolean isOnlyPrecedence() {
        for (Item item : items) {
            if (!item.hasPrecedence()) return false;
        }
        return true;
    }

    private TokenSequence generateNewTokens() {
        TokenSequence tokens = new TokenSequence();

        boolean insidePrecedence = false;
        TokenSequence buffer = new TokenSequence();
        for (Item item : items) {
            if (insidePrecedence) {
                if (item.hasPrecedence()) {
                    buffer.add(item.getToken());
                } else {
                    tokens.add(Token.expressionFromTokens(buffer));
                    buffer = new TokenSequence();
                    tokens.add(item.getToken());
                    insidePrecedence = false;
                }
            } else {
                if (item.hasPrecedence()) {
                    buffer.add(item.getToken());
                    insidePrecedence = true;
                } else {
                    tokens.add(item.getToken());
                }
            }
        }
        if (buffer.size() > 0) {
            tokens.add(Token.expressionFromTokens(buffer));
        }

        return tokens;
    }
}
