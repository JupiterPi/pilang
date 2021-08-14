package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private List<Item> items = new ArrayList<>();

    public ExpressionParser(TokenSequence tokens) {
        parseTokens(tokens);
    }

    public List<Item> getItems() {
        return items;
    }

    /* parser */

    private boolean partOfFunction = false;
    private TokenSequence functionDefinition = null;

    private void parseTokens(TokenSequence tokens) {
        for (Token t : tokens) {
            if (partOfFunction) {
                functionDefinition.add(t);
                if (t.getType() == Token.Type.BRACES_EXPRESSION) {
                    appendValue(makeFunction());
                    partOfFunction = false;
                }
            } else {
                if (t.getType() == Token.Type.TYPE) {
                    partOfFunction = true;
                    functionDefinition = new TokenSequence();
                    functionDefinition.add(t);
                    continue;
                }

                switch (t.getType()) {
                    case EXPRESSION:
                        appendValue(new Expression(t.getContent()));
                        break;
                    case BRACKET_EXPRESSION:
                        if (items.size() == 0) {
                            appendValue(new ArrayLiteral(t.getContent()));
                            break;
                        }

                        Item lastItem = items.get(items.size() - 1);
                        if (lastItem instanceof ValueItem) {
                            Value original = ((ValueItem) lastItem).getValue();
                            Value callWrapper = new ArrayCallWrapper(original, new Expression(t.getContent()));
                            items.set(items.size() - 1, new ValueItem(callWrapper));
                        } else {
                            appendValue(new ArrayLiteral(t.getContent()));
                        }
                        break;
                    case LITERAL:
                        appendValue(new Literal(t.getContent()));
                        break;
                    case OPERATOR:
                        appendOperator(t.getContent());
                        break;
                    case IDENTIFIER:
                        appendValue(new VariableReference(t.getContent()));
                        break;
                    default:
                        new Exception("invalid token type " + t.getType()).printStackTrace();
                }
            }
        }
    }

    private void appendValue(Value value) {
        items.add(new ValueItem(value));
    }

    private void appendOperator(String operator) {
        items.add(new OperatorItem(operator));
    }

    private Value makeFunction() {
        System.out.println(functionDefinition);
        return new Value() {
            @Override
            public DataType getType(Scope scope) {
                return null;
            }

            @Override
            public String get(Scope scope) {
                return null;
            }
        };
        // TODO implement function generation
    }
}
