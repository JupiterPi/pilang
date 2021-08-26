package jupiterpi.pilang.script.parser;

import jupiterpi.pilang.script.instructions.*;
import jupiterpi.pilang.script.instructions.structures.IfInstruction;
import jupiterpi.pilang.script.instructions.structures.WhileInstruction;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.parsing.Expression;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Instruction> instructions = new ArrayList<>();

    public Parser(List<TokenSequence> lines) {
        parseInstructions(lines);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    /* parser */

    private boolean insideHeader;
    private void parseInstructions(List<TokenSequence> lines) {
        insideHeader = true;
        for (TokenSequence line : lines) {
            if (line.isEmpty()) continue;
            List<Instruction> parsedInstructions = parseLine(line);
            instructions.addAll(parsedInstructions);
        }
    }

    private List<Instruction> parseLine(TokenSequence line) {
        List<Instruction> instructions = new ArrayList<>();

        // DeclareVariable, ReassignVariable
        if (line.contains(new Token(Token.Type.ASSIGN))) {
            List<TokenSequence> parts = line.split(new Token(Token.Type.ASSIGN));

            TokenSequence head = parts.get(0);
            TokenSequence expr = parts.get(1);

            Value value = new Expression(expr);
            if (head.contains(new Token(Token.Type.TYPE))) {
                DataType type = DataType.fromTokenSequence(head.subsequence(0, head.size()-1));
                String name = head.get(head.size()-1).getContent();

                instructions.add(new DeclareVariableInstruction(type, name, value));
            } else {
                TokenSequence reference = new TokenSequence(head);

                instructions.add(new ReassignVariableInstruction(reference, value));
            }

            insideHeader = false;
            return instructions;
        }

        // ImportScript
        if (line.get(0).getType() == Token.Type.NOTICE || line.get(0).getType() == Token.Type.INTEGRATE) {
            if (!insideHeader) {
                new Exception("import script instruction outside header: " + line.backToString()).printStackTrace();
                return null;
            }

            boolean integrate = line.get(0).getType() == Token.Type.INTEGRATE;
            String scriptName = line.get(1).getContent();

            instructions.add(new ImportScriptInstruction(integrate, scriptName));
            return instructions;
        }

        // Return
        if (line.get(0).getType() == Token.Type.RETURN) {
            TokenSequence returnValueTokens = line.subsequence(1);
            Value returnValue = new Expression(returnValueTokens);

            instructions.add(new ReturnInstruction(returnValue));
            return instructions;
        }

        // If
        if (line.get(0).getType() == Token.Type.IF) {
                /*Tokens:
                    0: if
                    1: (condition)
                    2: {positive}
                    3: else
                    4: {negative}
                */

            Value condition;
            List<Instruction> positiveBody;
            List<Instruction> negativeBody = null;

            Token conditionToken = line.get(1);
            if (conditionToken.getType() != Token.Type.EXPRESSION) new Exception("parens expression required after 'if': " + line).printStackTrace();
            condition = new Expression(conditionToken.getContent());

            List<Token> bodyTokens = new ArrayList<>();

            bodyTokens.add(line.get(2));
            if (line.get(2).getType() != Token.Type.BRACES_EXPRESSION) new Exception("braces expression required after if condition: " + line).printStackTrace();
            positiveBody = parseBraces(line.get(2));

            int restIndex = 3;

            if (line.get(3).getType() == Token.Type.ELSE) {
                if (line.get(4).getType() != Token.Type.BRACES_EXPRESSION) new Exception("braces expression required after else: " + line).printStackTrace();
                negativeBody = parseBraces(line.get(4));

                restIndex = 5;
            }

            instructions.add(new IfInstruction(condition, positiveBody, negativeBody));
            TokenSequence rest = line.subsequence(restIndex);
            if (rest.size() > 0) instructions.addAll(parseLine(rest));
            return instructions;
        }

        if (line.get(0).getType() == Token.Type.WHILE) {
            /*Tokens:
                0: while
                1: (condition)
                2: {body}
                */

            Value condition;
            List<Instruction> body;

            Token conditionToken = line.get(1);
            if (conditionToken.getType() != Token.Type.EXPRESSION) new Exception("parens expression required after 'while': " + line).printStackTrace();
            condition = new Expression(conditionToken.getContent());

            if (line.get(2).getType() != Token.Type.BRACES_EXPRESSION) new Exception("braces expression required after while condition: " + line).printStackTrace();
            body = parseBraces(line.get(2));

            instructions.add(new WhileInstruction(condition, body));
            TokenSequence rest = line.subsequence(3);
            if (rest.size() > 0) instructions.addAll(parseLine(rest));
            return instructions;
        }

        // FunctionCall          !!!!! last option !!!!!
        instructions.add(new FunctionCallInstruction(line));
        return instructions;
    }

    private List<Instruction> parseBraces(Token token) {
        List<TokenSequence> lines = new Lexer(token.getContent()).getTokens().split(new Token(Token.Type.SEMICOLON));
        return new Parser(lines).getInstructions();
    }
}
