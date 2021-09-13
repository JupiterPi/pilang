package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.OperatorSign;
import jupiterpi.pilang.values.parsing.signs.ValueSign;

import java.util.ArrayList;
import java.util.List;

public class ExpressionPrecedencer {
    private List<Sign> signs;

    public ExpressionPrecedencer(List<Sign> signs) {
        generateItemsList(signs);
        determinePrecedence();
        if (isOnlyPrecedence()) {
            this.signs = signs;
        } else {
            this.signs = generateNewItems();
        }
    }

    public List<Sign> getItems() {
        return signs;
    }

    /* precedencer */

    private List<PrecedenceItem> precedenceItems = new ArrayList<>();

    private void generateItemsList(List<Sign> signs) {
        for (Sign sign : signs) {
            precedenceItems.add(new PrecedenceItem(sign));
        }
    }

    private void determinePrecedence() {
        for (int i = 0; i < precedenceItems.size(); i++) {
            PrecedenceItem precedenceItem = precedenceItems.get(i);
            if (!(precedenceItem.getItem() instanceof OperatorSign)) continue;

            if (precedenceItem.hasPrecedence()) {
                applyPrecedence(i-1);
                applyPrecedence(i+1);
            }
        }
    }

    private void applyPrecedence(int i) {
        PrecedenceItem precedenceItem = precedenceItems.get(i);
        if (precedenceItem.getItem() instanceof ValueSign) {
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

    private List<Sign> generateNewItems() {
        List<Sign> signs = new ArrayList<>();

        boolean insidePrecedence = false;
        List<Sign> buffer = new ArrayList<>();
        for (PrecedenceItem item : precedenceItems) {
            if (insidePrecedence) {
                if (item.hasPrecedence()) {
                    buffer.add(item.getItem());
                } else {
                    signs.add(new ValueSign(new Expression(buffer)));
                    buffer = new ArrayList<>();
                    signs.add(item.getItem());
                    insidePrecedence = false;
                }
            } else {
                if (item.hasPrecedence()) {
                    buffer.add(item.getItem());
                    insidePrecedence = true;
                } else {
                    signs.add(item.getItem());
                }
            }
        }
        if (buffer.size() > 0) {
            signs.add(new ValueSign(new Expression(buffer)));
        }

        return signs;
    }
}
