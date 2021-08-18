package jupiterpi.pilang.values;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Function;
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
        DataType type = getType(scope);
        if (type.isArray()) {
            List<Value> values = new ArrayList<>();
            TokenSequence immediateTokens = new Lexer(get(scope)).getTokens();
            TokenSequence tokens = new Lexer(immediateTokens.get(0).getContent()).getTokens();
            for (TokenSequence item : tokens.split(new Token(Token.Type.COMMA))) {
                String str = item.backToString();
                values.add(new Value() {
                    @Override
                    public DataType getType(Scope scope) {
                        return type.sp_of();
                    }
                    @Override
                    public String get(Scope scope) {
                        return str;
                    }
                });
            }
            return values;
        } else {
            new Exception("tried to get array of non-array " + getType(scope) + " " + get(scope)).printStackTrace();
            return null;
        }
    }

    public Function getFunction(Scope scope) {
        if (getType(scope).isFunction()) {
            String str = get(scope);
            String id = str.substring(1, str.length()-1);
            return scope.getRegistry().findFunction(id);
        } else {
            new Exception("tried to get function of non-function " + getType(scope) + " " + get(scope)).printStackTrace();
            return null;
        }
    }

    public Value makeFinal(Scope scope) {
        DataType type = getType(scope);
        String value = get(scope);
        return new Value() {
            @Override
            public DataType getType(Scope scope) {
                return type;
            }

            @Override
            public String get(Scope scope) {
                return value;
            }
        };
    }
}
