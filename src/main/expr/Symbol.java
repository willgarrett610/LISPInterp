package main.expr;

import main.Environment;
import main.expr.value.Value;

public class Symbol extends Expr {

    private String name;

    public Symbol(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Value evaluate(Environment environment) {
        Value value = environment.getValue(this.name);
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
