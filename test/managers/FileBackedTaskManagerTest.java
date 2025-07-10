package managers;

import models.EpicTask;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileBackedTaskManagerTest {
    static InMemoryTaskManager manger = new InMemoryTaskManager();

    @BeforeEach
    void init() {
        manger.removeAll();

        for (int i = 1; i < 6; i++) {
            manger.createTask(new Task(i,"Task" + i, "Something text"));
            manger.createEpicTask(new EpicTask(i,"Epic" + i, "Something text"));
        }

        for (EpicTask epic : manger.getAllEpics().values()) {
            manger.createSubtask(new Subtask(0, epic, "Subtask", "Something text"));
        }
    }

    @Test
    void UpdateTask() {
        Task tmp = new Task(1, "ChangedTask", "ChangedText");
        tmp.setStatus(Status.NEW);

        manger.updateTask(tmp);

        Assertions.assertEquals("1,  ,  , TASK, ChangedTask, NEW, ChangedText", manger.getById(1).toString());
    }

    @Test
    void updateEpicTask() {
        EpicTask tmp = new EpicTask(2, "ChangedEpicTask", "ChangedText");
        tmp.setStatus(Status.NEW);

        manger.updateEpicTask(tmp);

        Assertions.assertEquals("2,  ,  , EPIC, ChangedEpicTask, NEW, ChangedText", manger.getEpicById(2).toString());
    }

    @Test
    void updateSubtask() {
        Subtask tmp = new Subtask(13, manger.getEpicById(10), "ChangedEpicTask", "ChangedText");
        tmp.setStatus(Status.NEW);

        manger.updateSubtask(tmp);

        Assertions.assertEquals("13,  ,  , SUBTASK, ChangedEpicTask, NEW, ChangedText, 6",
                manger.getSubtaskById(13).toString());
    }
}