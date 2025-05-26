package history;

import models.AbstractTask;

import java.util.ArrayList;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<AbstractTask> history = new LinkedList<>();
    private static final int MAX_COUNT_TASKS = 10;

    @Override
    public void add(AbstractTask task) {
        if (task != null) {
            history.add(task);
        }

        if (history.size() > MAX_COUNT_TASKS) {
            history.removeFirst();
        }
    }

    @Override
    public ArrayList<AbstractTask> getHistory() {
        ArrayList<AbstractTask> list = new ArrayList<>();

        for (int i = history.size() - 1; i > 0; i--) {
            list.add(history.get(i));

            if (i == history.size() - MAX_COUNT_TASKS) {
                break;
            }
        }

        return list;
    }
}
