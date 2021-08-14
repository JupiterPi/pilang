package jupiterpi.pilang.values.parsing;

public class OperatorItem implements Item {
    private String operator;

    public OperatorItem(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
