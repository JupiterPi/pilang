package jupiterpi.pilang.values.parsing;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.parser.tokens.Token;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.util.TokensUtil;
import jupiterpi.pilang.values.*;
import jupiterpi.pilang.values.arrays.ArrayCallWrapper;
import jupiterpi.pilang.values.arrays.ArrayLiteral;
import jupiterpi.pilang.values.functions.FunctionCallWrapper;
import jupiterpi.pilang.values.functions.FunctionLiteral;
import jupiterpi.pilang.values.operations.Operator;
import jupiterpi.pilang.values.other.Literal;
import jupiterpi.pilang.values.other.VariableReference;
import jupiterpi.pilang.values.parsing.signs.Sign;
import jupiterpi.pilang.values.parsing.signs.SignSequence;
import jupiterpi.pilang.values.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private SignSequence signs = new SignSequence();

    public ExpressionParser(TokenSequence tokens) {
        parseTokens(tokens);
    }

    public SignSequence getSigns() {
        return signs;
    }

    /* parser */

    private TokenSequence buffer = new TokenSequence();

    private void parseTokens(TokenSequence tokens) {
        for (Token token : tokens) {
            if (token.getType() == Token.Type.OPERATOR) {
                flushBuffer();

                buffer.add(token);
                flushBuffer();
            } else {
                buffer.add(token);
            }
        }
        flushBuffer();
    }

    private void flushBuffer() {
        if (!buffer.isEmpty()) {
            signs.add(parseSign(buffer));
            buffer = new TokenSequence();
        }
    }

    private Sign parseSign(TokenSequence tokens) {

        // operator
        if (tokens.size() == 1 && tokens.get(0).getType() == Token.Type.OPERATOR) {
            return Operator.makeOperator(tokens.get(0).getContent());
        }

        // property
        if (tokens.endsWith(new TokenSequence(new Token(Token.Type.COLON), new Token(Token.Type.IDENTIFIER)))) {
            int propertyIndex = tokens.lastIndexOf(new Token(Token.Type.COLON));
            TokenSequence propertyTokens = tokens.subsequence(propertyIndex);
            TokenSequence sourceTokens = tokens.subsequence(0, propertyIndex);

            Value source = (Value) parseSign(sourceTokens);
            String propertyName = propertyTokens.get(1).getContent();
            return new Property(source, propertyName);
        }

        // array call wrapper
        if (tokens.size() > 1 && tokens.endsWith(new TokenSequence(new Token(Token.Type.BRACKET_EXPRESSION)))) {
            int arrayCallWrapperIndex = tokens.lastIndexOf(new Token(Token.Type.BRACKET_EXPRESSION));
            TokenSequence arrayCallWrapperTokens = tokens.subsequence(arrayCallWrapperIndex);
            TokenSequence sourceTokens = tokens.subsequence(0, arrayCallWrapperIndex);

            Value source = (Value) parseSign(sourceTokens);
            return new ArrayCallWrapper(source, new Expression(arrayCallWrapperTokens.get(0).getContent()));
        }

        // function call wrapper
        if (tokens.size() > 1 && tokens.endsWith(new TokenSequence(new Token(Token.Type.EXPRESSION)))) {
            int functionCallWrapperIndex = tokens.lastIndexOf(new Token(Token.Type.EXPRESSION));
            TokenSequence functionCallWrapperTokens = tokens.subsequence(functionCallWrapperIndex);
            TokenSequence sourceTokens = tokens.subsequence(0, functionCallWrapperIndex);

            List<Value> parameters = new ArrayList<>();
            TokenSequence parametersTokens = new Lexer(functionCallWrapperTokens.get(0).getContent()).getTokens();
            for (TokenSequence parameterTokens : parametersTokens.split(new Token(Token.Type.COMMA))) {
                parameters.add(new Expression(parameterTokens));
            }

            Value source = (Value) parseSign(sourceTokens);
            return new FunctionCallWrapper(source, parameters);
        }

        if (tokens.size() == 1) {
            Token t = tokens.get(0);
            Token.Type type = t.getType();

            // expression
            if (type == Token.Type.EXPRESSION) {
                return new Expression(t.getContent());
            }

            // array literal
            if (type == Token.Type.BRACKET_EXPRESSION) {
                return new ArrayLiteral(t.getContent());
            }

            // literal
            if (type == Token.Type.LITERAL) {
                return new Literal(t.getContent());
            }

            // variable reference
            if (type == Token.Type.IDENTIFIER) {
                return new VariableReference(t.getContent());
            }

        } else {

            // function
            if (tokens.startsWith(TokensUtil.tokenTypeSequence(Token.Type.TYPE, Token.Type.EXPRESSION)) && tokens.endsWith(TokensUtil.tokenTypeSequence(Token.Type.BRACES_EXPRESSION))) {
                return new FunctionLiteral(tokens);
            }

        }

        new Exception("invalid tokens (to be made a value): " + tokens.backToString()).printStackTrace();
        return null;

    }
}
