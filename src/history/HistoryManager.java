package history;

import models.AbstractTask;

import java.util.ArrayList;

public interface HistoryManager {
    void add(AbstractTask task);

    ArrayList<AbstractTask> getHistory();
}
