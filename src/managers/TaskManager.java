package managers;

import history.HistoryManager;
import models.*;

import java.util.Map;
import java.util.Set;

public interface TaskManager {
    int getNewId();

    Map<Integer, Task> getAllTasks();

    Map<Integer, EpicTask> getAllEpics();

    Map<Integer, Subtask> getAllSubtasks();

    void removeAll();

    Task getById(int id);

    Subtask getSubtaskById(int id);

    EpicTask getEpicById(int id);

    HistoryManager getHistory();

    void createTask(Task task);

    void createSubtask(Subtask subtask);

    void createEpicTask(EpicTask epicTask);

    void updateTask(Task task);

    void updateEpicTask(EpicTask task);

    void updateSubtask(Subtask task);

    void setDateTime(int id, String start);

    void removeById(int id);

    Map<Integer, Subtask> getTasksOfEpic(int id);

    Set<AbstractTask> getPrioritizedTasks();
}
