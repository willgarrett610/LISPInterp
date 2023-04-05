package main.expr.value;

import main.expr.Expr;

public class Value<T> extends Expr {

    T value;

    public Value(T value) {
        this.value = value;
    }

    @Override
    public Value evaluate() {
        return this;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
