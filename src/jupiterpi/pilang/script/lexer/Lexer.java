package jupiterpi.pilang.script.lexer;

import jupiterpi.pilang.script.lexer.Token.Type;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.values.DataType;

import java.util.ArrayList;
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
    private final List<String> operators = Arrays.asList("+-*/".split(""));
    private final List<String> numbers = Arrays.asList("0123456789".split(""));
    private final List<String> whitespaces = Arrays.asList(" ".split(""));
    private final List<String> text = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));

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
                    buffer = null;
                    bufferType = null;
                    continue;
                }
            }
            if (bracketLevel > 0) {
                buffer += c;
                continue;
            }

            if (c.equals("=")) {
                flushBuffer();
                buffer = c;
                bufferType = "assign";
                continue;
            }

            String cType = getCharacterType(c);
            if (cType == "whitespace") {
                flushBuffer();
                buffer = null;
                bufferType = null;
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
        if (listContains(numbers, c)) return "literal";
        if (listContains(text, c)) return "text";
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
                type = OPERATOR;
                break;
            case "literal":
                type = LITERAL;
                break;
            case "text":
                if (DataType.from(buffer) != null) {
                    type = TYPE;
                } else {
                    type = IDENTIFIER;
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
    }
}
