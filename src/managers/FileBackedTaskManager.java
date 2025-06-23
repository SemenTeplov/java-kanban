package managers;

import managers.exceptions.ManagerLoadException;
import managers.exceptions.ManagerSaveException;
import models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private static String pathToFile = "resourses/backup.csv";
    private static final List<String[]> list = new LinkedList<>();

    static {
        loadFromFile(pathToFile);
    }

    public FileBackedTaskManager() {
        super();

        if (!list.isEmpty()) {
            for (String[] arr : list) {
                int bId = Integer.parseInt(arr[0]);
                String bType = arr[1];
                String bName = arr[2];
                String bStatus = arr[3];
                String bDescription = arr[4];

                if (bType.equals(Types.TASK.toString())) {
                    Task task = new Task(bId, bName, bDescription);

                    setBackedTaskStatus(task, bStatus);
                    tasks.put(bId, task);
                } else if (bType.equals(Types.EPIC.toString())) {
                    EpicTask epic = new EpicTask(bId, bName, bDescription);

                    setBackedTaskStatus(epic, bStatus);
                    epicTasks.put(bId, epic);
                } else if (bType.equals(Types.SUBTASK.toString())) {
                    int bIdEpic = Integer.parseInt(arr[5]);
                    Subtask sub = new Subtask(bId,
                            epicTasks.get(bIdEpic), bName, bDescription);

                    setBackedTaskStatus(sub, bStatus);
                    subTasks.put(sub.getId(), sub);
                    epicTasks.get(bIdEpic).addTask(sub);
                }

                currentId = bId + 1;
            }
        }
    }

    @Override
    public void createTask(String name, String description) {
        super.createTask(name, description);
        save();
    }

    @Override
    public void createSubtask(int idOwner, String name, String description) {
        super.createSubtask(idOwner, name, description);
        save();
    }

    @Override
    public void createEpicTask(String name, String description) {
        super.createEpicTask(name, description);
        save();
    }

    @Override
    public void updateTask(int id, String name, String description, Status status) {
        super.updateTask(id, name, description, status);
        save();
    }

    @Override
    public void updateEpicTask(int id, String name, String description, Status status) {
        super.updateEpicTask(id, name, description, status);
        save();
    }

    @Override
    public void updateSubtask(int id, String name, String description, Status status) {
        super.updateSubtask(id, name, description, status);
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }

    @Override
    public void removeById(int id) {
        super.removeById(id);
        save();
    }

    private void save() {
        String path = pathToFile;
        List<AbstractTask> list = getListAllTasks();

        try {
            StringBuilder strings = new StringBuilder("id,type,name,status,description,epic\n");

            for (AbstractTask task : list) {
                strings.append(task.toString()).append("\n");
            }

            Files.writeString(Path.of(path), strings, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public static void loadFromFile(String path) {
        try {
            List<String> listStrings = Files.readAllLines(Path.of(path));
            list.clear();

            for (int i = 1; i < listStrings.size(); i++) {
                list.add(listStrings.get(i).split(","));
            }
        } catch (IOException e) {
            throw new ManagerLoadException(e.getMessage());
        }
    }

    public static void setPathToFile(String path) {
        pathToFile = path;
    }

    private List<AbstractTask> getListAllTasks() {
        List<AbstractTask> list = new LinkedList<>(super.getAllTasks().values());
        list.addAll(super.getAllEpics().values());
        list.addAll(super.getAllSubtasks().values());

        list.sort((a, b) -> a.getId() - b.getId());

        return list;
    }

    private void setBackedTaskStatus(AbstractTask task, String status) {
        switch (status) {
            case "NEW" -> task.setStatus(Status.NEW);
            case "IN_PROGRESS" -> task.setStatus(Status.IN_PROGRESS);
            case "DONE" -> task.setStatus(Status.DONE);
        }
    }
}
