package main;

import main.error.LispException;
import main.expr.Expr;
import main.expr.SExpr;
import main.expr.Symbol;
import main.expr.value.Literal;
import main.expr.value.Number;
import main.expr.value.Value;
import main.function.Function;
import main.function.JavaFunction;
import main.function.JavaFunctionMethod;
import main.function.LispFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum JavaFunctionEnum {

    IFF("IF", JavaFunctionImpl.iff, false),
    DEFINE("DEFINE", JavaFunctionImpl.define, false),
    SET("SET!", JavaFunctionImpl.set, false),
    DEFUN("DEFUN", JavaFunctionImpl.defun, false),
    ADD("+", JavaFunctionImpl.add, true, "A", "B"),
    SUB("-", JavaFunctionImpl.sub, true, "A", "B"),
    MULT("*", JavaFunctionImpl.MULT, true, "A", "B"),
    DIV("/", JavaFunctionImpl.DIV, true, "A", "B");
//    CAR("CAR", JavaFunctionImpl.CAR, true);

    final public String symbol;
    final public JavaFunction function;

    JavaFunctionEnum(String symbol, JavaFunctionMethod method, boolean evaluateParams, String... paramNames) {
        this.symbol = symbol;
        this.function = new JavaFunction(method, evaluateParams, paramNames);
    }

    public static void addFunctionsToMap(HashMap<String, Function> functions) {
        for (JavaFunctionEnum functionEnum : values()) {
            functions.put(functionEnum.symbol, functionEnum.function);
        }
    }

}

class JavaFunctionImpl {

    private static Symbol enforceSymbol(Expr expr) throws LispException {
        if (!(expr instanceof Symbol symbol)) {
            throw new LispException(expr.toString() + " is not a symbol");
        }

        return symbol;
    }

    private static SExpr enforceSExpr(Expr expr) throws LispException {
        if (!(expr instanceof SExpr sExpr)) {
            throw new LispException(expr.toString() + " is not an s-expression");
        }

        return sExpr;
    }

    private static boolean checkOverflow(long val) {
        if (val > Integer.MAX_VALUE || val < Integer.MIN_VALUE) {
            return true;
        }
        return false;
    }

    protected static final JavaFunctionMethod iff = (e, params) -> {
        System.out.println(params.size());
        if (params.size() < 2) {
            throw new LispException("IF requires either 2 or 3 arguments: " + params.size() + " given");
        }
        Expr condition = params.get(0);
        if (condition != Literal.NIL) {
            // TRUE
            Expr trueExpr = params.get(1);
            return trueExpr.evaluate(e);
        } else if (params.size() >= 3){
            // FALSE
            Expr falseExpr = params.get(2);
            return falseExpr.evaluate(e);
        }
        return Literal.NIL;
    };

    protected static final JavaFunctionMethod define = (e, params) -> {
        // TODO: Error handling
        Expr nameExpr = params.get(0);
        Symbol name = enforceSymbol(params.get(0));

        if (e.variableExists(name.getName())) {
            System.err.println("Variable " + name + " already exists");
            return null;
        }

        Value value = params.get(1).evaluate(e);

        e.setVariable(name.getName(), value);

        return new Literal(name.getName());
    };

    protected static final JavaFunctionMethod set = (e, params) -> {
        // TODO: Error handling
        Symbol name = enforceSymbol(params.get(0));

        if (!e.variableExists(name.getName())) {
            System.err.println("Variable " + name + " does not exists");
            return null;
        }

        Value value = params.get(1).evaluate(e);

        e.setVariable(name.getName(), value);

        return new Literal(name.getName());
    };

    protected static final JavaFunctionMethod defun = (e, params) -> {
        // TODO: Error handling
        Symbol name = enforceSymbol(params.get(0));

        if (e.functionExists(name.getName())) {
            throw new LispException("Function already exists: " + name);
        }

        SExpr paramNameSExpr = enforceSExpr(params.get(1));

        List<Expr> paramNameExprs = paramNameSExpr.getChildren();
        List<String> paramNames = new ArrayList<>();

        for (Expr pNameExpr : paramNameExprs) {
            Symbol pNameSymb = enforceSymbol(pNameExpr);
            paramNames.add(pNameSymb.getName());
        }

        List<Expr> bodyExprList = params.subList(2, params.size());
        List<SExpr> body = new ArrayList<>();
        for (Expr bodyExpr : bodyExprList) {
            SExpr bodySExpr = enforceSExpr(bodyExpr);
            body.add(bodySExpr);
        }

        LispFunction function = new LispFunction(paramNames, body);
        e.setFunction(name.getName(), function);

        return new Literal(name.getName());
    };

    protected static final JavaFunctionMethod add = (e, __) -> {
        long a = e.getNumber("A").getValue();
        long b = e.getNumber("B").getValue();

        long out = a + b;

        if (checkOverflow(out)) throw new LispException("Integer overflow");

        return new Number((int) out);
    };

    protected static final JavaFunctionMethod sub = (e, __) -> {
        long a = e.getNumber("A").getValue();
        long b = e.getNumber("B").getValue();

        long out = a - b;

        if (checkOverflow(out)) throw new LispException("Integer overflow");

        return new Number((int) out);
    };

    protected static final JavaFunctionMethod MULT = (e, __) -> {
        long a = e.getNumber("A").getValue();
        long b = e.getNumber("B").getValue();

        long out = a * b;

        if (checkOverflow(out)) throw new LispException("Integer overflow");

        return new Number((int) out);
    };

    protected static final JavaFunctionMethod DIV = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");
        if (b.getValue() == 0) throw new LispException("Dividing by zero!");
        return new Number(Math.floorDiv(a.getValue(), b.getValue()));
    };

}
