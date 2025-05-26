import history.HistoryManager;
import managers.Managers;
import managers.TaskManager;
import models.AbstractTask;
import models.EpicTask;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager tManager = Managers.getDefault();

        for (int i = 1; i < 6; i++) {
            tManager.createTask("Task" + i, "Something text");
            tManager.createEpicTask("Epic" + i, "Something text");
        }

        for (int index : tManager.getAllEpics().keySet()) {
            tManager.createSubtask(index, "Subtask" + index, "Something text");
        }

        printAllTasks(tManager, tManager.getHistory());
    }

    private static void printAllTasks(TaskManager manager, HistoryManager hManager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks().values()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (EpicTask epic : manager.getAllEpics().values()) {
            System.out.println(epic);

            for (int index : epic.getAllTasks().keySet()) {
                System.out.println("--> " + epic.getTaskById(index));
            }
        }
        System.out.println("Подзадачи:");
        for (Subtask subtask : manager.getAllSubtasks().values()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (AbstractTask task : hManager.getHistory()) {
            System.out.println(task);
        }
    }
}
