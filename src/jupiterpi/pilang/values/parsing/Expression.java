package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.values.Literal;
import jupiterpi.pilang.values.Operation;
import jupiterpi.pilang.values.Value;

import java.util.Arrays;
import java.util.List;

public class Expression extends Value {
    private final Operation operation;

    public Expression(String expr) {
        operation = parseExpression(expr);
    }

    @Override
    public String get() {
        return operation.get();
    }

    public Operation getOperation() {
        return operation;
    }

    /* parser */

    // character types
    private final List<String> operators = Arrays.asList("+-*/".split(""));
    private final List<String> numbers = Arrays.asList("0123456789".split(""));
    private final List<String> whitespaces = Arrays.asList(" ".split(""));
    private final List<String> brackets = Arrays.asList("()".split(""));

    // buffer
    private String buffer = null;
    private String bufferType = null;
    private int bracketLevel = 0;

    // values buffer
    private Value a = null;
    private String operator = null;
    private Value b = null;

    private Operation parseExpression(String expr) {
        expr = expr + ";";
        for (String c : expr.split("")) {
            if (c.equals(";")) {
                flushBuffer();
                continue;
            }

            if (c.equals("(")) {
                if (bracketLevel == 0) {
                    flushBuffer();
                    flushOperation();
                    buffer = "";
                    bufferType = null;

                    bracketLevel++;
                    continue;
                }
                bracketLevel++;
            }
            if (c.equals(")")) {
                bracketLevel--;
                if (bracketLevel == 0) {
                    appendValue(new Expression(buffer));
                    buffer = null;
                    bufferType = null;
                    flushOperation();
                    continue;
                }
            }
            if (bracketLevel > 0) {
                buffer += c;
                continue;
            }

            String cType = getCharacterType(c);
            if (cType.equals("whitespace")) {
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
            flushOperation();
        }
        flushOperation();
        return (Operation) a;
    }

    private void flushOperation() {
        if (a != null && operator != null && b != null) {
            a = new Operation(a, operator, b);
            operator = null;
            b = null;
        }
    }

    private String getCharacterType(String c) {
        if (listContains(operators, c)) return "operator";
        if (listContains(numbers, c)) return "number";
        if (listContains(whitespaces, c)) return "whitespace";
        new Exception("invalid character: " + c).printStackTrace();
        return "";
    }

    private boolean listContains(List<String> list, String c) {
        for (String lc : list) {
            if (lc.equals(c)) return true;
        }
        return false;
    }

    private void flushBuffer() {
        if (buffer == null || buffer.isEmpty()) return;

        String type = "";
        for (String c : buffer.split("")) {
            String cType = getCharacterType(c);
            if (type.isEmpty()) {
                type = cType;
            } else {
                if (!type.equals(cType)) new Exception("multiple character types in buffer: " + buffer).printStackTrace();
            }
        }
        if (type.equals("operator")) {
            if (operator == null) {
                operator = buffer;
            } else {
                new Exception("multiple operators: " + operator + " and " + buffer).printStackTrace();
            }
        } else if (type.equals("number")) {
            Value value = new Literal(buffer);
            appendValue(value);
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
