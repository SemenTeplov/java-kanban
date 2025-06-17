package models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EpicTaskTest {
    static EpicTask task = new EpicTask(1, "Epic", "Text");

    @BeforeEach
    void init() {
        task.addTask(new Subtask(2, task, "Subtask1", "Text"));
        task.addTask(new Subtask(3, task, "Subtask2", "Text"));
        task.addTask(new Subtask(4, task, "Subtask3", "Text"));
    }

    @Test
    void getTaskById() {
        Assertions.assertEquals("3 Subtask2 Text", task.getTaskById(3).toString());
    }

    @Test
    void getAllTasks() {
        Assertions.assertEquals(3, task.getAllTasks().size());
    }

    @Test
    void getCountTasks() {
        Assertions.assertEquals(3, task.getCountTasks());
    }

    @Test
    void removeTask() {
        task.removeTask(3);

        Assertions.assertThrows(IllegalArgumentException.class, () -> task.getTaskById(3));
    }

    @Test
    void changeStatusOnNew() {
        for (int index : task.getAllTasks().keySet()) {
            task.getTaskById(index).setStatus(Status.NEW);
        }

        task.addTask(new Subtask(2, task, "Subtask1", "Text"));

        Assertions.assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    void changeStatusOnIn_Progress() {
        for (int index : task.getAllTasks().keySet()) {
            task.getTaskById(index).setStatus(Status.IN_PROGRESS);
        }

        task.addTask(new Subtask(2, task, "Subtask1", "Text"));

        Assertions.assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void changeStatusOnDone() {
        for (int index : task.getAllTasks().keySet()) {
            task.getTaskById(index).setStatus(Status.DONE);
        }

        task.addTask(new Subtask(2, task, "Subtask1", "Text"));

        Assertions.assertEquals(Status.DONE, task.getStatus());
    }

    @Test
    void changeStatusEpicOnNew() {
        boolean isNew = true;
        task.setStatus(Status.NEW);

        for (int index : task.getAllTasks().keySet()) {
            if (task.getTaskById(index).getStatus() != Status.NEW) {
                isNew = false;
            }
        }

        Assertions.assertTrue(isNew);
    }

    @Test
    void changeStatusEpicOnInProgress() {
        boolean isNew = true;
        task.setStatus(Status.IN_PROGRESS);

        for (int index : task.getAllTasks().keySet()) {
            if (task.getTaskById(index).getStatus() != Status.IN_PROGRESS) {
                isNew = false;
            }
        }

        Assertions.assertTrue(isNew);
    }

    @Test
    void changeStatusEpicOnDone() {
        boolean isNew = true;
        task.setStatus(Status.DONE);

        for (int index : task.getAllTasks().keySet()) {
            if (task.getTaskById(index).getStatus() != Status.DONE) {
                isNew = false;
            }
        }

        Assertions.assertTrue(isNew);
    }
}