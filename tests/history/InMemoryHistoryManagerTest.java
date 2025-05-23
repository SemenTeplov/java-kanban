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
        manager.add(new Subtask(3, epic, "SubTask", "Text"));
    }

    @Test
    void add() {
        int begin = manager.getHistory().size();

        manager.add(new Task(4, "Task", "Text"));

        Assertions.assertNotEquals(begin, manager.getHistory().size());
    }

    @Test
    void getHistory() {
        Assertions.assertNotNull(manager.getHistory());
    }
}