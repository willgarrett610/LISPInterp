package main.function;

import main.Environment;
import main.error.FunctionException;
import main.expr.Expr;
import main.expr.value.Value;

import java.util.List;

public interface JavaFunctionMethod {

    public Value evaluate(Environment environment, List<Expr> params) throws FunctionException;

}
