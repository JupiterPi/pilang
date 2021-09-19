package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.operations.Operator;
import jupiterpi.pilang.values.parsing.signs.Sign;

public class PrecedenceSign {
    private Sign sign;
    private int precedenceLevel;

    public PrecedenceSign(Sign sign) {
        this.sign = sign;
        if (sign instanceof Value) {
            this.precedenceLevel = PrecedenceLevel.NONE;
        } else {
            Operator operatorSign = (Operator) sign;
            this.precedenceLevel = operatorSign.getPrecedenceLevel();
        }
    }

    public Sign getSign() {
        return sign;
    }

    public int getPrecedenceLevel() {
        return precedenceLevel;
    }

    public void setPrecedenceLevel(int precedenceLevel) {
        this.precedenceLevel = precedenceLevel;
    }

    @Override
    public String toString() {
        return "{" + precedenceLevel + "} " + sign;
    }
}
