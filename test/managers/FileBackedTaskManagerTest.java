package managers;

import models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileBackedTaskManagerTest {
    static InMemoryTaskManager manger = new InMemoryTaskManager();

    @BeforeEach
    void init() {
        manger.removeAll();

        for (int i = 1; i < 6; i++) {
            manger.createTask("Task" + i, "Something text");
            manger.createEpicTask("Epic" + i, "Something text");
        }

        for (int index : manger.getAllEpics().keySet()) {
            manger.createSubtask(index, "Subtask" + index, "Something text");
        }
    }

    @Test
    void UpdateTask() {
        manger.updateTask(1, "ChangedTask", "ChangedText", Status.NEW);

        Assertions.assertEquals("1,  ,  , TASK, ChangedTask, NEW, ChangedText", manger.getById(1).toString());
    }

    @Test
    void updateEpicTask() {
        manger.updateEpicTask(2, "ChangedEpicTask", "ChangedText", Status.NEW);

        Assertions.assertEquals("2,  ,  , EPIC, ChangedEpicTask, NEW, ChangedText", manger.getEpicById(2).toString());
    }

    @Test
    void updateSubtask() {
        manger.updateSubtask(13, "ChangedSubTask", "ChangedText", Status.NEW);

        Assertions.assertEquals("13,  ,  , SUBTASK, ChangedSubTask, NEW, ChangedText, 6",
                manger.getSubtaskById(13).toString());
    }
}