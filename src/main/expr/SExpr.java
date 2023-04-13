package main.expr;

import main.Environment;
import main.error.FunctionException;
import main.expr.value.Literal;
import main.expr.value.Value;
import main.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SExpr extends Expr {

    final private List<Expr> children;

    public SExpr(List<Expr> children) {
        this.children = children;
    }

    public List<Expr> getChildren() {
        return this.children;
    }

    @Override
    public Value evaluate(Environment environment) throws FunctionException {
        Expr funcNameVal = children.get(0);
        if (!(funcNameVal instanceof Symbol)) {
            throw new FunctionException("Function name is not a symbol: " + funcNameVal.toString());
        }

        List<Expr> params = new ArrayList<>();
        for (int i = 1; i < children.size(); i++) {
            Expr child = children.get(i);
            params.add(child);
        }

        Function function = environment.getFunction(((Symbol) funcNameVal).getName());

        if (function == null) return Literal.NIL;

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
