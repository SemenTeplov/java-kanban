package managers;

import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager manger = new InMemoryTaskManager();

    @BeforeAll
    static void init() {
        for (int i = 1; i < 6; i++) {
            manger.createTask(new Task(i, "Task" + i, "Something text"));
            manger.createEpicTask(new EpicTask(i,"Epic" + i, "Something text"));
        }

        for (EpicTask epic : manger.getAllEpics().values()) {
            manger.createSubtask(new Subtask(0, epic, "Subtask", "Something text"));
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
        Task tmp = new Task(1, "ChangedTask", "ChangedText");
        tmp.setStatus(Status.NEW);

        manger.updateTask(tmp);

        Assertions.assertEquals("1, 12.10.20|10:15:20, 12.10.20|10:15:20, TASK, ChangedTask, NEW, ChangedText", manger.getById(1).toString());
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
        Subtask tmp = new Subtask(13, manger.getEpicById(6) ,"ChangedSubTask", "ChangedText");
        tmp.setStatus(Status.NEW);

        manger.updateSubtask(tmp);

        Assertions.assertEquals("13,  ,  , SUBTASK, ChangedSubTask, NEW, ChangedText, 6",
                manger.getSubtaskById(13).toString());
    }

    @Test
    void getTasksOfEpic() {
        Assertions.assertEquals(1, manger.getTasksOfEpic(2).size());
    }

    @Test
    void checkingTimeIntervals() {
        int day = 12;
        int month = 10;
        int year = 20;
        int hour = 10;
        int minute = 10;
        int secund = 20;

        for (AbstractTask task : manger.getAllTasks().values()) {
            manger.setDateTime(task.getId(), String.format("%d.%d.%d|%d:%d:%d", day, month, year, hour, minute += 5, secund));
        }

        Assertions.assertEquals(3, manger.getPrioritizedTasks().size());
    }
}