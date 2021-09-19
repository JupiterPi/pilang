package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.operations.Operator;
import jupiterpi.pilang.values.other.Literal;
import jupiterpi.pilang.values.operations.Operation;
import jupiterpi.pilang.values.parsing.precedence.ExpressionPrecedencer;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.SignSequence;

public class Expression extends Value {
    private Value value;

    // constructor interfaces

    public Expression(String expr) {
        createFromString(expr);
    }

    public Expression(TokenSequence tokens) {
        createFromTokenSequence(tokens);
    }

    public Expression(SignSequence signs) {
        createFromSigns(signs);
    }

    // constructor methods

    private void createFromString(String expr) {
        Lexer lexer = new Lexer(expr);
        TokenSequence tokens = lexer.getTokens();
        createFromTokenSequence(tokens);
    }

    private void createFromTokenSequence(TokenSequence tokens) {
        ExpressionParser parser = new ExpressionParser(tokens);
        SignSequence signs = parser.getSigns();

        ExpressionPrecedencer precedencer = new ExpressionPrecedencer(signs);
        signs = precedencer.getSigns();

        createFromSigns(signs);
    }

    private void createFromSigns(SignSequence signs) {
        value = parseSigns(signs);
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
    private Operator operator = null;
    private Value b = null;

    private Value parseSigns(SignSequence signs) {
        for (Sign sign : signs) {
            if (sign instanceof Value) {
                Value value = (Value) sign;
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
                if (sign instanceof Operator) {
                    if (operator == null) {
                        operator = (Operator) sign;
                        if (a == null && (operator.getOperator().equals("+") || operator.getOperator().equals("-"))) {
                            a = new Literal("0");
                        }
                    } else {
                        new Exception("no space for operator: " + ((Operator)sign).getOperator()).printStackTrace();
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

    public static void main(String[] args) {
        Lexer lexer = new Lexer("1 != 2 && 3 != 3");
        TokenSequence tokens = lexer.getTokens();

        ExpressionParser parser = new ExpressionParser(tokens);
        SignSequence signs = parser.getSigns();

        System.out.println(signs);

        ExpressionPrecedencer precedencer = new ExpressionPrecedencer(signs);
        signs = precedencer.getSigns();

        System.out.println(signs);
    }
}
