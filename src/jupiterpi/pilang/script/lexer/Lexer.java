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
    private final List<String> operators = Arrays.asList("+-*/&|=".split(""));
    private final List<String> literal = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.0123456789".split(""));
    private final List<String> literalNumberStart = Arrays.asList("0123456789".split(""));
    private final List<String> literalTextStart = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
    private final List<String> whitespaces = Arrays.asList(" \t\n\r".split(""));
    private final List<String> brackets = Arrays.asList("()[]".split(""));

    // buffer
    private String buffer = null;
    private String bufferType = null;
    private List<BracketType> bracketStack = new ArrayList<>();

    private enum BracketType {
        PARENTHESES, BRACKETS
    }

    private TokenSequence generateTokens(String expr) {
        expr = expr + ";";
        for (String c : expr.split("")) {
            if (c.equals(";")) {
                flushBuffer();
                continue;
            }

            if (listContains(brackets, c)) {
                int index = listIndexOf(brackets, c);
                if ((index+1) % 2 == 1) { // opening bracket
                    BracketType type = c.equals("(") ? BracketType.PARENTHESES : BracketType.BRACKETS;
                    bracketStack.add(type);
                    if (bracketStack.size() == 1) {
                        flushBuffer();
                        buffer = "";
                        bufferType = type.toString();
                        continue;
                    }
                } else { // closing bracket
                    BracketType type = c.equals(")") ? BracketType.PARENTHESES : BracketType.BRACKETS;
                    if (bracketStack.get(bracketStack.size()-1) == type) {
                        bracketStack.remove(bracketStack.size()-1);
                    } else {
                        new Exception("closing " + type + " bracket inside " + bracketStack.get(bracketStack.size()-1)).printStackTrace();
                    }
                    if (bracketStack.size() == 0) {
                        flushBuffer();
                        continue;
                    }
                }
            }
            if (bracketStack.size() > 0) {
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
    private int listIndexOf(List<String> list, String c) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(c)) return i;
        }
        return -1;
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
            case "PARENTHESES":
                type = EXPRESSION;
                break;
            case "BRACKETS":
                type = BRACKET_EXPRESSION;
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
