package main.function;

import main.Environment;
import main.expr.Expr;
import main.expr.value.Value;

import java.util.HashMap;
import java.util.List;

public interface Function {

    public Value evaluate(Environment environment, List<Value> params);

}
