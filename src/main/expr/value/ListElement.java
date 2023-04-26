package main.expr.value;

// Used to build a linked list for SList
public class ListElement {

    // Value of this element
    Value<?> elmt;
    // Next element in the linked list
    Value<?> next;

    public ListElement(Value<?> elmt, Value<?> next) {
        this.elmt = elmt;
        this.next = next;
    }

    public Value<?> getElmt() {
        return elmt;
    }

    public Value<?> getNext() {
        return next;
    }

}
