package main.function;

import main.Environment;
import main.error.LispException;
import main.expr.Expr;
import main.expr.value.Value;

import java.util.ArrayList;
import java.util.List;

public class JavaFunction implements Function {

    List<String> paramNames;
    JavaFunctionMethod method;
    boolean binaryCascade;
    boolean evaluateParams;

    public JavaFunction(JavaFunctionMethod method, boolean binaryCascade, boolean evaluateParams, String... paramNames) {
        this.paramNames = List.of(paramNames);
        this.method = method;
        this.binaryCascade = binaryCascade;
        this.evaluateParams = evaluateParams;
    }

    @Override
    public Value evaluate(Environment environment, List<Expr> params) throws LispException {
        if (this.binaryCascade && params.size() != 2) {
            if (params.size() < 2) {
                throw new LispException("Function needs at least 2 parameters");
            }
            Value out = evaluate(environment, params.subList(0,2));

            for (int i = 2; i < params.size(); i++) {
                List<Expr> cParams = new ArrayList<>();
                // Current cascade value
                cParams.add(out);
                // Next parameter
                cParams.add(params.get(i));

                out = evaluate(environment, cParams);
            }

            return out;
        }
        if (this.evaluateParams) {
            List<Value> paramValues = Expr.evaluateAll(environment, params);

            if (paramNames.size() != paramValues.size()) {
                String message = String
                        .format("Expected %d arguments but got %d", paramNames.size(), paramValues.size());
                throw new LispException(message);
            }

            environment = environment.addFunctionParams(paramNames, paramValues);
        }
        return method.evaluate(environment, params);
    }

}