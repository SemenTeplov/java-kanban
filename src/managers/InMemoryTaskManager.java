package managers;

import history.HistoryManager;
import models.*;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, EpicTask> epicTasks;
    private final Map<Integer, Subtask> subTasks;
    private final HistoryManager hManager;
    private int currentId;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subTasks = new HashMap<>();
        hManager = Managers.getDefaultHistory();
        currentId = 1;
    }

    @Override
    public int getNewId() {
        return currentId++;
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        for (Task task : tasks.values()) {
            hManager.add(task);
        }

        return tasks;
    }

    @Override
    public Map<Integer, EpicTask> getAllEpics() {
        for (EpicTask epicTask : epicTasks.values()) {
            hManager.add(epicTask);
        }

        return epicTasks;
    }

    @Override
    public Map<Integer, Subtask> getAllSubtasks() {
        for (Subtask subTask : subTasks.values()) {
            hManager.add(subTask);
        }

        return subTasks;
    }

    @Override
    public HistoryManager getHistory() {
        return hManager;
    }

    @Override
    public void removeAll() {
        tasks.clear();
        epicTasks.clear();
        subTasks.clear();
        currentId = 1;
    }

    @Override
    public Task getById(int id) {
        if (tasks.containsKey(id)) {
            hManager.add(tasks.get(id));

            return tasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subTasks.containsKey(id)) {
            hManager.add(subTasks.get(id));

            return subTasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    @Override
    public EpicTask getEpicById(int id) {
        if (epicTasks.containsKey(id)) {
            hManager.add(epicTasks.get(id));

            return epicTasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    @Override
    public void createTask(String name, String description) {
        tasks.put(currentId, new Task(currentId++, name, description));
    }

    @Override
    public void createSubtask(int idOwner, String name, String description) {
        if (!epicTasks.containsKey(idOwner)) {
            throw new IllegalArgumentException("ID " + idOwner + " doesn't exist.");
        }

        Subtask task = new Subtask(currentId++, epicTasks.get(idOwner), name, description);

        subTasks.put(task.getId(), task);
        epicTasks.get(idOwner).addTask(task);
    }

    @Override
    public void createEpicTask(String name, String description) {
        epicTasks.put(currentId, new EpicTask(currentId++, name, description));
    }

    @Override
    public void updateTask(int id, String name, String description, Status status) {
        Task task = getById(id);

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    @Override
    public void updateEpicTask(int id, String name, String description, Status status) {
        EpicTask task = getEpicById(id);

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    @Override
    public void updateSubtask(int id, String name, String description, Status status) {
        Subtask task = getSubtaskById(id);

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    @Override
    public void removeById(int id) {
        if (tasks.containsKey(id)) {
            hManager.remove(tasks.get(id));
            tasks.remove(id);
        } else if (subTasks.containsKey(id)) {
            hManager.remove(subTasks.get(id));
            subTasks.get(id).getOwner().removeTask(id);
            subTasks.remove(id);
        } else if (epicTasks.containsKey(id)) {
            for (Subtask sub : epicTasks.get(id).getAllTasks().values()) {
                hManager.remove(sub);
                epicTasks.get(id).getTaskById(sub.getId());
                subTasks.remove(sub.getId());
            }

            hManager.remove(epicTasks.remove(id));
            epicTasks.remove(id);
        }
    }

    @Override
    public Map<Integer, Subtask> getTasksOfEpic(int id) {
         if (epicTasks.containsKey(id)) {
            return epicTasks.get(id).getAllTasks();
         }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }
}
