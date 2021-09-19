package jupiterpi.pilang.values.parsing.precedence;

import jupiterpi.pilang.values.operations.Operator;
import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.SignSequence;

import java.util.ArrayList;
import java.util.List;

public class ExpressionPrecedencer {
    private SignSequence signs;

    public ExpressionPrecedencer(SignSequence signs) {
        this.signs = applyPrecedence(signs);
    }

    public SignSequence getSigns() {
        return signs;
    }

    /* precedencer */

    private SignSequence applyPrecedence(SignSequence signs) {
        List<PrecedenceSign> precedenceSigns = new ArrayList<>();

        // generate signs list, check operator-value order
        boolean lastIsOperator = !(signs.get(0) instanceof Operator);
        for (Sign sign : signs) {
            boolean isOperator = (sign instanceof Operator);
            if (lastIsOperator == isOperator) new Exception("invalid operator-value order").printStackTrace();
            precedenceSigns.add(new PrecedenceSign(sign));
            lastIsOperator = isOperator;
        }

        // determine precedence
        for (int i = 0; i < precedenceSigns.size(); i++) {
            PrecedenceSign precedenceSign = precedenceSigns.get(i);
            if (!(precedenceSign.getSign() instanceof Operator)) continue;

            int precedenceLevel = precedenceSign.getPrecedenceLevel();
            for (int offset : new int[]{-1, +1}) {
                PrecedenceSign affectedPrecedenceSign = precedenceSigns.get(i + offset);
                if (precedenceLevel > affectedPrecedenceSign.getPrecedenceLevel()) affectedPrecedenceSign.setPrecedenceLevel(precedenceLevel);
            }
        }

        // extract base precedence
        int basePrecedenceLevel = Integer.MAX_VALUE;
        int changesInBasePrecedenceLevel = 0;
        for (PrecedenceSign precedenceSign : precedenceSigns) {
            int precedenceLevel = precedenceSign.getPrecedenceLevel();
            if (precedenceLevel < basePrecedenceLevel) {
                changesInBasePrecedenceLevel++;
                basePrecedenceLevel = precedenceLevel;
            }
        }

        // single precedence
        boolean isSinglePrecedence = true;
        int firstPrecedenceLevel = -1;
        for (PrecedenceSign precedenceSign : precedenceSigns) {
            int currentPrecedenceLevel = precedenceSign.getPrecedenceLevel();
            if (firstPrecedenceLevel == -1) {
                firstPrecedenceLevel = currentPrecedenceLevel;
            } else {
                if (currentPrecedenceLevel != firstPrecedenceLevel) {
                    isSinglePrecedence = false;
                    break;
                }
            }
        }
        if (isSinglePrecedence) return signs;

        // generate new signs
        signs = new SignSequence();
        boolean currentlyPrecedence = false;
        SignSequence buffer = new SignSequence();
        for (PrecedenceSign precedenceSign : precedenceSigns) {
            int currentPrecedenceLevel = precedenceSign.getPrecedenceLevel();
            boolean precedence = currentPrecedenceLevel > basePrecedenceLevel;
            if (precedence) {
                if (!currentlyPrecedence) {
                    currentlyPrecedence = true;
                }
                buffer.add(precedenceSign.getSign());
            } else {
                if (currentlyPrecedence) {
                    signs.add(new Expression(buffer));
                    currentlyPrecedence = false;
                    buffer = new SignSequence();
                }
                signs.add(precedenceSign.getSign());
            }
        }
        if (currentlyPrecedence) {
            signs.add(new Expression(buffer));
        }

        return signs;
    }
}
