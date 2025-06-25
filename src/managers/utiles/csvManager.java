package managers.utiles;

import managers.TaskManager;
import managers.exceptions.ManagerLoadException;
import managers.exceptions.ManagerSaveException;
import models.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class csvManager {
    public static void save(String pathToFile, TaskManager manager) {
        List<AbstractTask> list = getListAllTasks(manager);
        Path path = Path.of(pathToFile);

        try {
            Files.delete(path);
            Files.createFile(path);
            StringBuilder strings = new StringBuilder("id,type,name,status,description,epic\n");

            for (AbstractTask task : list) {
                strings.append(toCSV(task)).append("\n");
            }

            Files.writeString(path, strings, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public static List<String> loadFromFile(String str) {
        try {
            Path path = Path.of(str);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new ManagerLoadException(e.getMessage());
        }
    }

    private static List<AbstractTask> getListAllTasks(TaskManager manager) {
        List<AbstractTask> list = new LinkedList<>(manager.getAllTasks().values());
        list.addAll(manager.getAllEpics().values());
        list.addAll(manager.getAllSubtasks().values());

        list.sort((a, b) -> a.getId() - b.getId());

        return list;
    }

    private static String toCSV(AbstractTask task) {
        String[] strings = task.toString().split(", ");

        return String.join(",", strings);
    }
}
