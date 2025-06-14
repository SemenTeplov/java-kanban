import managers.Managers;
import managers.TaskManager;
import models.AbstractTask;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        TaskManager tManager = Managers.getDefault();
        Random rand = new Random();

        // Создание двух задач эпик с тремя подзадачами и один эпик без подзадач.
        tManager.createEpicTask("Epic 1", "With subtasks");
        tManager.createEpicTask("Epic 2", "With subtasks");
        tManager.createEpicTask("Epic 3", "With subtasks");
        tManager.createEpicTask("Epic 4", "Without subtasks");

        for (int i = 1; i <= 3; i++) {
            for (int l = 1; l <= 3; l++) {
                tManager.createSubtask(i, "subtask " + l, "something text");
            }
        }

        // Запрос задач
        for (int i = 0; i < 5; i++) {
            System.out.println(tManager.getEpicById(rand.nextInt(1, 5)));
            System.out.println(tManager.getSubtaskById(rand.nextInt(5, 13)));
        }
        System.out.println();

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
    }
}
