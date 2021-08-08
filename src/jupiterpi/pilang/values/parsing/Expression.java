package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.*;
import jupiterpi.pilang.values.parsing.precedence.ExpressionPrecedencer;

public class Expression extends Value {
    private Value value;

    public Expression(String expr) {
        Lexer lexer = new Lexer(expr);
        TokenSequence tokens = lexer.getTokens();
        create(tokens);
    }

    public Expression(TokenSequence tokens) {
        create(tokens);
    }

    private void create(TokenSequence tokens) {
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
            switch (t.getType()) {
                case EXPRESSION:
                    appendValue(new Expression(t.getContent()));
                    break;
                case BRACKET_EXPRESSION:
                    appendValue(new ArrayLiteral(t.getContent()));
                    break;
                case LITERAL:
                    appendValue(new Literal(t.getContent()));
                    break;
                case OPERATOR:
                    if (operator == null) {
                        operator = t.getContent();
                    } else {
                        new Exception("no space for operator: " + t.getContent()).printStackTrace();
                    }
                    break;
                case IDENTIFIER:
                    appendValue(new VariableReference(t.getContent()));
                    break;
                default: new Exception("invalid token type " + t.getType()).printStackTrace();
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
