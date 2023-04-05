package main.expr;

import main.expr.value.Value;

public class Symbol extends Expr {

    private String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
