package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.instructions.DeclareVariableInstruction;
import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.lexer.Token;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.Expression;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Instruction> instructions = new ArrayList<>();

    public Parser(List<TokenSequence> lines) {
        parseInstruction(lines);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    /* parser */

    private void parseInstruction(List<TokenSequence> lines) {
        for (TokenSequence line : lines) {
            if (line.contains(new Token(Token.Type.ASSIGN))) {
                List<TokenSequence> parts = line.split(new Token(Token.Type.ASSIGN));

                TokenSequence head = parts.get(0);
                TokenSequence expr = parts.get(1);

                DataType type = DataType.from(head.get(0).getContent());
                String name = head.get(1).getContent();
                Value value = new Expression(expr.backToString());

                Instruction instruction = new DeclareVariableInstruction(type, name, value);
                instructions.add(instruction);
            }
        }
    }
}
