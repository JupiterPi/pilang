package jupiterpi.pilang.values.functions;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Parser;
import jupiterpi.pilang.script.parser.tokens.Token;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.script.runtime.Function;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.ArrayList;
import java.util.List;

public class FunctionLiteral extends Value {
    private DataType type;
    private List<VariableHead> parameters;
    private List<Instruction> content;

    public FunctionLiteral(TokenSequence tokens) {
        if (tokens.size() >= 3) {
            TokenSequence type = tokens.subsequence(0, tokens.size()-2);
            Token parameters = tokens.get(tokens.size()-2);
            Token content = tokens.get(tokens.size()-1);

            this.type = DataType.fromTokenSequence(type).sp_asFunction();

            this.parameters = new ArrayList<>();
            TokenSequence parameterTokens = new Lexer(parameters.getContent()).getTokens();
            for (TokenSequence parameter : parameterTokens.split(new Token(Token.Type.COMMA))) {
                this.parameters.add(new VariableHead(parameter));
            }

            TokenSequence contentTokens = new Lexer(content.getContent()).getTokens();
            List<TokenSequence> lines = contentTokens.split(new Token(Token.Type.SEMICOLON));
            this.content = new Parser(lines).getInstructions();
        } else new Exception("malformed function definition").printStackTrace();
    }

    /* runtime */

    private boolean created = false;
    private Function function;

    @Override
    public DataType getType(Scope scope) {
        checkCreated(scope);
        return type;
    }

    @Override
    public String get(Scope scope) {
        checkCreated(scope);
        return String.format("{%s}", function.getReference());
    }

    private void checkCreated(Scope scope) {
        if (!created) {
            this.function = new Function(scope, parameters, type.sp_of(), content);
            created = true;
        }
    }
}
