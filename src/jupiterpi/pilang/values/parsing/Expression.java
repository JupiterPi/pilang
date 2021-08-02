package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.lexer.Lexer;
import jupiterpi.pilang.script.lexer.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Literal;
import jupiterpi.pilang.values.Operation;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.precedence.ExpressionPrecedencer;

import static jupiterpi.pilang.script.lexer.Token.Type.*;

public class Expression extends Value {
    private final Operation operation;

    public Expression(String expr) {
        Lexer lexer = new Lexer(expr);
        TokenSequence tokens = lexer.getTokens();

        ExpressionPrecedencer precedencer = new ExpressionPrecedencer(tokens);
        tokens = precedencer.getTokens();

        operation = parseTokens(tokens);
    }

    @Override
    public String get() {
        return operation.get();
    }

    @Override
    public DataType getType() {
        return operation.getType();
    }

    public Operation getOperation() {
        return operation;
    }

    /* parser */

    // values buffer
    private Value a = null;
    private String operator = null;
    private Value b = null;

    private Operation parseTokens(TokenSequence tokens) {
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
