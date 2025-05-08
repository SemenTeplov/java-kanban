package Models;

public class Task extends AbstractTask {
    public Task(int id, String name, String description) {
        super(id, name, description);
    }

    public Task(int id, Task task) {
        super(id, task.getName(), task.getDescription());
        setStatus(task.getStatus());
    }

    @Override
    public void setStatus(Status status) {
        super.status = status;
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
        return String.format("%d %s %s", getId(), getName(), getDescription());
    }
}
