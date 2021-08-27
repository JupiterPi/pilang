package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.parser.Token.Type;
import jupiterpi.pilang.values.DataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jupiterpi.pilang.script.parser.Token.Type.*;

public class Lexer {
    private TokenSequence tokens = new TokenSequence();

    public Lexer(String expr) {
        tokens = generateTokens(expr);
    }

    public TokenSequence getTokens() {
        return tokens;
    }

    /*public static void main(String[] args) {
        Lexer lexer = new Lexer("5+3+ 1.0 +true");
        TokenSequence tokens = lexer.getTokens();
        System.out.println(tokens);
    }*/

    /* lexer */

    // character types
    private final List<String> operators = Arrays.asList("+-*/&|=<>!".split(""));
    private final List<String> sequence = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.0123456789".split(""));
    private final List<String> sequenceNumberStart = Arrays.asList("0123456789".split(""));
    private final List<String> sequenceTextStart = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".split(""));
    private final List<String> whitespaces = Arrays.asList(" \t\n\r".split(""));
    private final List<String> brackets = Arrays.asList("()[]{}".split(""));

    // buffer
    private String buffer = null;
    private String bufferType = null;

    private List<BracketType> bracketStack = new ArrayList<>();
    private enum BracketType {
        PARENTHESES, BRACKETS, BRACES
    }

    private boolean insideChar = false;
    private String charBuffer = null;

    private final String END_CHARACTER = "ä";
    private TokenSequence generateTokens(String expr) {
        expr = expr + END_CHARACTER;
        for (String c : expr.split("")) {
            if (c.equals(END_CHARACTER)) {
                flushBuffer();
                continue;
            }

            if (listContains(brackets, c)) {
                int index = listIndexOf(brackets, c);
                if ((index+1) % 2 == 1) { // opening bracket
                    BracketType type = BracketType.PARENTHESES;
                    if (c.equals("[")) type = BracketType.BRACKETS;
                    if (c.equals("{")) type = BracketType.BRACES;

                    bracketStack.add(type);
                    if (bracketStack.size() == 1) {
                        flushBuffer();
                        buffer = "";
                        bufferType = type.toString();
                        continue;
                    }
                } else { // closing bracket
                    BracketType type = BracketType.PARENTHESES;
                    if (c.equals("]")) type = BracketType.BRACKETS;
                    if (c.equals("}")) type = BracketType.BRACES;

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

            if (insideChar) {
                if (c.equals("'")) {
                    tokens.add(new Token(LITERAL, String.format("'%s'", charBuffer)));
                    charBuffer = null;
                    insideChar = false;
                } else {
                    charBuffer += c;
                }
                continue;
            } else {
                if (c.equals("'")) {
                    flushBuffer();
                    charBuffer = "";
                    insideChar = true;
                    continue;
                }
            }

            if (c.equals(",")) {
                flushBuffer();
                buffer = ",";
                bufferType = "comma";
                flushBuffer();
                continue;
            }
            if (c.equals(";")) {
                flushBuffer();
                buffer = ";";
                bufferType = "semicolon";
                flushBuffer();
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
        if (listContains(sequence, c)) return "sequence";
        if (listContains(whitespaces, c)) return "whitespace";
        if (c.equals(",")) return "comma";
        if (c.equals(";")) return "semicolon";
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

    private final List<String> assignOperators = Arrays.asList("=", "+=", "-=", "*=", "/=");
    private final List<String> incrementOperators = Arrays.asList("++", "--");

    private void flushBuffer() {
        if (buffer == null) return;
        Type type = null;
        switch (bufferType) {
            case "operator":
                if (listContains(assignOperators, buffer)) type = ASSIGN;
                else if (listContains(incrementOperators, buffer)) type = INCREMENT;
                else type = OPERATOR;
                break;
            case "sequence":
                if (listContains(sequenceNumberStart, buffer.substring(0, 1)) || buffer.startsWith("'")) { // number or char
                    type = LITERAL;
                } else {
                    if (DataType.baseFromString(buffer) != null) {
                        type = TYPE;
                    } else {
                        switch (buffer) {
                            case "notice": type = NOTICE; break;
                            case "integrate": type = INTEGRATE; break;
                            case "return": type = RETURN; break;
                            case "if": type = IF; break;
                            case "else": type = ELSE; break;
                            case "while": type = WHILE; break;
                            case "for": type = FOR; break;
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
            case "BRACES":
                type = BRACES_EXPRESSION;
                break;
            case "comma":
                type = COMMA;
                break;
            case "semicolon":
                type = SEMICOLON;
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
