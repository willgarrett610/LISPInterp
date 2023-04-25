package main.expr;

import main.Environment;
import main.error.LispException;
import main.expr.value.Literal;
import main.expr.value.Value;

public class Symbol extends Expr {

    private final String name;

    public Symbol(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Value<?> evaluate(Environment environment) throws LispException {
        if (this.name.equalsIgnoreCase("T")) return new Literal("T");

        return environment.getValue(this.name);
    }

    @Override
    public String getType() {
        return "SYMBOL";
    }

    @Override
    public String toString() {
        return name;
    }
}
