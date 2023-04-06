package main.expr;

import main.Environment;
import main.expr.value.Value;

public abstract class Expr {

    public abstract Value evaluate(Environment environment);

}
