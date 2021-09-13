package jupiterpi.pilang.script.instructions;

import jupiterpi.pilang.script.parser.tokens.Token;
import jupiterpi.pilang.script.parser.tokens.TokenSequence;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.FinalValue;
import jupiterpi.pilang.values.Value;
import jupiterpi.pilang.values.arrays.ArrayValue;
import jupiterpi.pilang.values.other.Operation;
import jupiterpi.pilang.values.parsing.Expression;
import jupiterpi.tools.util.AppendingList;

import java.util.ArrayList;
import java.util.List;

public class ReassignVariableInstruction extends Instruction {
    private TokenSequence reference;
    private String operator;
    private Value value;

    public ReassignVariableInstruction(TokenSequence reference, String operator, Value value) {
        this.reference = reference;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public void execute(Scope scope) {
        Variable variable = scope.getVariable(reference.get(0).getContent());

        /* modify assigning based on operator */

        Value targetValue;

        // =, +=, -=, *=, /=, ++, --
        switch (operator) {
            case "=": targetValue = value; break;
            case "+=": targetValue = new Operation(new Expression(reference), "+", value); break;
            case "-=": targetValue = new Operation(new Expression(reference), "-", value); break;
            case "*=": targetValue = new Operation(new Expression(reference), "*", value); break;
            case "/=": targetValue = new Operation(new Expression(reference), "/", value); break;
            case "++": targetValue = new Operation(new Expression(reference), "+", FinalValue.fromInt(1)); break;
            case "--": targetValue = new Operation(new Expression(reference), "-", FinalValue.fromInt(1)); break;
            default:
                new Exception("invalid operator: " + operator).printStackTrace();
                targetValue = value;
                break;
        }

        // non-array variables
        if (!variable.getType(scope).isArray()) {
            variable.set(scope, targetValue);
            return;
        }

        // generate indices
        Index targetIndex = new Index();
        if (reference.size() > 1) {
            TokenSequence indicesTokens = reference.subsequence(1);
            for (Token token : indicesTokens) {
                if (token.getType() == Token.Type.BRACKET_EXPRESSION) {
                    Value index = new Expression(token.getContent());
                    if (index.getType(scope).equals(new DataType(DataType.BaseType.INT))) {
                        targetIndex.down(index.getInteger(scope));
                    } else new Exception("index must be of int type!").printStackTrace();
                } else new Exception("invalid index: " + token).printStackTrace();
            }
        }

        // check if target array dimensions meet requirements
        List<DataType.Specification> variableSpecificationStack = variable.getType(scope).getSpecificationStack();
        for (int i = variableSpecificationStack.size()-1; i >= variableSpecificationStack.size() - targetIndex.getDepth(); i--) {
            DataType.Specification specification = variableSpecificationStack.get(i);
            if (specification != DataType.Specification.ARRAY) new Exception("array specified in ReassignVariableInstruction is some dimensions short! (needed " + targetIndex.getDepth() + "+ dimensions)").printStackTrace();
        }

        // check if base type is equal
        DataType.BaseType variableBase = variable.getType(scope).getBaseType();
        DataType.BaseType valueBase = value.getType(scope).getBaseType();
        if (variableBase != valueBase) new Exception(String.format("mismatching base types: variable: %s, new value: %s", variableBase, valueBase)).printStackTrace();

        // replace target in array
        Value replacement = generateArray(new Index(), targetIndex, variable.getArrayValue(scope), value);

        // reassign changed array to variable
        variable.set(scope, replacement);
    }

    private ArrayValue generateArray(Index currentIndex, Index targetIndex, ArrayValue original, Value targetReplacement) {
        ArrayValue arr = new ArrayValue();
        List<Value> originalValues = original.getValues();
        boolean success = false;
        for (int i = 0; i < originalValues.size(); i++) {
            Value value = originalValues.get(i);
            Index itemIndex = currentIndex.c().down(i);

            if (itemIndex.equals(targetIndex)) { // replace immediate target
                value = targetReplacement;
                success = true;
            } else if (targetIndex.subIndex(itemIndex.getDepth()).equals(itemIndex)) { // re-call for deeper target
                value = generateArray(itemIndex, targetIndex, value.getArrayValue(null), targetReplacement);
            }

            arr.addValue(value);
        }

        if (targetIndex.c().up().equals(currentIndex)) {
            if (!success) new Exception("array index out of bounds: " + targetIndex + "!").printStackTrace();
        }

        System.out.println(this);

        return arr;
    }

    private static class Index {
        private List<FinalValue> stack;

        public Index() {
            stack = new ArrayList<>();
        }

        public Index c() {
            return new Index(new ArrayList<>(stack));
        }

        public Index down(int index) {
            stack.add(FinalValue.fromInt(index));
            return this;
        }

        public Index up() {
            stack.remove(stack.size()-1);
            return this;
        }

        public int get(int depth) {
            return stack.get(depth).getInteger(null);
        }

        public int getDepth() {
            return stack.size();
        }

        public Index subIndex(int depth) {
            if (depth >= getDepth()) return new Index(new ArrayList<>(stack));
            List<FinalValue> subStack = new ArrayList<>();
            for (int i = 0; i < depth; i++) {
                subStack.add(stack.get(i));
            }
            return new Index(subStack);
        }
        private Index(List<FinalValue> stack) {
            this.stack = stack;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Index other = (Index) o;

            if (this.getDepth() != other.getDepth()) return false;
            for (int depth = 0; depth < this.getDepth(); depth++) {
                boolean equal = this.get(depth) == other.get(depth);
                if (!equal) return false;
            }
            return true;
        }

        @Override
        public String toString() {
            AppendingList str = new AppendingList();
            for (FinalValue value : stack) {
                str.add("[" + value.get() + "]");
            }
            return str.render("");
        }
    }

    @Override
    public String toString() {
        return "ReassignVariableInstruction{" +
                "reference='" + reference.backToString() + "'" +
                ", value=" + value +
                '}';
    }
}
