import managers.FileBackedTaskManager;
import managers.TaskManager;
import models.AbstractTask;

public class MainTestBackUp {
    public static void main(String[] args) {
        // Загрузка пустого файла Backup
        FileBackedTaskManager tManager_origin1 = new FileBackedTaskManager();
        tManager_origin1.removeAll();

        System.out.println("Пустой список");

        FileBackedTaskManager.loadFromFile("resourses/backup.csv");
        FileBackedTaskManager tManager_test1 = new FileBackedTaskManager();
        printTasks(tManager_test1);

        System.out.println("-".repeat(20));

        // Сохранение нескольких задач и загрузка
        FileBackedTaskManager tManager_origin2 = new FileBackedTaskManager();
        tManager_origin2.removeAll();

        tManager_origin2.createEpicTask("Epic 1", "With subtasks");
        tManager_origin2.createEpicTask("Epic 2", "With subtasks");
        tManager_origin2.createEpicTask("Epic 3", "With subtasks");
        tManager_origin2.createEpicTask("Epic 4", "Without subtasks");

        for (int i = 1; i <= 3; i++) {
            for (int l = 1; l <= 3; l++) {
                tManager_origin2.createSubtask(i, "subtask " + l, "something text");
            }
        }

        System.out.println("Полный список");

        FileBackedTaskManager.loadFromFile("resourses/backup.csv");
        FileBackedTaskManager tManager_test2 = new FileBackedTaskManager();
        printTasks(tManager_test2);

        System.out.println("-".repeat(20));

    }

    public static void printTasks(TaskManager tManager) {
        for (AbstractTask task : tManager.getAllTasks().values()) {
            System.out.println(task);
        }

        for (AbstractTask task : tManager.getAllEpics().values()) {
            System.out.println(task);
        }

        for (AbstractTask task : tManager.getAllSubtasks().values()) {
            System.out.println(task);
        }
    }
}
