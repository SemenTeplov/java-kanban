package models;

public class Subtask extends AbstractTask {
    private final EpicTask owner;

    public Subtask(int id, EpicTask owner, String name, String description) {
        super(id, name, description, Types.SUBTASK);
        this.owner = owner;
        owner.checkStatus();
    }

    public Subtask(int id, Subtask task) {
        super(id, task.getName(), task.getDescription(), Types.SUBTASK);
        setStatus(task.getStatus());
        this.owner = task.getOwner();
        owner.checkStatus();
    }

    public int getIdOwner() {
        return this.owner.getId();
    }

    public EpicTask getOwner() {
        return this.owner;
    }

    @Override
    public void setStatus(Status status) {
        super.status = status;
        owner.checkStatus();
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
        return String.format("%d, %s, %s, %s, %s, %d", getId(), type, getName(), status, getDescription(), getIdOwner());
    }
}
