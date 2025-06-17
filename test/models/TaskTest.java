package models;

import org.junit.jupiter.api.Assertions;

public class TaskTest {
    @org.junit.jupiter.api.Test
    public void testEqualsWithSameId() {
        Task task1 = new Task(1, "Task1", "SomeText");
        Task task2 = new Task(1, "Task1", "SomeText");

        Assertions.assertEquals(task1, task2);
    }

    @org.junit.jupiter.api.Test
    public void testEqualsWithDifferentsId() {
        Task task1 = new Task(1, "Task1", "SomeText");
        Task task2 = new Task(2, "Task1", "SomeText");

        Assertions.assertNotEquals(task1, task2);
    }
}