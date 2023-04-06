package main.function;

import main.Environment;
import main.expr.value.Value;

public interface JavaFunctionMethod {

    public Value evaluate(Environment environment);

}
