package models;

import managers.utiles.DateTimeFormatPatterns;

import java.util.HashMap;
import java.util.Map;

public class EpicTask extends AbstractTask {
    private final Map<Integer, Subtask> tasks;

    public EpicTask(int id, String name, String description) {
        super(id, name, description, Types.EPIC);
        tasks = new HashMap<>();
    }

    public EpicTask(int id, EpicTask task) {
        super(id, task.getName(), task.getDescription(), Types.EPIC);
        setStatus(task.getStatus());
        tasks = new HashMap<>();
    }

    public void addTask(Subtask task) {
        tasks.put(task.getId(), task);
    }

    public Subtask getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        throw new IllegalArgumentException("ID " + id + " doesn't exist.");
    }

    public Map<Integer, Subtask> getAllTasks() {
        return tasks;
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public int getCountTasks() {
        return tasks.size();
    }

    @Override
    public void setStatus(Status status) {
        if (status == Status.NEW || status == Status.DONE) {
            tasks.keySet().forEach(item -> tasks.get(item).setStatus(status));
        }

        super.status = status;
        super.definitionDurationIfStatusDone();
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode += tasks.keySet().stream().reduce(0, Integer::sum);

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
        return String.format("%d, %s, %s, %s, %s, %s, %s",
                getId(),
                super.startTime == null ? " " : super.getStartTime(),
                super.startTime == null ? " " : super.getEndTime(),
                type,
                getName(),
                status,
                getDescription());
    }

    public void checkStatus() {
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
            super.definitionDurationIfStatusDone();
        } else {
            super.status = Status.IN_PROGRESS;
        }
    }
}
