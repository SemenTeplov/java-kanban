package history;

import models.AbstractTask;

import java.util.List;
import history.models.Node;

public interface HistoryManager {
    void add(AbstractTask task);
    void remove(Node node);
    void remove(AbstractTask task);
    List<AbstractTask> getHistory();
}
