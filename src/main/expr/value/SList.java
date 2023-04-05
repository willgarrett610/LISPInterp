package main.expr.value;

import java.util.List;
import java.util.stream.Collectors;

public class SList extends Value<List<Value>> {

    public SList(List<Value> values) {
        super(values);
    }

    @Override
    public String toString() {
        String values = value.stream()
                .map(val -> val.toString())
                .collect(Collectors.joining(" ", "(", ")"));
        return values;
    }
}
