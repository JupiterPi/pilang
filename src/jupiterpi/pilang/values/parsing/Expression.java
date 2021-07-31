package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.values.Literal;
import jupiterpi.pilang.values.Operation;
import jupiterpi.pilang.values.Value;

import java.util.List;

import static jupiterpi.pilang.values.parsing.Token.Type.*;

public class Expression extends Value {
    private final Operation operation;

    public Expression(String expr) {
        List<Token> tokens = new ExpressionLexer(expr).getTokens();
        operation = parseTokens(tokens);
    }

    @Override
    public String get() {
        return operation.get();
    }

    public Operation getOperation() {
        return operation;
    }

    /* parser */

    // values buffer
    private Value a = null;
    private String operator = null;
    private Value b = null;

    private Operation parseTokens(List<Token> tokens) {
        for (Token t : tokens) {
            if (t.getType() == EXPRESSION) {
                appendValue(new Expression(t.getContent()));
            } else if (t.getType() == LITERAL) {
                appendValue(new Literal(t.getContent()));
            } else if (t.getType() == OPERATOR) {
                if (operator == null) {
                    operator = t.getContent();
                } else {
                    new Exception("no space for operator: " + t.getContent()).printStackTrace();
                }
            }
            flushOperation();
        }
        return (Operation) a;
    }

    private void flushOperation() {
        if (a != null && operator != null && b != null) {
            a = new Operation(a, operator, b);
            operator = null;
            b = null;
        }
    }

    private void appendValue(Value value) {
        if (a == null) {
            a = value;
        } else {
            if (b == null) {
                b = value;
            } else {
                new Exception("no space for value: " + value).printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Expression{" +
                "operation=" + operation +
                '}';
    }
}
