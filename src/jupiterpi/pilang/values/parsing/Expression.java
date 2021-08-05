package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.*;
import jupiterpi.pilang.values.parsing.precedence.ExpressionPrecedencer;

import static jupiterpi.pilang.script.parser.Token.Type.*;

public class Expression extends Value {
    private final Value value;

    public Expression(String expr) {
        Lexer lexer = new Lexer(expr);
        TokenSequence tokens = lexer.getTokens();

        ExpressionPrecedencer precedencer = new ExpressionPrecedencer(tokens);
        tokens = precedencer.getTokens();

        value = parseTokens(tokens);
    }

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

    private Value parseTokens(TokenSequence tokens) {
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
            } else if (t.getType() == IDENTIFIER) {
                appendValue(new VariableReference(t.getContent()));
            }
            flushOperation();
        }
        return a;
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
                "" + value +
                '}';
    }
}
