package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.tokens.Token;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.values.*;
import jupiterpi.pilang.values.arrays.ArrayCallWrapper;
import jupiterpi.pilang.values.arrays.ArrayLiteral;
import jupiterpi.pilang.values.functions.FunctionCallWrapper;
import jupiterpi.pilang.values.functions.FunctionLiteral;
import jupiterpi.pilang.values.operations.Operator;
import jupiterpi.pilang.values.other.Literal;
import jupiterpi.pilang.values.other.VariableReference;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.SignSequence;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private SignSequence signs = new SignSequence();

    public ExpressionParser(TokenSequence tokens) {
        parseTokens(tokens);
    }

    public SignSequence getSigns() {
        return signs;
    }

    /* parser */

    private boolean partOfFunction = false;
    private TokenSequence functionDefinition = null;

    private void parseTokens(TokenSequence tokens) {
        for (Token t : tokens) {
            if (partOfFunction) {
                functionDefinition.add(t);
                if (t.getType() == Token.Type.BRACES_EXPRESSION) {
                    appendValue(new FunctionLiteral(functionDefinition));
                    partOfFunction = false;
                }
            } else {
                if (t.getType() == Token.Type.TYPE) {
                    partOfFunction = true;
                    functionDefinition = new TokenSequence();
                    functionDefinition.add(t);
                    continue;
                }

                Sign lastSign = signs.size() == 0 ? null : signs.get(signs.size() - 1);
                switch (t.getType()) {
                    case EXPRESSION:
                        if (lastSign == null) {
                            appendValue(new Expression(t.getContent()));
                            break;
                        }

                        if (lastSign instanceof Value) {
                            Value original = (Value) lastSign;

                            List<Value> parameters = new ArrayList<>();
                            TokenSequence parametersTokens = new Lexer(t.getContent()).getTokens();
                            for (TokenSequence parameterTokens : parametersTokens.split(new Token(Token.Type.COMMA))) {
                                parameters.add(new Expression(parameterTokens));
                            }

                            Value callWrapper = new FunctionCallWrapper(original, parameters);
                            signs.set(signs.size() - 1, callWrapper);
                        } else {
                            appendValue(new Expression(t.getContent()));
                        }
                        break;
                    case BRACKET_EXPRESSION:
                        if (lastSign == null) {
                            appendValue(new ArrayLiteral(t.getContent()));
                            break;
                        }

                        if (lastSign instanceof Value) {
                            Value original = (Value) lastSign;
                            Value callWrapper = new ArrayCallWrapper(original, new Expression(t.getContent()));
                            signs.set(signs.size() - 1, callWrapper);
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
        signs.add(value);
    }

    private void appendOperator(String operator) {
        signs.add(Operator.makeOperator(operator));
    }
}
