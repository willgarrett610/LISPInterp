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

    // Constructs linked list from java List
    public static SList fromList(List<Value<?>> values) {
        SList head = null;
        // Loops backwards through list
        for (int i = values.size() - 1; i >= 0; i--) {
            Value<?> value = values.get(i);
            // Link new item to the list
            ListElement elmt = new ListElement(value, head);
            // Update head pointer
            head = new SList(elmt);
        }
        return head;
    }

    @Override
    public String toString() {
        StringBuilder vBuilder = new StringBuilder("(");

        // Start traversal
        Value<?> curr = this;

        do {
            if (!(curr instanceof SList)) {
                vBuilder.append(". ").append(curr.getValue());
                curr = null;
                continue;
            }

            SList sList = (SList) curr;

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