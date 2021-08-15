package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.instructions.*;
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
        boolean insideHeader = true;
        for (TokenSequence line : lines) {
            if (line.isEmpty()) continue;

            Instruction instruction = null;

            // DeclareVariable, ReassignVariable
            if (line.contains(new Token(Token.Type.ASSIGN))) {
                List<TokenSequence> parts = line.split(new Token(Token.Type.ASSIGN));

                TokenSequence head = parts.get(0);
                TokenSequence expr = parts.get(1);

                Value value = new Expression(expr);
                String name = head.get(head.size()-1).getContent();
                if (head.size() > 1) {
                    DataType type = DataType.fromTokenSequence(head.subsequence(0, head.size()-1));
                    instruction = new DeclareVariableInstruction(type, name, value);
                } else {
                    instruction = new ReassignVariableInstruction(name, value);
                }

                insideHeader = false;
            }

            // ImportScript
            if (line.get(0).getType() == Token.Type.NOTICE || line.get(0).getType() == Token.Type.INTEGRATE) {
                if (!insideHeader) {
                    new Exception("import script instruction outside header: " + line.backToString()).printStackTrace();
                    continue;
                }

                boolean integrate = line.get(0).getType() == Token.Type.INTEGRATE;
                String scriptName = line.get(1).getContent();
                instruction = new ImportScriptInstruction(integrate, scriptName);
            }

            // Return
            if (line.get(0).getType() == Token.Type.RETURN) {
                TokenSequence returnValueTokens = line.subsequence(1);
                Value returnValue = new Expression(returnValueTokens);
                instruction = new ReturnInstruction(returnValue);
            }

            // FunctionCall          !!!!! last option !!!!!
            if (instruction == null) {
                instruction = new FunctionCallInstruction(line);
            }

            if (instruction == null) new Exception("invalid line: " + line.backToString()).printStackTrace();
            instructions.add(instruction);
        }
    }
}
