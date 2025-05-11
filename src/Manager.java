import models.AbstractTask;
import models.EpicTask;
import models.Status;
import models.Task;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, EpicTask> epicTasks;
    private int currentId;

    public Manager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        currentId = 1;
    }

    public int getNewId() {
        return currentId++;
    }

    public Map<Integer, Task> getAll() {
        return tasks;
    }

    public void removeAll() {
        tasks.clear();
        epicTasks.clear();
        currentId = 1;
    }

    public Task getById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        for (int i : epicTasks.keySet()) {
            if (epicTasks.get(i).getAllTasks().containsKey(id)) {
                return epicTasks.get(i).getTaskById(id);
            }
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public EpicTask getEpicById(int id) {
        if (epicTasks.containsKey(id)) {
            return epicTasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public void createTask(String name, String description) {
        tasks.put(currentId, new Task(currentId++, name, description));
    }

    public void createEpicTask(String name, String description) {
        epicTasks.put(currentId, new EpicTask(currentId++, name, description));
    }

    public void updateTask(int id, String name, String description, Status status) {
        getById(id).setName(name);
        getById(id).setDescription(description);
        getById(id).setStatus(status);
    }

    public void updateTask(int id, String name, String description) {
        getById(id).setName(name);
        getById(id).setDescription(description);
    }

    public void updateTask(int id, String name) {
        getById(id).setName(name);
    }

    public void removeById(int id) {
        tasks.remove(id);
        epicTasks.remove(id);

        for (int i : epicTasks.keySet()) {
            epicTasks.get(i).getAllTasks().remove(id);
        }
    }

    public Map<Integer, Task> getTasksOfEpic(int id) {
         if (epicTasks.containsKey(id)) {
            return epicTasks.get(id).getAllTasks();
         }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }
}
