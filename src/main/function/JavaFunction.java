package main.function;

import main.Environment;
import main.expr.value.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaFunction implements Function {

    List<String> paramNames;
    JavaFunctionMethod method;

    public JavaFunction(JavaFunctionMethod method, String... paramNames) {
        this.paramNames = List.of(paramNames);
        this.method = method;
    }

    @Override
    public Value evaluate(Environment environment, List<Value> params) {
        environment = environment.addVariables(paramNames, params);
        return method.evaluate(environment);
    }

}
