package jupiterpi.pilang.script.nativescripts;

import jupiterpi.pilang.script.parser.Lexer;
import jupiterpi.pilang.script.runtime.Function;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.values.DataType;
import jupiterpi.pilang.values.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class NativeFunction extends Function {
    List<DataType> parameters = new ArrayList<>();

    public NativeFunction(NativeScript script, String... parameters) {
        super(script);
        for (String parameter : parameters) {
            this.parameters.add(DataType.fromTokenSequence(new Lexer(parameter).getTokens()));
        }
    }

    @Override
    public Value executeFunction(List<Value> parameters, Scope callingScope) {
        if (this.parameters.size() == parameters.size()) {
            for (int i = 0; i < this.parameters.size(); i++) {
                DataType parameterType = this.parameters.get(i);
                Value value = parameters.get(i);
                if (!parameterType.equals(value.getType(callingScope))) {
                    new Exception("mismatching types in parameters").printStackTrace();
                }
            }
        } else new Exception("mismatching parameters").printStackTrace();

        List<Value> newParameters = new ArrayList<>();
        for (Value parameter : parameters) {
            newParameters.add(parameter.asFinal(callingScope));
        }

        Value returnValue = run(newParameters);
        if (returnValue == null) {
            returnValue = new Value() {
                @Override
                public DataType getType(Scope scope) {
                    return new DataType(DataType.BaseType.VOID);
                }

                @Override
                public String get(Scope scope) {
                    return "";
                }
            };
        } else System.out.println("non-null return value");
        return returnValue;
    }

    protected abstract Value run(List<Value> parameters);
}
