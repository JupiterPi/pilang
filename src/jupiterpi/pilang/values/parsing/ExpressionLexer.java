package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.values.parsing.Token.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jupiterpi.pilang.values.parsing.Token.Type.*;

public class ExpressionLexer {
    private List<Token> tokens = new ArrayList<>();

    public ExpressionLexer(String expr) {
        tokens = generateTokens(expr);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    /* lexer */

    // character types
    private final List<String> operators = Arrays.asList("+-*/".split(""));
    private final List<String> numbers = Arrays.asList("0123456789".split(""));
    private final List<String> whitespaces = Arrays.asList(" ".split(""));
    //private final List<String> brackets = Arrays.asList("()".split(""));/**/

    // buffer
    private String buffer = null;
    private Type bufferType = null;
    private int bracketLevel = 0;

    private List<Token> generateTokens(String expr) {
        expr = expr + ";";
        for (String c : expr.split("")) {
            if (c.equals(";")) {
                flushBuffer();
                continue;
            }

            if (c.equals("(")) {
                if (bracketLevel == 0) {
                    flushBuffer();
                    buffer = "";
                    bufferType = EXPRESSION;

                    bracketLevel++;
                    continue;
                }
                bracketLevel++;
            }
            if (c.equals(")")) {
                bracketLevel--;
                if (bracketLevel == 0) {
                    flushBuffer();
                    buffer = null;
                    bufferType = null;
                    continue;
                }
            }
            if (bracketLevel > 0) {
                buffer += c;
                continue;
            }

            Type cType = getCharacterType(c);
            if (cType == null) {
                flushBuffer();
                buffer = null;
                bufferType = null;
            } else {
                if (buffer == null) {
                    buffer = c;
                    bufferType = cType;
                } else {
                    if (bufferType == cType) {
                        buffer += c;
                    } else {
                        flushBuffer();
                        buffer = c;
                        bufferType = cType;
                    }
                }
            }
        }
        return tokens;
    }

    private Token.Type getCharacterType(String c) {
        if (listContains(operators, c)) return OPERATOR;
        if (listContains(numbers, c)) return LITERAL;
        if (listContains(whitespaces, c)) return null;
        new Exception("invalid character: " + c).printStackTrace();
        return null;
    }

    private boolean listContains(List<String> list, String c) {
        for (String lc : list) {
            if (lc.equals(c)) return true;
        }
        return false;
    }

    private void flushBuffer() {
        if (buffer == null || buffer.isEmpty()) return;
        tokens.add(new Token(bufferType, buffer));
    }
}
