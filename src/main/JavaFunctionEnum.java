package main;

import main.expr.value.Number;
import main.function.Function;
import main.function.JavaFunction;
import main.function.JavaFunctionMethod;

import java.util.HashMap;

public enum JavaFunctionEnum {

    ADD("+", JavaFunctionImpl.add, "A", "B");

    public String symbol;
    public JavaFunction function;

    JavaFunctionEnum(String symbol, JavaFunctionMethod method, String... paramNames) {
        this.symbol = symbol;
        this.function = new JavaFunction(method, paramNames);
    }

    public static void addFunctionsToMap(HashMap<String, Function> functions) {
        for (JavaFunctionEnum functionEnum : values()) {
            functions.put(functionEnum.symbol, functionEnum.function);
        }
    }

}

class JavaFunctionImpl {

    protected static final JavaFunctionMethod add = e -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");
        return new Number(a.getValue() + b.getValue());
    };

}
