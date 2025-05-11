import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());

        logger.info("Testing class Manager");
        TestManager.testCreateTask();
        TestManager.testCreateEpicTask();
        TestManager.testGetAll();
        TestManager.testGetById();
        TestManager.testUpdateTask();
        TestManager.testRemoveById();
        TestManager.testGetTasksOfEpic();
        logger.info("-".repeat(50));
    }
}
