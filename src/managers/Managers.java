package managers;

import history.HistoryManager;
import history.InMemoryHistoryManager;

public class Managers {
    private static final HistoryManager hManager = new InMemoryHistoryManager();
    private static final TaskManager tManager = new InMemoryTaskManager();

    public static TaskManager getDefault() {
        return tManager;
    }

    public static HistoryManager getDefaultHistory() {
        return hManager;
    }
}
