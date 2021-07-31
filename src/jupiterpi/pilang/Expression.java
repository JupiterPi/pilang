package jupiterpi.pilang;

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

    /* parser */

    // character types
    private final List<String> operators = Arrays.asList("+-*/".split(""));
    private final List<String> numbers = Arrays.asList("0123456789".split(""));
    private final List<String> whitespaces = Arrays.asList(" ".split(""));

    // buffer
    private String buffer = null;
    private String bufferType = null;

    // values buffer
    private Value a = null;
    private String operator = null;
    private Value b = null;

    private Operation parseExpression(String expr) {
        expr = expr + ";";
        for (String c : expr.split("")) {
            if (c.equals(";")) {
                flushBuffer();
            }

            String cType = getCharacterType(c);
            if (cType.equals("whitespace")) {
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

            if (a != null && operator != null && b != null) {
                a = new Operation(a, operator, b);
                operator = null;
                b = null;
            }
        }
        return (Operation) a;
    }

    private String getCharacterType(String c) {
        if (operators.contains(c)) return "operator";
        if (numbers.contains(c)) return "number";
        if (whitespaces.contains(c)) return "whitespace";
        new Exception("invalid character: " + c).printStackTrace();
        return "";
    }

    private void flushBuffer() {
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
            if (a == null) {
                a = value;
            } else {
                if (b == null) {
                    b = value;
                } else {
                    new Exception("no space for value: " + buffer).printStackTrace();
                }
            }
        }
    }
}
