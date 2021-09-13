package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.parsing.Expression;

public class FunctionCallInstruction extends Instruction {
    private Expression expression;

    public FunctionCallInstruction(TokenSequence expr) {
        this.expression = new Expression(expr);
    }

    @Override
    public void execute(Scope scope) {
        expression.get(scope);
    }
}
