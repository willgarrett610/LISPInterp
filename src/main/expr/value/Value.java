package main.expr.value;

import main.Environment;
import main.expr.Expr;

public class Value<T> extends Expr {

    T value;

    public Value(T value) {
        this.value = value;
    }

    @Override
    public Value evaluate(Environment environment) {
        return this;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getType() {
        return null;
    };

    @Override
    public String toString() {
        return value.toString();
    }

}
