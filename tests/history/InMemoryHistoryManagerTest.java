package history;

import models.EpicTask;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {
    static InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @BeforeAll
    static void init() {
        manager.add(new Task(1, "Task", "Text"));
        EpicTask epic = new EpicTask(2, "EpicTask", "Text");
        manager.add(epic);
        manager.add(new Task(4, "Task", "Text"));
        manager.add(new Subtask(3, epic, "SubTask", "Text"));
    }

    @Test
    void addEsistsInBegine() {
        String expected = manager.getHistory().toString();

        manager.add(new Task(1, "Task", "Text"));

        String actual = manager.getHistory().toString();

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void addEsistsInMiddle() {
        String expected = manager.getHistory().toString();

        manager.add(new Task(5, "Task5", "Text"));
        manager.add(new Task(4, "Task", "Text"));

        String actual = manager.getHistory().toString();

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void addEsistsInEnd() {
        manager.add(new Task(6, "Task6", "Text"));

        String expected = manager.getHistory().toString();

        manager.add(new Task(6, "Task6", "Text"));

        String actual = manager.getHistory().toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getHistory() {
        Assertions.assertNotNull(manager.getHistory());
    }
}