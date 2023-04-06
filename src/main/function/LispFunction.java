package main.function;

import main.Environment;
import main.expr.Expr;
import main.expr.SExpr;
import main.expr.value.Value;

import java.util.HashMap;
import java.util.List;

public class LispFunction extends SExpr implements Function {

    List<String> paramNames;

    public LispFunction(List<String> paramNames, List<Expr> children) {
        super(children);
        this.paramNames = paramNames;
    }

    public Value evaluate(Environment environment, List<Value> params) {
        environment = environment.addVariables(paramNames, params);
        return super.evaluate(environment);
    }

}
