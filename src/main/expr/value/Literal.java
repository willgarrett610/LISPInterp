package main.expr.value;

public class Literal extends Value<String> {

    public static final Literal NIL = new Literal("NIL");
    public static final Literal T = new Literal("T");

    public Literal(String value) {
        super(value);
    }

    @Override
    public String getType() {
        return "LITERAL";
    }

    public static Literal fromBool(boolean bool) {
        return bool ? T : NIL;
    }

}
