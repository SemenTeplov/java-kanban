package managers;

import org.junit.jupiter.api.Assertions;

public class ManagersTest {
    @org.junit.jupiter.api.Test
    public void testGetTaskManager() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @org.junit.jupiter.api.Test
    public void testGetHistoryManager() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}
