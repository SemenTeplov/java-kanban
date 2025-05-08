import Models.AbstractTask;
import Models.EpicTask;
import Models.Status;
import Models.Task;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Map<Integer, AbstractTask> tasks;
    private int currentId;

    public Manager() {
        tasks = new HashMap<>();
        currentId = 1;
    }

    public int getNewId() {
        return currentId++;
    }

    public Map<Integer, AbstractTask> getAll() {
        return tasks;
    }

    public void removeAll() {
        tasks.clear();
        currentId = 1;
    }

    public AbstractTask getById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        for (int i : tasks.keySet()) {
            if (tasks.get(i).getClass().getSimpleName().equals("EpicTask")) {
                EpicTask epicTask = (EpicTask)tasks.get(i);

                if (epicTask.getAllTasks().containsKey(id)) {
                    return epicTask.getTaskById(id);
                }
            }
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public void createTask(String name, String description) {
        tasks.put(currentId, new Task(currentId++, name, description));
    }

    public void createEpicTask(String name, String description) {
        tasks.put(currentId, new EpicTask(currentId++, name, description));
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

        for (int i : tasks.keySet()) {
            if (tasks.get(i).getClass().getSimpleName().equals("EpicTask")) {
                EpicTask epicTask = (EpicTask)tasks.get(i);

                epicTask.getAllTasks().remove(id);
            }
        }
    }

    public Map<Integer, AbstractTask> getTasksOfEpic(int id) {
        if (getById(id).getClass().getSimpleName().equals("EpicTask")) {
            EpicTask epic = (EpicTask)getById(id);

            return epic.getAllTasks();
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }
}
