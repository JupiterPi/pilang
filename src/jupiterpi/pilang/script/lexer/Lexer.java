package jupiterpi.pilang.script.lexer;

import jupiterpi.pilang.script.lexer.Token.Type;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.values.DataType;

import java.util.Arrays;
import java.util.List;

import static jupiterpi.pilang.script.lexer.Token.Type.*;

public class Lexer {
    private TokenSequence tokens = new TokenSequence();

    public Lexer(String expr) {
        tokens = generateTokens(expr);
    }

    public TokenSequence getTokens() {
        return tokens;
    }

    /* lexer */

    // character types
    private final List<String> operators = Arrays.asList("+-*/&|=".split(""));
    private final List<String> literal = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.0123456789".split(""));
    private final List<String> literalNumberStart = Arrays.asList("0123456789".split(""));
    private final List<String> literalTextStart = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
    private final List<String> whitespaces = Arrays.asList(" \t\n\r".split(""));

    // buffer
    private String buffer = null;
    private String bufferType = null;
    private int bracketLevel = 0;

    private TokenSequence generateTokens(String expr) {
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
                    bufferType = "expression";

                    bracketLevel++;
                    continue;
                }
                bracketLevel++;
            }
            if (c.equals(")")) {
                bracketLevel--;
                if (bracketLevel == 0) {
                    flushBuffer();
                    continue;
                }
            }
            if (bracketLevel > 0) {
                buffer += c;
                continue;
            }

            String cType = getCharacterType(c);
            if (cType == "whitespace") {
                flushBuffer();
            } else {
                if (buffer == null) {
                    buffer = c;
                    bufferType = cType;
                } else {
                    if (bufferType.equals(cType)) {
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

    private String getCharacterType(String c) {
        if (listContains(operators, c)) return "operator";
        if (listContains(literal, c)) return "literal";
        if (listContains(whitespaces, c)) return "whitespace";
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
        Type type = null;
        switch (bufferType) {
            case "operator":
                if (buffer.equals("=")) type = ASSIGN;
                else type = OPERATOR;
                break;
            case "literal":
                if (listContains(literalNumberStart, buffer.substring(0, 1))) {
                    type = LITERAL;
                } else {
                    if (DataType.from(buffer) != null) {
                        type = TYPE;
                    } else {
                        switch (buffer) {
                            case "notice": type = NOTICE; break;
                            case "integrate": type = INTEGRATE; break;
                            case "true": case "false": type = LITERAL; break;
                            default: type = IDENTIFIER;
                        }
                    }
                }
                break;
            case "expression":
                type = EXPRESSION;
                break;
            case "assign":
                type = ASSIGN;
                break;
        }
        tokens.add(new Token(type, buffer));

        buffer = null;
        bufferType = null;
    }
}
