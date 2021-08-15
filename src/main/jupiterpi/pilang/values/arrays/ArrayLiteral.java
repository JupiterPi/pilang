package jupiterpi.pilang.values.arrays;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.tools.util.AppendingList;

import java.util.ArrayList;
import java.util.List;

public class ArrayLiteral extends Value {
    private List<Value> values = new ArrayList<>();

    public ArrayLiteral(String str) {
        TokenSequence tokens = new Lexer(str).getTokens();
        for (TokenSequence expr : tokens.split(new Token(Token.Type.COMMA))) {
            Value value = new Expression(expr);
            values.add(value);
        }
    }

    @Override
    public DataType getType(Scope scope) {
        DataType type = null;
        for (Value value : values) {
            if (type == null) {
                type = value.getType(scope);
            } else {
                if (!(value.getType(scope).equals(type))) {
                    new Exception("cannot create array of unequal types: " + type + " and " + value.getType(scope)).printStackTrace();
                }
            }
        }
        return type.sp_asArray();
    }

    @Override
    public String get(Scope scope) {
        AppendingList str = new AppendingList();
        for (Value value : values) {
            str.add(value.get(scope));
        }
        return "[" + str.render(",") + "]";
    }

    @Override
    public String toString() {
        AppendingList str = new AppendingList();
        for (Value value : values) {
            str.add(value.toString());
        }
        return "ArrayLiteral{value=[" + str.render(", ") + "]}";
    }
}
