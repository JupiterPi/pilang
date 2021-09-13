package jupiterpi.pilang.values.arrays;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.tokens.Token;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.tools.util.AppendingList;

public class ArrayLiteral extends ArrayValue {
    public ArrayLiteral(String str) {
        super();
        TokenSequence tokens = new Lexer(str).getTokens();
        for (TokenSequence expr : tokens.split(new Token(Token.Type.COMMA))) {
            Value value = new Expression(expr);
            values.add(value);
        }
    }

    @Override
    public DataType getType(Scope scope) {
        return super.getType(scope);
    }

    @Override
    public String get(Scope scope) {
        return super.get(scope);
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
