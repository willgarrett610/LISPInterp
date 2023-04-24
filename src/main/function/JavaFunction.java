package main.function;

import main.Environment;
import main.error.LispException;
import main.expr.Expr;
import main.expr.value.Value;

import java.util.List;

public class JavaFunction implements Function {

    List<String> paramNames;
    JavaFunctionMethod method;
    boolean evaluateParams;

    public JavaFunction(JavaFunctionMethod method, boolean evaluateParams, String... paramNames) {
        this.paramNames = List.of(paramNames);
        this.method = method;
        this.evaluateParams = evaluateParams;
    }

    @Override
    public Value evaluate(Environment environment, List<Expr> params) throws LispException {
        if (this.evaluateParams) {
            List<Value> paramValues = Expr.evaluateAll(environment, params);
            environment = environment.addVariables(paramNames, paramValues);
        }
        return method.evaluate(environment, params);
    }

}
