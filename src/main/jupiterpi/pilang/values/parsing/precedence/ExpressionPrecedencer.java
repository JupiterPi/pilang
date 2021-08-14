package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.pilang.values.parsing.Item;
import jupiterpi.pilang.values.parsing.OperatorItem;
import jupiterpi.pilang.values.parsing.ValueItem;

import java.util.ArrayList;
import java.util.List;

public class ExpressionPrecedencer {
    private List<Item> items;

    public ExpressionPrecedencer(List<Item> items) {
        generateItemsList(items);
        determinePrecedence();
        if (isOnlyPrecedence()) {
            this.items = items;
        } else {
            this.items = generateNewItems();
        }
    }

    public List<Item> getItems() {
        return items;
    }

    /* precedencer */

    private List<PrecedenceItem> precedenceItems = new ArrayList<>();

    private void generateItemsList(List<Item> items) {
        for (Item item : items) {
            precedenceItems.add(new PrecedenceItem(item));
        }
    }

    private void determinePrecedence() {
        for (int i = 0; i < precedenceItems.size(); i++) {
            PrecedenceItem precedenceItem = precedenceItems.get(i);
            if (!(precedenceItem.getItem() instanceof OperatorItem)) continue;

            if (precedenceItem.hasPrecedence()) {
                applyPrecedence(i-1);
                applyPrecedence(i+1);
            }
        }
    }

    private void applyPrecedence(int i) {
        PrecedenceItem precedenceItem = precedenceItems.get(i);
        if (precedenceItem.getItem() instanceof ValueItem) {
            precedenceItem.setPrecedence(true);
        } else {
            new Exception("invalid operator-value order").printStackTrace();
        }
    }

    private boolean isOnlyPrecedence() {
        for (PrecedenceItem item : precedenceItems) {
            if (!item.hasPrecedence()) return false;
        }
        return true;
    }

    private List<Item> generateNewItems() {
        List<Item> items = new ArrayList<>();

        boolean insidePrecedence = false;
        List<Item> buffer = new ArrayList<>();
        for (PrecedenceItem item : precedenceItems) {
            if (insidePrecedence) {
                if (item.hasPrecedence()) {
                    buffer.add(item.getItem());
                } else {
                    items.add(new ValueItem(new Expression(buffer)));
                    buffer = new ArrayList<>();
                    items.add(item.getItem());
                    insidePrecedence = false;
                }
            } else {
                if (item.hasPrecedence()) {
                    buffer.add(item.getItem());
                    insidePrecedence = true;
                } else {
                    items.add(item.getItem());
                }
            }
        }
        if (buffer.size() > 0) {
            items.add(new ValueItem(new Expression(buffer)));
        }

        return items;
    }
}
