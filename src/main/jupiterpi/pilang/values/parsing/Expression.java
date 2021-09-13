package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.other.Literal;
import jupiterpi.pilang.values.other.Operation;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.precedence.ExpressionPrecedencer;
import jupiterpi.pilang.values.parsing.signs.OperatorSign;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.ValueSign;

import java.util.List;

public class Expression extends Value {
    private Value value;

    // constructor interfaces

    public Expression(String expr) {
        createFromString(expr);
    }

    public Expression(TokenSequence tokens) {
        createFromTokenSequence(tokens);
    }

    public Expression(List<Sign> signs) {
        createFromItems(signs);
    }

    // constructor methods

    private void createFromString(String expr) {
        Lexer lexer = new Lexer(expr);
        TokenSequence tokens = lexer.getTokens();
        createFromTokenSequence(tokens);
    }

    private void createFromTokenSequence(TokenSequence tokens) {
        ExpressionParser parser = new ExpressionParser(tokens);
        List<Sign> signs = parser.getItems();

        ExpressionPrecedencer precedencer = new ExpressionPrecedencer(signs);
        signs = precedencer.getItems();

        createFromItems(signs);
    }

    private void createFromItems(List<Sign> signs) {
        value = parseItems(signs);
    }

    // Value getters

    @Override
    public String get(Scope scope) {
        return value.get(scope);
    }

    @Override
    public DataType getType(Scope scope) {
        return value.getType(scope);
    }

    /* parser */

    // values buffer
    private Value a = null;
    private String operator = null;
    private Value b = null;

    private Value parseItems(List<Sign> signs) {
        for (Sign sign : signs) {
            if (sign instanceof ValueSign) {
                Value value = ((ValueSign) sign).getValue();
                if (a == null) {
                    a = value;
                } else {
                    if (b == null) {
                        b = value;
                    } else {
                        new Exception("no space for value: " + value).printStackTrace();
                    }
                }
            } else {
                if (sign instanceof OperatorSign) {
                    if (operator == null) {
                        operator = ((OperatorSign) sign).getOperator();
                        if (a == null && (operator.equals("+") || operator.equals("-"))) {
                            a = new Literal("0");
                        }
                    } else {
                        new Exception("no space for operator: " + ((OperatorSign) sign).getOperator()).printStackTrace();
                    }
                } else new Exception("unknown Sign type").printStackTrace();
            }

            if (a != null && operator != null && b != null) {
                a = new Operation(a, operator, b);
                operator = null;
                b = null;
            }
        }
        return a;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "" + value +
                '}';
    }
}
