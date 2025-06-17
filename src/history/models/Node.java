package history.models;

import models.AbstractTask;

public class Node {
    private final int id;
    private final AbstractTask task;
    private Node prev;
    private Node next;

    public Node(int id, AbstractTask task, Node prev, Node next) {
        this.id = id;
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    public int getId() {
        return this.id;
    }

    public AbstractTask getTask() {
        return this.task;
    }

    public Node getPrev() {
        return this.prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
