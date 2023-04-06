package main.expr;

import main.expr.value.Value;

import java.util.List;
import java.util.stream.Collectors;

public class SExpr extends Expr {

    private List<Expr> children;

    public SExpr(List<Expr> children) {
        this.children = children;
    }

    @Override
    public Value evaluate() {
        return null;
    }

    @Override
    public String toString() {
        String values = children.stream()
                .map(val -> val.toString())
                .collect(Collectors.joining(" ", "(", ")"));
        return "'" + values;
    }

}
