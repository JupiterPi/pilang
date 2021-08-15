package jupiterpi.pilang.values.functions;

import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.values.DataType;

public class VariableHead {
    private DataType type;
    private String name;

    public VariableHead(TokenSequence tokens) {
        this.type = DataType.fromTokenSequence(tokens.subsequence(0, tokens.size()-1));
        this.name = tokens.get(tokens.size()-1).getContent();
    }

    public DataType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "VariableHead{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
