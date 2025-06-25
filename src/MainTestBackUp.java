import managers.FileBackedTaskManager;
import managers.TaskManager;
import models.AbstractTask;

public class MainTestBackUp {
    public static void main(String[] args) {
        String pathToBackup = "resourses/backup.csv";

        // Загрузка пустого файла Backup
        FileBackedTaskManager tManagerOrigin1 = new FileBackedTaskManager(pathToBackup);
        tManagerOrigin1.removeAll();

        System.out.println("Пустой список");

        FileBackedTaskManager tManagerTest1 = new FileBackedTaskManager(pathToBackup);
        printTasks(tManagerTest1);

        System.out.println("-".repeat(20));

        // Сохранение нескольких задач и загрузка
        FileBackedTaskManager tManagerOrigin2 = new FileBackedTaskManager(pathToBackup);
        tManagerOrigin2.removeAll();

        tManagerOrigin2.createEpicTask("Epic 1", "With subtasks");
        tManagerOrigin2.createEpicTask("Epic 2", "With subtasks");
        tManagerOrigin2.createEpicTask("Epic 3", "With subtasks");
        tManagerOrigin2.createEpicTask("Epic 4", "Without subtasks");

        for (int i = 1; i <= 3; i++) {
            for (int l = 1; l <= 3; l++) {
                tManagerOrigin2.createSubtask(i, "subtask " + l, "something text");
            }
        }

        System.out.println("Полный список");

        FileBackedTaskManager.loadFromFile(pathToBackup);
        FileBackedTaskManager tManagerTest2 = new FileBackedTaskManager(pathToBackup);
        printTasks(tManagerTest2);

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
