package main.expr.value;

public class ListElement {

    Value<?> elmt;
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
