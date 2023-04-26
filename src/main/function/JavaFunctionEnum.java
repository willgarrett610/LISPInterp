package main.function;

import main.error.LispException;
import main.expr.Expr;
import main.expr.SExpr;
import main.expr.Symbol;
import main.expr.value.*;
import main.expr.value.Number;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum JavaFunctionEnum {

    IFF("IF", JavaFunctionImpl.iff, false),
    DEFINE("DEFINE", JavaFunctionImpl.define, false),
    SET("SET!", JavaFunctionImpl.set, false),
    DEFUN("DEFUN", JavaFunctionImpl.defun, false),
    ADD("+", JavaFunctionImpl.add, true, true, "A", "B"),
    SUB("-", JavaFunctionImpl.sub, true, true, "A", "B"),
    MULT("*", JavaFunctionImpl.mult, true, true, "A", "B"),
    DIV("/", JavaFunctionImpl.div, true, true, "A", "B"),
    CAR("CAR", JavaFunctionImpl.car, true, "L"),
    CDR("CDR", JavaFunctionImpl.cdr, true, "L"),
    CONS("CONS", JavaFunctionImpl.cons, true, "A", "B"),
    SQRT("SQRT", JavaFunctionImpl.sqrt, true, "A"),
    POW("POW", JavaFunctionImpl.pow, true, "A", "B"),
    GT(">", JavaFunctionImpl.gt, true, "A", "B"),
    LT("<", JavaFunctionImpl.lt, true, "A", "B"),
    EQ("==", JavaFunctionImpl.eq, true, "A", "B"),
    GEQ(">=", JavaFunctionImpl.geq, true, "A", "B"),
    LEQ("<=", JavaFunctionImpl.leq, true, "A", "B"),
    NEQ("!=", JavaFunctionImpl.neq, true, "A", "B"),
    AND("AND", JavaFunctionImpl.and, true, "A", "B"),
    OR("OR", JavaFunctionImpl.or, true, "A", "B"),
    NOT("NOT", JavaFunctionImpl.not, true, "A"),
    QUIT("QUIT", JavaFunctionImpl.quit, true);

    final public String symbol;
    final public JavaFunction function;

    JavaFunctionEnum(String symbol, JavaFunctionMethod method, boolean binaryCascade, boolean evaluateParams, String... paramNames) {
        this.symbol = symbol;
        this.function = new JavaFunction(method, binaryCascade, evaluateParams, paramNames);
    }

    JavaFunctionEnum(String symbol, JavaFunctionMethod method, boolean evaluateParams, String... paramNames) {
        this.symbol = symbol;
        this.function = new JavaFunction(method, false, evaluateParams, paramNames);
    }

    public static void addFunctionsToMap(HashMap<String, Function> functions) {
        for (JavaFunctionEnum functionEnum : values()) {
            functions.put(functionEnum.symbol, functionEnum.function);
        }
    }

}

class JavaFunctionImpl {

    private static Symbol enforceSymbol(Expr expr) throws LispException {
        if (!(expr instanceof Symbol)) {
            throw new LispException(expr.toString() + " is not a symbol");
        }

        return (Symbol) expr;
    }

    private static SExpr enforceSExpr(Expr expr) throws LispException {
        if (!(expr instanceof SExpr)) {
            throw new LispException(expr.toString() + " is not an s-expression");
        }

        return (SExpr) expr;
    }

    private static boolean checkOverflow(long val) {
        return (val > Integer.MAX_VALUE || val < Integer.MIN_VALUE);
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
        Expr nameExpr = params.get(0);
        Symbol name = enforceSymbol(params.get(0));

        if (e.variableExists(name.getName())) {
            throw new LispException("Variable " + name + " already exists");
        }

        Value<?> value = params.get(1).evaluate(e);

        e.setVariable(name.getName(), value);

        return value;
    };

    protected static final JavaFunctionMethod set = (e, params) -> {
        Symbol name = enforceSymbol(params.get(0));

        if (!e.variableExists(name.getName())) {
            throw new LispException("Variable " + name + " does not exists");
        }

        Value<?> value = params.get(1).evaluate(e);

        e.setVariable(name.getName(), value);

        return value;
    };

    protected static final JavaFunctionMethod defun = (e, params) -> {
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

    protected static final JavaFunctionMethod mult = (e, __) -> {
        long a = e.getNumber("A").getValue();
        long b = e.getNumber("B").getValue();

        long out = a * b;

        if (checkOverflow(out)) throw new LispException("Integer overflow");

        return new Number((int) out);
    };

    protected static final JavaFunctionMethod div = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");
        if (b.getValue() == 0) throw new LispException("Dividing by zero!");
        return new Number(Math.floorDiv(a.getValue(), b.getValue()));
    };

    protected static final JavaFunctionMethod car = (e, __) -> {
        SList sList = e.getSList("L");

        return sList.getValue().getElmt();
    };

    protected static final JavaFunctionMethod cdr = (e, __) -> {
        SList sList = e.getSList("L");

        return sList.getValue().getNext();
    };

    protected static final JavaFunctionMethod cons = (e, __) -> {
        Value<?> arg1 = e.getValue("A");
        Value<?> arg2 = e.getValue("B");

        ListElement listElement = new ListElement(arg1, arg2);
        return new SList(listElement);
    };

    protected static final JavaFunctionMethod sqrt = (e, __) -> {
        Number a = e.getNumber("A");

        int value = (int) Math.floor(Math.sqrt(a.getValue()));

        return new Number(value);
    };

    protected static final JavaFunctionMethod pow = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        int value = (int) Math.pow(a.getValue(), b.getValue());

        return new Number(value);
    };

    protected static final JavaFunctionMethod gt = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        return Literal.fromBool(a.getValue() > b.getValue());
    };

    protected static final JavaFunctionMethod lt = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        return Literal.fromBool(a.getValue() < b.getValue());
    };

    protected static final JavaFunctionMethod eq = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        return Literal.fromBool(a.getValue() == b.getValue());
    };

    protected static final JavaFunctionMethod geq = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        return Literal.fromBool(a.getValue() >= b.getValue());
    };

    protected static final JavaFunctionMethod leq = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        return Literal.fromBool(a.getValue() <= b.getValue());
    };

    protected static final JavaFunctionMethod neq = (e, __) -> {
        Number a = e.getNumber("A");
        Number b = e.getNumber("B");

        return Literal.fromBool(a.getValue() != b.getValue());
    };

    protected static final JavaFunctionMethod and = (e, __) -> {
        Value a = e.getValue("A");
        Value b = e.getValue("B");

        if (a.equals(Literal.NIL)) {
            return a;
        }

        return b;
    };

    protected static final JavaFunctionMethod or = (e, __) -> {
        Value a = e.getValue("A");
        Value b = e.getValue("B");

        if (a.equals(Literal.NIL)) {
            return b;
        }

        return a;
    };

    protected static final JavaFunctionMethod not = (e, __) -> {
        Value a = e.getValue("A");

        if (a.equals(Literal.NIL)) {
            return Literal.T;
        }

        return Literal.NIL;
    };

    protected static final JavaFunctionMethod quit = (___, __) -> {
        return null;
    };

}
