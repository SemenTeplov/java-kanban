package Models;

public abstract class AbstractTask {
    private final int id;
    private String name;
    private String description;
    protected Status status;

    public AbstractTask(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.IN_PROGRESS;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return this.status;
    }

    public abstract void setStatus(Status status);
}
