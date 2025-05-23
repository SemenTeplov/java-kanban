package history;

import models.AbstractTask;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<AbstractTask> history = new ArrayList<>();;

    @Override
    public void add(AbstractTask task) {
        history.add(task);
    }

    @Override
    public ArrayList<AbstractTask> getHistory() {
        ArrayList<AbstractTask> list = new ArrayList<>();

        for (int i = history.size() - 1; i > 0; i--) {
            list.add(history.get(i));

            if (i == history.size() - 10) {
                break;
            }
        }

        return list;
    }
}
