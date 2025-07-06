package managers;

import managers.utiles.CSVManager;
import models.*;

import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final String pathToFile;

    public FileBackedTaskManager(String path) {
        super();
        pathToFile = path;
        getData();
    }

    @Override
    public void createTask(String name, String description) {
        super.createTask(name, description);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void createSubtask(int idOwner, String name, String description) {
        super.createSubtask(idOwner, name, description);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void createEpicTask(String name, String description) {
        super.createEpicTask(name, description);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void updateTask(int id, String name, String description, Status status) {
        super.updateTask(id, name, description, status);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void updateEpicTask(int id, String name, String description, Status status) {
        super.updateEpicTask(id, name, description, status);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void updateSubtask(int id, String name, String description, Status status) {
        super.updateSubtask(id, name, description, status);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void setDateTime(int id, String start) {
        super.setDateTime(id, start);
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void removeAll() {
        super.removeAll();
        CSVManager.save(pathToFile, this);
    }

    @Override
    public void removeById(int id) {
        super.removeById(id);
        CSVManager.save(pathToFile, this);
    }

    private void getData() {
        List<String> listStrings = CSVManager.loadFromFile(pathToFile);

        if (!listStrings.isEmpty()) {
            for (String str : listStrings.subList(1, listStrings.size())) {
                String[] arr = str.split(",");

                int bId = Integer.parseInt(arr[0]);
                String bType = arr[5];
                String bName = arr[6];
                String bStatus = arr[3];
                String bDescription = arr[4];
                String start = arr[1];
                String end = arr[2];

                if (bType.equals(Types.TASK.toString())) {
                    Task task = new Task(bId, bName, bDescription);
                    task.setDateTime(start, end);

                    setBackedTaskStatus(task, bStatus);
                    tasks.put(bId, task);
                } else if (bType.equals(Types.EPIC.toString())) {
                    EpicTask epic = new EpicTask(bId, bName, bDescription);
                    epic.setDateTime(start, end);

                    setBackedTaskStatus(epic, bStatus);
                    epicTasks.put(bId, epic);
                } else if (bType.equals(Types.SUBTASK.toString())) {
                    int bIdEpic = Integer.parseInt(arr[7]);
                    Subtask sub = new Subtask(bId,
                            epicTasks.get(bIdEpic), bName, bDescription);
                    sub.setDateTime(start, end);

                    setBackedTaskStatus(sub, bStatus);
                    subTasks.put(sub.getId(), sub);
                    epicTasks.get(bIdEpic).addTask(sub);
                }

                currentId = bId + 1;
            }
        }
    }

    private static void setBackedTaskStatus(AbstractTask task, String status) {
        switch (status) {
            case "NEW" -> task.setStatus(Status.NEW);
            case "IN_PROGRESS" -> task.setStatus(Status.IN_PROGRESS);
            case "DONE" -> task.setStatus(Status.DONE);
        }
    }
}
