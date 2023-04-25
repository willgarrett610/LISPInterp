package main.expr.value;

public class Number extends Value<Integer> {

    public Number(Integer value) {
        super(value);
    }

    @Override
    public String getType() {
        return "NUMBER";
    }

}
