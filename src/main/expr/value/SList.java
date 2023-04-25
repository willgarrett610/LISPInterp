package main.expr.value;

import main.Environment;

import java.util.List;

public class SList extends Value<ListElement> {

    public SList(ListElement value) {
        super(value);
    }

    @Override
    public ListElement getValue() {
        return super.getValue();
    }

    @Override
    public Value<?> evaluate(Environment environment) {
        return this;
    }

    public static SList fromList(List<Value<?>> values) {
        SList curr = null;
        for (int i = values.size() - 1; i >= 0; i--) {
            Value<?> value = values.get(i);
            ListElement elmt = new ListElement(value, curr);
            curr = new SList(elmt);
        }
        return curr;
    }

    @Override
    public String toString() {
        StringBuilder vBuilder = new StringBuilder("(");

        // Start traversal
        Value<?> curr = this;

        do {
            if (!(curr instanceof SList sList)) {
                vBuilder.append(". ").append(curr.getValue());
                curr = null;
                continue;
            }

            Value<?> elmt = sList.value.elmt;

            vBuilder.append(elmt.getValue().toString());

            if (sList.value.getNext() != null) vBuilder.append(" ");

            curr = sList.value.next;
        } while (curr != null);

        return vBuilder.append(")").toString();
    }

    @Override
    public String getType() {
        return "LIST";
    }
}