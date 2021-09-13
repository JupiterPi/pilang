package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.OperatorSign;
import jupiterpi.pilang.values.parsing.signs.ValueSign;

import java.util.Arrays;
import java.util.List;

public class PrecedenceItem {
    private final List<String> precedenceOperators = Arrays.asList("*", "/");

    private Sign sign;
    private boolean precedence;

    public PrecedenceItem(Sign sign) {
        this.sign = sign;
        if (sign instanceof ValueSign) {
            this.precedence = false;
        } else {
            OperatorSign operatorItem = (OperatorSign) sign;
            this.precedence = listContains(precedenceOperators, operatorItem.getOperator());
        }
    }
    private boolean listContains(List<String> list, String c) {
        for (String lc : list) {
            if (lc.equals(c)) return true;
        }
        return false;
    }

    public Sign getItem() {
        return sign;
    }

    public boolean hasPrecedence() {
        return precedence;
    }

    public void setPrecedence(boolean precedence) {
        this.precedence = precedence;
    }
}
