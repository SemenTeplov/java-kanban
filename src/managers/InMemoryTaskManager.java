package managers;

import history.HistoryManager;
import managers.utiles.DateTimeFormatPatterns;
import models.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class InMemoryTaskManager implements TaskManager {
    protected static final Map<Integer, Task> tasks;
    protected static final Map<Integer, EpicTask> epicTasks;
    protected static final Map<Integer, Subtask> subTasks;
    protected static Integer currentId;

    private static final Map<Long, Boolean> line;
    private final HistoryManager hManager;
    private final String originTime = "01.01.25|00:00:00";

    static {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subTasks = new HashMap<>();
        line = new HashMap<>();
        currentId = 1;
    }

    public InMemoryTaskManager() {
        hManager = Managers.getDefaultHistory();
    }

    @Override
    public int getNewId() {
        return currentId++;
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        tasks.values().forEach(hManager::add);

        return tasks;
    }

    @Override
    public Map<Integer, EpicTask> getAllEpics() {
        epicTasks.values().forEach(hManager::add);

        return epicTasks;
    }

    @Override
    public Map<Integer, Subtask> getAllSubtasks() {
        subTasks.values().forEach(hManager::add);

        return subTasks;
    }

    @Override
    public HistoryManager getHistory() {
        return hManager;
    }

    @Override
    public void removeAll() {
        tasks.clear();
        epicTasks.clear();
        subTasks.clear();
        currentId = 1;
    }

    @Override
    public Task getById(int id) {
        Optional<Task> task = Optional.of(tasks.get(id));
        hManager.add(task.orElseThrow(IllegalArgumentException::new));

        return task.get();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Optional<Subtask> task = Optional.of(subTasks.get(id));
        hManager.add(task.orElseThrow(IllegalArgumentException::new));

        return task.get();
    }

    @Override
    public EpicTask getEpicById(int id) {
        Optional<EpicTask> task = Optional.of(epicTasks.get(id));
        hManager.add(task.orElseThrow(IllegalArgumentException::new));

        return task.get();
    }

    @Override
    public void createTask(String name, String description) {
        tasks.put(currentId, new Task(currentId++, name, description));
    }

    @Override
    public void createSubtask(int idOwner, String name, String description) {
        Optional<EpicTask> oTask = Optional.of(epicTasks.get(idOwner));
        EpicTask task = oTask.orElseThrow(IllegalArgumentException::new);

        Subtask subtask = new Subtask(currentId++, epicTasks.get(idOwner), name, description);

        subTasks.put(subtask.getId(), subtask);
        task.addTask(subtask);
    }

    @Override
    public void createEpicTask(String name, String description) {
        epicTasks.put(currentId, new EpicTask(currentId++, name, description));
    }

    @Override
    public void updateTask(int id, String name, String description, Status status) {
        Task task = getById(id);

        updateTask(task, name, description, status);
    }

    @Override
    public void updateEpicTask(int id, String name, String description, Status status) {
        EpicTask task = getEpicById(id);

        updateTask(task, name, description, status);
    }

    @Override
    public void updateSubtask(int id, String name, String description, Status status) {
        Subtask task = getSubtaskById(id);

        updateTask(task, name, description, status);
    }

    @Override
    public void setDateTime(int id, String start) {
        if (!start.isBlank()) {
            if (!isTasksOverlay(start)) {
                if (tasks.containsKey(id)) {
                    tasks.get(id).setDateTime(start);
                } else if (epicTasks.containsKey(id)) {
                    epicTasks.get(id).setDateTime(start);
                } else if (subTasks.containsKey(id)) {
                    subTasks.get(id).setDateTime(start);
                }

                line.put(Duration.between(LocalDateTime.parse(start, DateTimeFormatPatterns.format),
                        LocalDateTime.parse(originTime, DateTimeFormatPatterns.format)).toMinutes() / 10, true);
            } else {
                System.out.println("Time of task is overlay");
            }
        }
    }

    @Override
    public void removeById(int id) {
        if (tasks.containsKey(id)) {
            hManager.remove(tasks.get(id));
            tasks.remove(id);
        } else if (subTasks.containsKey(id)) {
            hManager.remove(subTasks.get(id));
            subTasks.get(id).getOwner().removeTask(id);
            subTasks.remove(id);
        } else if (epicTasks.containsKey(id)) {
            epicTasks.get(id).getAllTasks().values().forEach(sub -> {
                hManager.remove(sub);
                epicTasks.get(id).getTaskById(sub.getId());
                subTasks.remove(sub.getId());
            });

            hManager.remove(epicTasks.remove(id));
            epicTasks.remove(id);
        }
    }

    @Override
    public Map<Integer, Subtask> getTasksOfEpic(int id) {
        Optional<EpicTask> task = Optional.of(epicTasks.get(id));

        return task.orElseThrow(IllegalArgumentException::new).getAllTasks();
    }

    @Override
    public Set<AbstractTask> getPrioritizedTasks() {
        Set<AbstractTask> tasks = new TreeSet<>((t1, t2) ->
                t2.getStartTime().compareTo(t1.getStartTime()));

        tasks.addAll(Stream.concat(Stream.concat(this.getAllTasks().values().stream(),
                this.getAllSubtasks().values().stream()),
                this.getAllEpics().values().stream()).filter(a -> a.getStartTime() != null).toList());

        return tasks;
    }

    public boolean isTasksOverlay(String start) {
        if (start.isEmpty()) {
            return false;
        } else {
            if (line.containsKey(Duration.between(LocalDateTime.parse(start, DateTimeFormatPatterns.format),
                    LocalDateTime.parse(originTime, DateTimeFormatPatterns.format)).toMinutes() / 10)) {

                return true;
            }

            return false;
        }
    }

    private void updateTask(AbstractTask task, String name, String description, Status status) {
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }
}
