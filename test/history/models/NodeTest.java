package history.models;

import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NodeTest {
    static Node node;
    static Node first;

    @BeforeAll
    static void init() {
        node = new Node(0, new Task(0, "Task", "Something description"), null, null);
        first = node;
        node.setPrev(node);

        for (int i = 1; i <= 3; i++) {
            node = new Node(i, new Task(i, "Task" + i, "Something description"), node, null);
            node.getPrev().setNext(node);
        }
    }

    @Test
    void getId() {
        String expected = "0 1 2 3 ";
        StringBuilder actual = new StringBuilder();
        node = first;

        while (node != null) {
            actual.append(node.getId()).append(" ");
            node = node.getNext();
        }

        Assertions.assertEquals(expected, actual.toString());
    }

    @Test
    void getTask() {
        String expected = "0,  ,  , TASK, Task, IN_PROGRESS, Something description";

        Assertions.assertEquals(expected, first.getTask().toString());
    }

    @Test
    void getPrev() {
        node = first.getNext();

        String actual = "current node = " + node.getId() + " " + node.getTask().toString() + ", ";
        actual += "prev node = " + node.getPrev().getId() + " " + node.getPrev().getTask().toString();
        String expected = "current node = 1 1,  ,  , TASK, Task1, IN_PROGRESS, Something description, prev node = 0 0," +
                "  ,  , TASK, Task, IN_PROGRESS, Something description";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getNext() {
        node = first;

        String actual = "current node = " + node.getId() + " " + node.getTask().toString() + ", ";
        actual += "prev node = " + node.getNext().getId() + " " + node.getNext().getTask().toString();
        String expected = "current node = 0 0,  ,  , TASK, Task, IN_PROGRESS, Something description, prev node = 1 1,  " +
                ",  , TASK, Task1, IN_PROGRESS, Something description";

        Assertions.assertEquals(expected, actual);
    }
}
