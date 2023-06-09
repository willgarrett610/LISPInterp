package main.expr.value;

public class Literal extends Value<String> {

    // NIL constant literal
    public static final Literal NIL = new Literal("NIL");
    // T constant literal
    public static final Literal T = new Literal("T");

    public Literal(String value) {
        super(value);
    }

    @Override
    public String getType() {
        return "LITERAL";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Literal))
            return false;
        Literal lit = (Literal) obj;
        return super.equals(obj) || lit.value.equalsIgnoreCase(this.value);
    }

    public static Literal fromBool(boolean bool) {
        return bool ? T : NIL;
    }

}
