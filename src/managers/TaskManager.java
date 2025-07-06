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

    void createTask(String name, String description);

    void createSubtask(int idOwner, String name, String description);

    void createEpicTask(String name, String description);

    void updateTask(int id, String name, String description, Status status);

    void updateEpicTask(int id, String name, String description, Status status);

    void updateSubtask(int id, String name, String description, Status status);

    void setDateTime(int id, String start);

    void removeById(int id);

    Map<Integer, Subtask> getTasksOfEpic(int id);

    Set<AbstractTask> getPrioritizedTasks();
}
