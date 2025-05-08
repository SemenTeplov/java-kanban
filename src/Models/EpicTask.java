package Models;

import java.util.HashMap;
import java.util.Map;

public class EpicTask extends AbstractTask {
    private final Map<Integer, AbstractTask> tasks;

    public EpicTask(int id, String name, String description) {
        super(id, name, description);
        tasks = new HashMap<>();
    }

    public EpicTask(int id, EpicTask task) {
        super(id, task.getName(), task.getDescription());
        setStatus(task.getStatus());
        tasks = new HashMap<>();
    }

    public void addTask(int id, String name, String description) {
        tasks.put(id, new Task(id, name, description));
        checkStatus();
    }

    public AbstractTask getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public Map<Integer, AbstractTask> getAllTasks() {
        return tasks;
    }

    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            checkStatus();
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public int getCountTasks() {
        return tasks.size();
    }

    @Override
    public void setStatus(Status status) {
        if (status == Status.NEW || status == Status.DONE) {
            for (int i : tasks.keySet()) {
                tasks.get(i).setStatus(status);
            }
        }

        super.status = status;
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();

        for (int i : tasks.keySet()) {
            hashCode += tasks.get(i).hashCode();
        }

        return 32 * hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass().getName().equals(this.getClass().getName())) {
            Task task = (Task)obj;

            return this.hashCode() == task.hashCode() &&
                    getId() == task.getId() &&
                    getName().equals(task.getName()) &&
                    getDescription().equals(task.getDescription());
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder printStr =
                new StringBuilder(String.format("%d %s %s", getId(), getName(), getDescription()));

        if (!tasks.isEmpty()) {
            printStr.append(" subtasks: ");

            for (int i : tasks.keySet()) {
                printStr.append(tasks.get(i).toString()).append(" ");
            }
        }

        return printStr.toString();
    }

    private void checkStatus() {
        int countNew = 0;
        int countDone = 0;

        for (int i : tasks.keySet()) {
            if (tasks.get(i).getStatus() == Status.NEW) {
                countNew++;
            } else if (tasks.get(i).getStatus() == Status.DONE) {
                countDone++;
            }
        }

        if (countNew == tasks.size()) {
            super.status = Status.NEW;
        } else if (countDone == tasks.size()) {
            super.status = Status.DONE;
        } else {
            super.status = Status.IN_PROGRESS;
        }
    }
}
