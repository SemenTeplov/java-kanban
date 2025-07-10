import managers.Managers;
import managers.TaskManager;
import models.AbstractTask;
import models.EpicTask;
import models.Subtask;
import models.Task;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        TaskManager tManager = Managers.getDefault();
        Random rand = new Random();

        // Создание двух задач эпик с тремя подзадачами и один эпик без подзадач.
        tManager.createEpicTask(new EpicTask(0, "Epic 1", "With subtasks"));
        tManager.createEpicTask(new EpicTask(0, "Epic 2", "With subtasks"));
        tManager.createEpicTask(new EpicTask(0, "Epic 3", "With subtasks"));
        tManager.createEpicTask(new EpicTask(0, "Epic 4", "Without subtasks"));

        int index = tManager.getNewId();
        for (EpicTask et : tManager.getAllEpics().values()) {
            tManager.createSubtask(new Subtask(0, et, "subtask " + index++, "something text"));
        }

        // Проверка истории на отсутствие повторов
        for (AbstractTask task : tManager.getHistory().getHistory()) {
            System.out.println(task);
        }

        System.out.println();

        // Проверка на удаление задачи из истории
        tManager.getHistory().remove(tManager.getHistory().getHistory().get(2));

        for (AbstractTask task : tManager.getHistory().getHistory()) {
            System.out.println(task);
        }

        System.out.println();

        // Проверка удаления epica с его подзадачами
        tManager.removeById(2);

        for (AbstractTask task : tManager.getHistory().getHistory()) {
            System.out.println(task);
        }

        tManager.createTask(new Task(0, "Task 1", "something"));
        tManager.createTask(new Task(0, "Task 2", "something"));
        tManager.createTask(new Task(0, "Task 3", "something"));

        int day = 12;
        int month = 10;
        int year = 20;
        int hour = 10;
        int minute = 10;
        int secund = 20;

        for (AbstractTask task : tManager.getAllTasks().values()) {
            tManager.setDateTime(task.getId(), String.format("%d.%d.%d|%d:%d:%d", day, month, year, hour, minute += 5, secund));
        }
    }
}
