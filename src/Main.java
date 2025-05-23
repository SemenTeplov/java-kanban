import managers.Managers;
import managers.TaskManager;
import models.AbstractTask;
import models.EpicTask;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            Managers.getDefault().createTask("Task" + i, "Something text");
            Managers.getDefault().createEpicTask("Epic" + i, "Something text");
        }

        for (int index : Managers.getDefault().getAllEpics().keySet()) {
            Managers.getDefault().createSubtask(index, "Subtask" + index, "Something text");
        }

        printAllTasks(Managers.getDefault());
    }

    private static void printAllTasks(TaskManager manager) {
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
        for (AbstractTask task : Managers.getDefaultHistory().getHistory()) {
            System.out.println(task);
        }
    }
}
