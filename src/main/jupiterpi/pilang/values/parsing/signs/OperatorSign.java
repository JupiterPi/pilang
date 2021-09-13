package jupiterpi.pilang.values.parsing.signs;

public class OperatorSign implements Sign {
    private String operator;

    public OperatorSign(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
