package models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubtaskTest {
    static EpicTask task = new EpicTask(1, "Epic", "Text");
    static Subtask subtask = new Subtask(2, task, "Subtask1", "Text");

    @BeforeEach
    void init() {
        task.addTask(subtask);
    }

    @Test
    void getIdOwner() {
        Assertions.assertEquals(1, subtask.getIdOwner());
    }

    @Test
    void getOwner() {
        Assertions.assertEquals("1 Epic Text subtasks: 2 Subtask1 Text ", subtask.getOwner().toString());
    }
}