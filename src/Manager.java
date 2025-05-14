import models.*;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, EpicTask> epicTasks;
    private final Map<Integer, Subtask> subTasks;
    private int currentId;

    public Manager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subTasks = new HashMap<>();
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
        subTasks.clear();
        currentId = 1;
    }

    public Task getById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public Subtask getSubtaskById(int id) {
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
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

    public void createSubtask(int idOwner, String name, String description) {
        if (!epicTasks.containsKey(idOwner)) {
            throw new IllegalArgumentException("ID " + idOwner + " doesn't exist.");
        }

        Subtask task = new Subtask(currentId++, idOwner, name, description);

        subTasks.put(currentId, task);
        epicTasks.get(idOwner).addTask(task);
    }

    public void createEpicTask(String name, String description) {
        epicTasks.put(currentId, new EpicTask(currentId++, name, description));
    }

    public void updateTask(int id, String name, String description, Status status) {
        Task task = getById(id);

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    public void updateEpicTask(int id, String name, String description, Status status) {
        EpicTask task = getEpicById(id);

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    public void updateSubtask(int id, String name, String description, Status status) {
        Subtask task = getSubtaskById(id);

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    public void removeById(int id) {
        tasks.remove(id);
        epicTasks.remove(id);
        subTasks.remove(id);

        for (int i : epicTasks.keySet()) {
            epicTasks.get(i).getAllTasks().remove(id);
        }
    }

    public Map<Integer, Subtask> getTasksOfEpic(int id) {
         if (epicTasks.containsKey(id)) {
            return epicTasks.get(id).getAllTasks();
         }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }
}
