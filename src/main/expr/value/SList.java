package main.expr.value;

import main.Environment;

import java.util.List;
import java.util.stream.Collectors;

public class SList extends Value<List<Value>> {

    public boolean atomCons = false;

    public SList(List<Value> values) {
        super(values);
    }

    public SList(List<Value> values, boolean atomCons) {
        super(values);
        this.atomCons = atomCons;
    }

    @Override
    public List<Value> getValue() {
        return super.getValue();
    }

    @Override
    public Value evaluate(Environment environment) {
        if (value.size() == 0) return Literal.NIL;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder vBuilder = new StringBuilder("(");

        for (int i = 0; i < value.size(); i++) {
            Value elmt = value.get(i);

            if (atomCons && i == value.size() - 1) {
                vBuilder.append(". ");
            }

            vBuilder.append(elmt.toString());

            if (i != value.size() - 1) {
                vBuilder.append(" ");
            }
        }

        return vBuilder.append(")").toString();
    }

    @Override
    public String getType() {
        return "LIST";
    }
}
