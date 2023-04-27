package main.function;

import main.Environment;
import main.error.LispException;
import main.expr.Expr;
import main.expr.value.Value;

import java.util.List;

public interface JavaFunctionMethod {

    Value<?> evaluate(Environment environment, List<Expr> params) throws LispException;

}
