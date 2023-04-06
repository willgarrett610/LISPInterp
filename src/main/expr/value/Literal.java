package main.expr.value;

public class Literal extends Value<String> {

    public static final Literal NIL = new Literal("NIL");

    public Literal(String value) {
        super(value);
    }

    @Override
    public String getType() {
        return "LITERAL";
    }
}
