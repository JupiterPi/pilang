package jupiterpi.pilang.values;

import jupiterpi.pilang.script.parser.Token;
import jupiterpi.pilang.script.parser.TokenSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataType {
    public enum BaseType {
        INT, FLOAT, BOOL, CHAR
    }

    public enum Specification {
        ARRAY
    }

    private BaseType baseType;
    private List<Specification> specificationStack;

    public DataType(BaseType baseType) {
        this.baseType = baseType;
        this.specificationStack = new ArrayList<>();
    }

    public DataType(BaseType baseType, List<Specification> specificationStack) {
        this.baseType = baseType;
        this.specificationStack = specificationStack;
    }

    public static DataType from(String str) {
        BaseType baseType;
        try {
            baseType = BaseType.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return new DataType(baseType);
    }

    public static DataType from(TokenSequence tokens) {
        BaseType baseType;
        try {
            baseType = BaseType.valueOf(tokens.get(0).getContent().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        tokens = tokens.sublist(1);

        List<Specification> specificationStack = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getType() == Token.Type.BRACKET_EXPRESSION) {
                specificationStack.add(Specification.ARRAY);
            }
        }

        return new DataType(baseType, specificationStack);
    }

    /* getters */

    public BaseType getBaseType() {
        return baseType;
    }

    public List<Specification> getSpecificationStack() {
        return new ArrayList<>(specificationStack);
    }

    /* modify specification stack */

    public DataType sp_asArray() {
        List<Specification> specificationStack = getSpecificationStack();
        specificationStack.add(Specification.ARRAY);
        return new DataType(baseType, specificationStack);
    }

    public DataType sp_of() {
        List<Specification> specificationStack = getSpecificationStack();
        specificationStack.remove(specificationStack.size()-1);
        return new DataType(baseType, specificationStack);
    }

    /* clone */

    public DataType clone() {
        return new DataType(this);
    }

    public DataType(DataType original) {
        this.baseType = original.getBaseType();
        this.specificationStack = original.getSpecificationStack();
    }

    /* others */

    public boolean isArray() {
        return specificationStack.get(specificationStack.size()-1) == Specification.ARRAY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataType dataType = (DataType) o;
        return baseType == dataType.baseType &&
                Objects.equals(specificationStack, dataType.specificationStack);
    }

    @Override
    public String toString() {
        String str = this.baseType.toString().toLowerCase();
        for (Specification specification : specificationStack) {
            if (specification == Specification.ARRAY) {
                str += "[]";
            }
        }
        return str;
    }
}
