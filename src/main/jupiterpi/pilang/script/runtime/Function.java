package jupiterpi.pilang.script.runtime;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.Parser;
import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;
import jupiterpi.pilang.values.Value;

import java.util.List;

public class Function extends Scope {
    private List<Instruction> instructions;

    public Function(Scope parentScope, Token content) {
        super(parentScope);

        TokenSequence tokens = new Lexer(content.getContent()).getTokens();
        List<TokenSequence> lines = tokens.split(new Token(Token.Type.SEMICOLON));
        instructions = new Parser(lines).getInstructions();
    }

    public Value execute() {
        for (Instruction instruction : instructions) {
            instruction.execute(this);
        }
        return null;
    }
}
