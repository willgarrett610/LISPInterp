package main.expr;

import main.Environment;
import main.expr.value.Literal;
import main.expr.value.Value;
import main.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SExpr extends Expr {

    private List<Expr> children;

    public SExpr(List<Expr> children) {
        this.children = children;
    }

    @Override
    public Value evaluate(Environment environment) {
        Expr funcNameVal = children.get(0);
        if (!(funcNameVal instanceof Symbol)) {
            // TODO: Error handling
            System.err.println("Function name must be a symbol");
            return null;
        }

        List<Value> params = new ArrayList<>();
        for (int i = 1; i < children.size(); i++) {
            Expr child = children.get(i);
            params.add(child.evaluate(environment));
        }

        Function function = environment.getFunction(((Symbol) funcNameVal).getName());

        if (function == null) return null;

        return function.evaluate(environment, params);
    }

    @Override
    public String toString() {
        String values = children.stream()
                .map(val -> val.toString())
                .collect(Collectors.joining(" ", "(", ")"));
        return "'" + values;
    }

}
