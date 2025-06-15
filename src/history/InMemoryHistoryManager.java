package history;

import models.AbstractTask;
import history.models.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodeHashMap = new HashMap<>();
    private Node first;
    private Node last;
    private Node current;
    private int size;

    @Override
    public void add(AbstractTask task) {
        this.current = this.last;

        if (this.current == null) {
            this.current = new Node(task.getId(), task, null, null);
            this.first = this.current;
        } else {
            if (nodeHashMap.containsKey(task.getId())) {
                remove(nodeHashMap.get(task.getId()));
                this.current = this.last;
            }

            this.current = new Node(task.getId(), task, this.current, null);

            if (this.first == null) {
                this.first = this.current;
            }

            if (this.current.getPrev() != null) {
                this.current.getPrev().setNext(this.current);
            }
        }

        this.last = this.current;
        this.nodeHashMap.put(this.current.getId(), this.current);
        this.size++;
    }

    @Override
    public void remove(AbstractTask task) {
        if (nodeHashMap.containsKey(task.getId())) {
            remove(nodeHashMap.get(task.getId()));
            nodeHashMap.remove(task.getId());
        }
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public List<AbstractTask> getHistory() {
        ArrayList<AbstractTask> list = new ArrayList<>();
        this.current = this.first;

        while (this.current != null) {
            list.add(this.current.getTask());
            this.current = this.current.getNext();
        }

        return list;
    }

    private void remove(Node node) {
        if (node != null && nodeHashMap.containsKey(node.getId())) {
            this.size--;

            if (node.getPrev() != null) {
                node.getPrev().setNext(node.getNext());

                if (node.getNext() == null) {
                    this.last = node.getPrev();
                }
            }

            if (node.getNext() != null) {
                node.getNext().setPrev(node.getPrev());

                if (node.getPrev() == null) {
                    this.first = node.getNext();
                }
            }
        }

        if (size == 0) {
            this.first = null;
            this.last = null;
        }
    }
}
