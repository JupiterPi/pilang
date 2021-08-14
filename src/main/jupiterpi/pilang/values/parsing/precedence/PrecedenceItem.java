package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.parsing.Item;
import jupiterpi.pilang.values.parsing.OperatorItem;
import jupiterpi.pilang.values.parsing.ValueItem;

import java.util.Arrays;
import java.util.List;

public class PrecedenceItem {
    private final List<String> precedenceOperators = Arrays.asList("*", "/");

    private Item item;
    private boolean precedence;

    public PrecedenceItem(Item item) {
        this.item = item;
        if (item instanceof ValueItem) {
            this.precedence = false;
        } else {
            OperatorItem operatorItem = (OperatorItem) item;
            this.precedence = listContains(precedenceOperators, operatorItem.getOperator());
        }
    }
    private boolean listContains(List<String> list, String c) {
        for (String lc : list) {
            if (lc.equals(c)) return true;
        }
        return false;
    }

    public Item getItem() {
        return item;
    }

    public boolean hasPrecedence() {
        return precedence;
    }

    public void setPrecedence(boolean precedence) {
        this.precedence = precedence;
    }
}
