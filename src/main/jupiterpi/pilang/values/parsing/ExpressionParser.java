package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.TokenSequence;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private List<Item> items;

    public ExpressionParser(TokenSequence tokens) {
        items = parseTokens(tokens);
    }

    public List<Item> getItems() {
        return items;
    }

    /* parser */

    private List<Item> parseTokens(TokenSequence tokens) {
        return new ArrayList<>();
    }
}
