package main.expr;

import main.Environment;
import main.error.FunctionException;
import main.expr.value.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class Expr {

    public abstract Value evaluate(Environment environment) throws FunctionException;

    public static List<Value> evaluateAll(Environment environment, List<Expr> exprList) throws FunctionException {
        List<Value> out = new ArrayList<>();
        for (Expr expr : exprList) {
            out.add(expr.evaluate(environment));
        }
        return out;
    }

}
