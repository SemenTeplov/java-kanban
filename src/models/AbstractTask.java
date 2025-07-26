package models;

import managers.utiles.DateTimeFormatPatterns;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class AbstractTask {
    private int id;
    private String name;
    private String description;
    private Duration duration;

    protected LocalDateTime startTime;
    protected Status status;
    protected Types type;


    public AbstractTask(int id, String name, String description, Types type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.IN_PROGRESS;
        this.type = type;
        this.duration = Duration.ZERO;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndTime() {
        return startTime.plus(duration).format(DateTimeFormatPatterns.format);
    }

    public void setDateTime(String start, String end) {
        if (!start.isBlank()) {
            this.startTime = LocalDateTime.parse(start, DateTimeFormatPatterns.format);
            this.duration = Duration.between(this.startTime, LocalDateTime.parse(end));
        }
    }

    public void setDateTime(String start) {
        this.startTime = LocalDateTime.parse(start, DateTimeFormatPatterns.format);
        this.duration = Duration.ZERO;
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

    public String getStartTime() {
        if (this.startTime != null) {
            return this.startTime.format(DateTimeFormatPatterns.format);
        }

        return "";
    }

    public void setStartTime(LocalDateTime time) {
        this.startTime = time;
    }

    public abstract void setStatus(Status status);

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    protected void definitionDurationIfStatusDone() {
        if (Status.DONE.equals(this.status)) {
            if (startTime == null) {
                startTime = LocalDateTime.now();
            }

            this.duration = Duration.between(startTime, LocalDateTime.now());
        }
    }
}
