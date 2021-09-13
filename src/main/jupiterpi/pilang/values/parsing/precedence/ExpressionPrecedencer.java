package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.OperatorSign;
import jupiterpi.pilang.values.parsing.signs.SignSequence;
import jupiterpi.pilang.values.parsing.signs.ValueSign;

import java.util.ArrayList;
import java.util.List;

public class ExpressionPrecedencer {
    private SignSequence signs;

    public ExpressionPrecedencer(SignSequence signs) {
        generateSignsList(signs);
        determinePrecedence();
        if (isOnlyPrecedence()) {
            this.signs = signs;
        } else {
            this.signs = generateNewSigns();
        }
    }

    public SignSequence getSigns() {
        return signs;
    }

    /* precedencer */

    private List<PrecedenceSign> precedenceSigns = new ArrayList<>();

    private void generateSignsList(SignSequence signs) {
        for (Sign sign : signs) {
            precedenceSigns.add(new PrecedenceSign(sign));
        }
    }

    private void determinePrecedence() {
        for (int i = 0; i < precedenceSigns.size(); i++) {
            PrecedenceSign precedenceSign = precedenceSigns.get(i);
            if (!(precedenceSign.getSign() instanceof OperatorSign)) continue;

            if (precedenceSign.hasPrecedence()) {
                applyPrecedence(i-1);
                applyPrecedence(i+1);
            }
        }
    }

    private void applyPrecedence(int i) {
        PrecedenceSign precedenceSign = precedenceSigns.get(i);
        if (precedenceSign.getSign() instanceof ValueSign) {
            precedenceSign.setPrecedence(true);
        } else {
            new Exception("invalid operator-value order").printStackTrace();
        }
    }

    private boolean isOnlyPrecedence() {
        for (PrecedenceSign sign : precedenceSigns) {
            if (!sign.hasPrecedence()) return false;
        }
        return true;
    }

    private SignSequence generateNewSigns() {
        SignSequence signs = new SignSequence();

        boolean insidePrecedence = false;
        SignSequence buffer = new SignSequence();
        for (PrecedenceSign sign : precedenceSigns) {
            if (insidePrecedence) {
                if (sign.hasPrecedence()) {
                    buffer.add(sign.getSign());
                } else {
                    signs.add(new ValueSign(new Expression(buffer)));
                    buffer = new SignSequence();
                    signs.add(sign.getSign());
                    insidePrecedence = false;
                }
            } else {
                if (sign.hasPrecedence()) {
                    buffer.add(sign.getSign());
                    insidePrecedence = true;
                } else {
                    signs.add(sign.getSign());
                }
            }
        }
        if (buffer.size() > 0) {
            signs.add(new ValueSign(new Expression(buffer)));
        }

        return signs;
    }
}
