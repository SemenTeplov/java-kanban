package managers;

import history.HistoryManager;
import org.junit.jupiter.api.Assertions;

public class ManagersTest {
    TaskManager tManager = Managers.getDefault();
    HistoryManager hManager = Managers.getDefaultHistory();

    @org.junit.jupiter.api.Test
    public void testGetTaskManager() {
        Assertions.assertNotNull(tManager);
    }

    @org.junit.jupiter.api.Test
    public void testGetHistoryManager() {
        Assertions.assertNotNull(hManager);
    }
}
