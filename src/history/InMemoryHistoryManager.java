package history;

import models.AbstractTask;
import history.models.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final Table table;

    private Node first;
    private Node last;
    private Node current;
    private int size;

    {
        table= new Table();
    }

    @Override
    public void add(AbstractTask task) {
        this.current = this.last;

        if (this.current == null) {
            this.current = new Node(task.getId(), task, null, null);
            this.first = this.current;
        } else {
            if (table.getNode(task.getId()) != null) {
                remove(table.getNode(task.getId()));
                this.current = this.last;
            }

            this.current = new Node(task.getId(), task, this.current, null);
            this.current.getPrev().setNext(this.current);
        }

        this.last = this.current;
        this.table.setNode(this.current);
        this.size++;
    }

    @Override
    public void remove(Node node) {
        Node tmpNode = table.removeNode(node);

        if (tmpNode != null) {
            this.size--;

            if (tmpNode.getPrev() != null) {
                tmpNode.getPrev().setNext(tmpNode.getNext());

                if (tmpNode.getNext() == null) {
                    this.last = tmpNode.getPrev();
                }
            }

            if (tmpNode.getNext() != null) {
                tmpNode.getNext().setPrev(tmpNode.getPrev());

                if (tmpNode.getPrev() == null) {
                    this.first = tmpNode.getNext();
                }
            }
        }
    }

    @Override
    public void remove(AbstractTask task) {
        remove(new Node(task.getId(), task, null, null));
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

    static class Table {
        private final int ROW = 10;
        private final int COL = 10;
        private final Node[][][] table;

        {
            int DEEP = 1;
            table = new Node[ROW][COL][DEEP];
        }

        public Node getNode(int key) {
            for (Node node : table[key % ROW][key % ROW / COL]) {
                if (node != null && node.getId() == key) {
                    return node;
                }
            }

            return null;
        }

        public void setNode(Node node) {
            Node[] arrNodes = table[node.getId() % ROW][node.getId() % ROW / COL];
            int prevLength = arrNodes.length;

            for (int i = 0; i < prevLength; i++) {
                if (arrNodes[i] == null) {
                    arrNodes[i] = node;
                    return;
                }
            }

            arrNodes = Arrays.copyOf(arrNodes, prevLength * 2);
            arrNodes[prevLength] = node;
        }

        public Node removeNode(Node node) {
            Node[] nodes = table[node.getId() % ROW][node.getId() % ROW / COL];
            Node tmpNode;

            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] != null && nodes[i].getId() == node.getId()) {
                    tmpNode = nodes[i];
                    nodes[i] = null;

                    return tmpNode;
                }
            }

            return null;
        }
    }
}
