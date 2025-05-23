package managers;

import models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager manger = new InMemoryTaskManager();

    @BeforeAll
    static void init() {
        for (int i = 1; i < 6; i++) {
            manger.createTask("Task" + i, "Something text");
            manger.createEpicTask("Epic" + i, "Something text");
        }

        for (int index : manger.getAllEpics().keySet()) {
            manger.createSubtask(index, "Subtask" + index, "Something text");
        }
    }

    @Test
    void getById() {
        Assertions.assertNotNull(manger.getById(3));
    }

    @Test
    void getSubtaskById() {
        Assertions.assertNotNull(manger.getSubtaskById(13));
    }

    @Test
    void getEpicById() {
        Assertions.assertNotNull(manger.getEpicById(4));
    }

    @Test
    void getAllTasks() {
        Assertions.assertEquals(5, manger.getAllTasks().size());
    }

    @Test
    void getAllEpics() {
        Assertions.assertEquals(5, manger.getAllEpics().size());
    }

    @Test
    void getAllSubtasks() {
        Assertions.assertEquals(5, manger.getAllSubtasks().size());
    }

    @Test
    void updateTask() {
        manger.updateTask(1, "ChangedTask", "ChangedText", Status.NEW);

        Assertions.assertEquals("1 ChangedTask ChangedText", manger.getById(1).toString());
    }

    @Test
    void updateEpicTask() {
        manger.updateEpicTask(2, "ChangedEpicTask", "ChangedText", Status.NEW);

        Assertions.assertEquals("2 ChangedEpicTask ChangedText subtasks: 11 Subtask2 Something text ", manger.getEpicById(2).toString());
    }

    @Test
    void updateSubtask() {
        manger.updateSubtask(13, "ChangedSubTask", "ChangedText", Status.NEW);

        Assertions.assertEquals("13 ChangedSubTask ChangedText", manger.getSubtaskById(13).toString());
    }

    @Test
    void getTasksOfEpic() {
        Assertions.assertEquals(1, manger.getTasksOfEpic(2).size());
    }

//    @Test
//    void removeById() {
//        manger.removeById(13);
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> manger.getSubtaskById(13).toString());
//    }
//
//    @Test
//    void removeAll() {
//        manger.removeAll();
//
//        Assertions.assertTrue(manger.getAllTasks().isEmpty()
//                && manger.getAllEpics().isEmpty()
//                && manger.getAllSubtasks().isEmpty());
//    }
}