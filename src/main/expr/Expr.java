package main.expr;

import main.Environment;
import main.error.LispException;
import main.expr.value.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class Expr {

    public abstract Value evaluate(Environment environment) throws LispException;

    public static List<Value> evaluateAll(Environment environment, List<Expr> exprList) throws LispException {
        List<Value> out = new ArrayList<>();
        for (Expr expr : exprList) {
            out.add(expr.evaluate(environment));
        }
        return out;
    }

}
