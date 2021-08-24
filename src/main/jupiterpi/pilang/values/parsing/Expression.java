package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.other.Operation;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.precedence.ExpressionPrecedencer;

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

    public Expression(List<Item> items) {
        createFromItems(items);
    }

    // constructor methods

    private void createFromString(String expr) {
        Lexer lexer = new Lexer(expr);
        TokenSequence tokens = lexer.getTokens();
        createFromTokenSequence(tokens);
    }

    private void createFromTokenSequence(TokenSequence tokens) {
        ExpressionParser parser = new ExpressionParser(tokens);
        List<Item> items = parser.getItems();

        ExpressionPrecedencer precedencer = new ExpressionPrecedencer(items);
        items = precedencer.getItems();

        createFromItems(items);
    }

    private void createFromItems(List<Item> items) {
        value = parseItems(items);
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

    private Value parseItems(List<Item> items) {
        for (Item item : items) {
            if (item instanceof ValueItem) {
                Value value = ((ValueItem) item).getValue();
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
                if (item instanceof OperatorItem) {
                    if (operator == null) {
                        operator = ((OperatorItem) item).getOperator();
                    } else {
                        new Exception("no space for operator: " + ((OperatorItem) item).getOperator()).printStackTrace();
                    }
                } else new Exception("unknown Item type").printStackTrace();
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
