package history;

import models.AbstractTask;

import java.util.List;

public interface HistoryManager {
    void add(AbstractTask task);

    void remove(AbstractTask task);

    List<AbstractTask> getHistory();
}
