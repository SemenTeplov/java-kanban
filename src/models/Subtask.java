package models;

public class Subtask extends AbstractTask{
    private int idOwner;

    public Subtask(int id, int idOwner, String name, String description) {
        super(id, name, description);
        this.idOwner = idOwner;
    }

    public Subtask(int id, Subtask task) {
        super(id, task.getName(), task.getDescription());
        setStatus(task.getStatus());
        this.idOwner = task.getIdOwner();
    }

    public int getIdOwner() {
        return this.idOwner;
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
