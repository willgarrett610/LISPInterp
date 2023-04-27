package main.function;

import main.Environment;
import main.error.LispException;
import main.expr.Expr;
import main.expr.SExpr;
import main.expr.value.Value;

import java.util.List;

public class LispFunction implements Function {

    List<String> paramNames;
    List<SExpr> lines;

    public LispFunction(List<String> paramNames, List<SExpr> lines) {
        this.paramNames = paramNames;
        this.lines = lines;
    }

    public Value<?> evaluate(Environment environment, List<Expr> params) throws LispException {
        List<Value<?>> paramValues = Expr.evaluateAll(environment, params);

        if (paramNames.size() != paramValues.size()) {
            String message = String
                    .format("Expected %d arguments but got %d", paramNames.size(), paramValues.size());
            throw new LispException(message);
        }

        environment = environment.addFunctionParams(paramNames, paramValues);

        Value<?> returnValue = null;
        for (SExpr line : lines) {
            returnValue = line.evaluate(environment);
        }

        return returnValue;
    }

}
