package managers;

import history.HistoryManager;
import managers.utiles.DateTimeFormatPatterns;
import models.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected static final Map<Integer, Task> tasks;
    protected static final Map<Integer, EpicTask> epicTasks;
    protected static final Map<Integer, Subtask> subTasks;
    protected static Integer currentId;

    private static Set<AbstractTask> priorTasks;
    private static final Map<Long, Boolean> line;
    private HistoryManager hManager;
    private final String originTime = "01.01.25|00:00:00";

    static {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subTasks = new HashMap<>();
        line = new HashMap<>();
        priorTasks = new TreeSet<>((t1, t2) -> t2.getStartTime().compareTo(t1.getStartTime()));
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
        priorTasks.clear();
        line.clear();
        hManager = Managers.getDefaultHistory();
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
    public void createTask(Task task) {
        checkingTaskOverlayForCreate(task);
        task.setId(currentId++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        checkingTaskOverlayForCreate(subtask);

        Optional<EpicTask> oTask = Optional.of(epicTasks.get(subtask.getIdOwner()));
        EpicTask task = oTask.orElseThrow(IllegalArgumentException::new);

        subtask.setId(currentId++);
        subtask.setIdOwner(task);

        subTasks.put(subtask.getId(), subtask);
        task.addTask(subtask);
    }

    @Override
    public void createEpicTask(EpicTask epicTask) {
        checkingTaskOverlayForCreate(epicTask);
        epicTask.setId(currentId++);
        epicTasks.put(epicTask.getId(), epicTask);
    }

    @Override
    public void updateTask(Task task) {
        checkingTaskOverlayForUpdate(task);
        updateThisTask(getById(task.getId()), task.getName(), task.getDescription(), task.getStatus());
    }

    @Override
    public void updateEpicTask(EpicTask task) {
        checkingTaskOverlayForUpdate(task);
        updateThisTask(getEpicById(task.getId()), task.getName(), task.getDescription(), task.getStatus());
    }

    @Override
    public void updateSubtask(Subtask task) {
        checkingTaskOverlayForUpdate(task);
        updateThisTask(getSubtaskById(task.getId()), task.getName(), task.getDescription(), task.getStatus());
    }

    @Override
    public void setDateTime(int id, String start) {
        if (!start.isBlank()) {
            if (!isTasksOverlay(start)) {
                if (tasks.containsKey(id)) {
                    tasks.get(id).setDateTime(start);
                    priorTasks.add(tasks.get(id));
                } else if (epicTasks.containsKey(id)) {
                    epicTasks.get(id).setDateTime(start);
                    priorTasks.add(epicTasks.get(id));
                } else if (subTasks.containsKey(id)) {
                    subTasks.get(id).setDateTime(start);
                    priorTasks.add(subTasks.get(id));
                }

                putLine(start);
            } else {
                System.out.println("Time of task is overlay");
            }
        }
    }

    @Override
    public void removeById(int id) {
        if (tasks.containsKey(id)) {
            hManager.remove(tasks.get(id));
            priorTasks.remove(tasks.get(id));
            tasks.remove(id);
        } else if (subTasks.containsKey(id)) {
            hManager.remove(subTasks.get(id));
            subTasks.get(id).getOwner().removeTask(id);
            priorTasks.remove(subTasks.get(id));
            subTasks.remove(id);
        } else if (epicTasks.containsKey(id)) {
            epicTasks.get(id).getAllTasks().values().forEach(sub -> {
                hManager.remove(sub);
                epicTasks.get(id).getTaskById(sub.getId());
                subTasks.remove(sub.getId());
            });

            hManager.remove(epicTasks.remove(id));
            priorTasks.remove(epicTasks.get(id));
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
        return priorTasks;
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

    private void checkingTaskOverlayForUpdate(AbstractTask task) {
        if (task.getStartTime() != null && isTasksOverlay(task.getStartTime())) {
            putLine(task.getStartTime().toString());
        }
    }

    private void checkingTaskOverlayForCreate(AbstractTask task) {
        if (!task.getStartTime().isBlank() && !isTasksOverlay(task.getStartTime())) {
            putLine(task.getStartTime());
            priorTasks.add(task);
        }
    }

    private void updateThisTask(AbstractTask task, String name, String description, Status status) {
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
    }

    private void putLine(String start) {
        line.put(Duration.between(LocalDateTime.parse(start, DateTimeFormatPatterns.format),
                LocalDateTime.parse(originTime, DateTimeFormatPatterns.format)).toMinutes() / 10, true);
    }
}
