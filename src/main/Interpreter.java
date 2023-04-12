package main;

import main.error.FunctionException;
import main.expr.Expr;
import main.expr.value.Literal;
import main.expr.value.Value;
import main.parser.Parser;

public class Interpreter {

    Environment environment;

    public Interpreter() {
        this.environment = new Environment();
    }

    public Value evaluate(String exprString) {
        Expr expr = Parser.parse(exprString);
        if (expr == null) return Literal.NIL;

        Value result = Literal.NIL;
        try {
            result = expr.evaluate(this.environment);
        } catch (FunctionException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

}
