package models;

import managers.utiles.DateTimeFormatPatterns;

public class Task extends AbstractTask {
    public Task(int id, String name, String description) {
        super(id, name, description, Types.TASK);
    }

    public Task(int id, Task task) {
        super(id, task.getName(), task.getDescription(), Types.TASK);
        setStatus(task.getStatus());
    }

    @Override
    public void setStatus(Status status) {
        super.status = status;
        super.definitionDurationIfStatusDone();
    }

    @Override
    public int hashCode() {
        if (getName() == null) {
            setName("");
        }

        if (getDescription() == null) {
            setDescription("");
        }

        return 32 * (getId() + getName().hashCode() + getDescription().hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass().getName().equals(this.getClass().getName())) {
            Task task = (Task)obj;

            if (this.hashCode() == task.hashCode() &&
                getId() == task.getId() &&
                getName().equals(task.getName()) &&
                getDescription().equals(task.getDescription())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s, %s",
                getId(),
                super.startTime == null ? " " : super.startTime.format(DateTimeFormatPatterns.format),
                super.startTime == null ? " " : super.getEndTime().format(DateTimeFormatPatterns.format),
                type,
                getName(),
                status,
                getDescription());
    }
}
