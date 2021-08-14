package jupiterpi.pilang.values;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;

import java.util.ArrayList;
import java.util.List;

public abstract class Value {
    public abstract DataType getType(Scope scope);
    public abstract String get(Scope scope);

    public int getInteger(Scope scope) {
        if (getType(scope).equals(new DataType(DataType.BaseType.INT))) {
            return Integer.parseInt(get(scope));
        } else {
            new Exception(String.format("tried to get int value of %s %s", getType(scope), get(scope))).printStackTrace();
            return 0;
        }
    }

    public float getFloat(Scope scope) {
        if (getType(scope).equals(new DataType(DataType.BaseType.FLOAT))) {
            return Float.parseFloat(get(scope));
        } else {
            new Exception(String.format("tried to get float value of %s %s", getType(scope), get(scope))).printStackTrace();
            return 0.0f;
        }
    }

    public boolean getBool(Scope scope) {
        if (getType(scope).equals(new DataType(DataType.BaseType.FLOAT))) {
            return Boolean.parseBoolean(get(scope));
        } else {
            new Exception(String.format("tried to get bool value of %s %s", getType(scope), get(scope))).printStackTrace();
            return false;
        }
    }

    public char getChar(Scope scope) {
        if (getType(scope).equals(new DataType(DataType.BaseType.CHAR))) {
            return get(scope).charAt(1);
        } else {
            new Exception(String.format("tried to get char value of %s %s", getType(scope), get(scope))).printStackTrace();
            return '\u0000';
        }
    }

    public List<Value> getArray(Scope scope) {
        if (getType(scope).isArray()) {
            List<Value> values = new ArrayList<>();
            TokenSequence immediateTokens = new Lexer(get(scope)).getTokens();
            TokenSequence tokens = new Lexer(immediateTokens.get(0).getContent()).getTokens();
            for (TokenSequence item : tokens.split(new Token(Token.Type.COMMA))) {
                String str = item.backToString();
                if (str.startsWith("[")) {
                    values.add(new ArrayLiteral(str.substring(1, str.length()-1)));
                } else {
                    values.add(new Literal(str));
                }
            }
            return values;
        } else {
            new Exception("tried to get array of non-array " + getType(scope) + " " + get(scope)).printStackTrace();
            return null;
        }
    }
}
