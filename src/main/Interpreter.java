package main;

import main.expr.Expr;
import main.expr.SExpr;
import main.expr.value.Value;
import main.parser.Parser;

import java.util.Deque;
import java.util.HashMap;

public class Interpreter {

    Environment environment;

    public Interpreter() {
        this.environment = new Environment();
    }

    public Value evaluate(String exprString) {
        Expr expr = Parser.parse(exprString);
        if (expr == null) return null;
        return expr.evaluate(this.environment);
    }

}
