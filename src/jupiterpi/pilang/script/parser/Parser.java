package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.instructions.DeclareVariableInstruction;
import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.instructions.ReassignVariableInstruction;
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
            Instruction instruction = null;
            if (line.contains(new Token(Token.Type.ASSIGN))) {
                List<TokenSequence> parts = line.split(new Token(Token.Type.ASSIGN));

                TokenSequence head = parts.get(0);
                TokenSequence expr = parts.get(1);

                Value value = new Expression(expr.backToString());
                String name = head.get(head.size()-1).getContent();
                if (head.size() > 1) {
                    DataType type = DataType.from(head.get(0).getContent());
                    instruction = new DeclareVariableInstruction(type, name, value);
                } else {
                    instruction = new ReassignVariableInstruction(name, value);
                }
            }
            if (instruction == null) new Exception("invalid line: " + line.backToString()).printStackTrace();
            instructions.add(instruction);
        }
    }
}
