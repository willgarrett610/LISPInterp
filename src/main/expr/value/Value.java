package main.expr.value;

import main.Environment;
import main.expr.Expr;

public abstract class Value<T> extends Expr {

    T value;

    public Value(T value) {
        this.value = value;
    }

    @Override
    public Value<?> evaluate(Environment environment) {
        return this;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
