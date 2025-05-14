import models.EpicTask;
import models.Status;

import java.util.logging.Logger;

public class TestManager {
    static Logger logger = Logger.getLogger(TestManager.class.getName());
    static Manager manager = new Manager();

    public static void testCreateTask() {
        logger.info("--start test createTask--");

        manager.createTask("Task 1", "Some text1.");
        manager.createTask("Task 2", "Some text2.");

        if (!manager.getById(1).toString().equals("1 Task 1 Some text1.")) {
            logger.warning("ERROR test1 createTask is fail\n" +
                    "expected: 1 Task 1 Some text1.\n" +
                    "received: " + manager.getById(1).toString());
            return;
        }

        if (!manager.getById(2).toString().equals("2 Task 2 Some text2.")) {
            logger.warning("ERROR test2 createTask is fail\n" +
                    "expected: 2 Task 2 Some text2.\n" +
                    "received: " + manager.getById(2).toString());
            return;
        }

        manager.removeAll();
        logger.info("--test createTask is success--");
    }

    public static void testCreateEpicTask() {
        logger.info("--start test createEpicTask--");
        manager.createEpicTask("EpicTask 1", "Some text1.");
        manager.createEpicTask("EpicTask 2", "Some text2.");

        if (!manager.getEpicById(1).toString().equals("1 EpicTask 1 Some text1.")) {
            logger.warning("ERROR test1 createEpicTask is fail\n" +
                    "expected: 1 EpicTask 1 Some text1.\n" +
                    "received: " + manager.getById(1).toString());
            return;
        }

        if (!manager.getEpicById(2).toString().equals("2 EpicTask 2 Some text2.")) {
            logger.warning("ERROR test2 createEpicTask is fail\n" +
                    "expected: 2 EpicTask 2 Some text2.\n" +
                    "received: " + manager.getById(2).toString());
            return;
        }

        manager.removeAll();
        logger.info("--test createEpicTask is success--");
    }

    public static void testGetAll() {
        logger.info("--start test getAll--");
        manager.createTask("Task 1", "Some text1.");
        manager.createTask("Task 2", "Some text2.");

        if (!manager.getAll().get(1).toString().equals("1 Task 1 Some text1.")) {
            logger.warning("ERROR test1 getAll is fail\n" +
                    "expected: 1 Task 1 Some text1.\n" +
                    "received: " + manager.getAll().get(1).toString());
            return;
        }

        if (!manager.getAll().get(2).toString().equals("2 Task 2 Some text2.")) {
            logger.warning("ERROR test2 getAll is fail\n" +
                    "expected: 2 Task 2 Some text2.\n" +
                    "received: " + manager.getAll().get(2).toString());
            return;
        }

        manager.removeAll();
        logger.info("--test getAll is success--");
    }

    public static void testGetById() {
        logger.info("--start test getById--");

        manager.createTask("Task 1", "Some text1.");
        manager.createTask("Task 2", "Some text2.");
        manager.createTask("Task 3", "Some text3.");
        manager.createTask("Task 4", "Some text4.");
        manager.createEpicTask("EpicTask 1", "Some text1.");
        manager.createEpicTask("EpicTask 2", "Some text2.");

        EpicTask task = manager.getEpicById(5);
        manager.createSubtask(task.getId(), "Task 5", "Some text5.");
        manager.createSubtask(task.getId(), "Task 6", "Some text6.");

        task = manager.getEpicById(6);
        manager.createSubtask(task.getId(), "Task 7", "Some text7.");
        manager.createSubtask(task.getId(), "Task 8", "Some text8.");

        if (!manager.getById(2).toString().equals("2 Task 2 Some text2.")) {
            logger.warning("ERROR test1 getById is fail\n" +
                    "expected: 2 Task 2 Some text2.\n" +
                    "received: " + manager.getById(2).toString());
            return;
        }

        if (!manager.getById(4).toString().equals("4 Task 4 Some text4.")) {
            logger.warning("ERROR test2 getById is fail\n" +
                    "expected: 4 Task 4 Some text4.\n" +
                    "received: " + manager.getById(4).toString());
            return;
        }

        if (!manager.getEpicById((5)).toString()
                .equals("5 EpicTask 1 Some text1. subtasks: 7 Task 5 Some text5. 8 Task 6 Some text6. ")) {
            logger.warning("ERROR test3 getById is fail\n" +
                    "expected: 5 EpicTask 1 Some text1. subtasks: 7 Task 5 Some text5. 8 Task 6 Some text6.\n" +
                    "received: " + manager.getById(5).toString());
            return;
        }

        if (!manager.getSubtaskById(9).toString().equals("9 Task 7 Some text7.")) {
            logger.warning("ERROR test4 getById is fail\n" +
                    "expected: 9 Task 7 Some text7.\n" +
                    "received: " + manager.getById(4).toString());
            return;
        }

        manager.removeAll();
        logger.info("--test getById is success--");
    }

    public static void testUpdateTask() {
        logger.info("--start test updateTask--");

        manager.createTask("Task 1", "Some text1.");
        manager.createTask("Task 2", "Some text2.");
        manager.createTask("Task 3", "Some text3.");

        manager.updateTask(1, "TaskChange1", "Text1", Status.NEW);
        manager.updateTask(2, "TaskChange2", "Text2", Status.IN_PROGRESS);
        manager.updateTask(3, "TaskChange3", "Text3", Status.IN_PROGRESS);

        if (!manager.getById(1).toString().equals("1 TaskChange1 Text1")
                && manager.getById(1).getStatus() != Status.NEW) {
            logger.warning("ERROR test1 updateTask is fail\n" +
                    "expected: 1 TaskChange1 Text1\n" +
                    "received: " + manager.getById(1).toString());
            return;
        }

        if (!manager.getById(2).toString().equals("2 TaskChange2 Text2")
                && manager.getById(2).getStatus() != Status.IN_PROGRESS) {
            logger.warning("ERROR test2 updateTask is fail\n" +
                    "expected: 2 TaskChange2 Text2\n" +
                    "received: " + manager.getById(2).toString());
            return;
        }

        if (!manager.getById(3).toString().equals("3 TaskChange3 Some text3.")
                && manager.getById(3).getStatus() != Status.IN_PROGRESS) {
            logger.warning("ERROR test3 updateTask is fail\n" +
                    "expected: 3 TaskChange3 Some text3.\n" +
                    "received: " + manager.getById(3).toString());
            return;
        }

        manager.removeAll();
        logger.info("--test updateTask is success--");
    }

    public static void testRemoveById() {
        logger.info("--start test removeById--");

        manager.createTask("Task 1", "Some text1.");
        manager.createTask("Task 2", "Some text2.");
        manager.createTask("Task 3", "Some text3.");
        manager.createTask("Task 4", "Some text4.");
        manager.createEpicTask("EpicTask 1", "Some text1.");
        manager.createEpicTask("EpicTask 2", "Some text2.");

        EpicTask task = manager.getEpicById(5);
        manager.createSubtask(task.getId(), "Task 5", "Some text5.");
        manager.createSubtask(task.getId(), "Task 6", "Some text6.");

        task = manager.getEpicById(6);
        manager.createSubtask(task.getId(), "Task 7", "Some text7.");
        manager.createSubtask(task.getId(), "Task 8", "Some text8.");

        manager.removeById(2);
        manager.removeById(9);

        try {
            logger.warning("test1 removeById is fail, object " + manager.getById(2).toString() + "didn't remove");
            return;
        } catch (IllegalArgumentException e) {
            logger.info("test1 removeById is success");
        }

        try {
            logger.warning("test2 removeById is fail, object " + manager.getById(9).toString() + "didn't remove");
            return;
        } catch (IllegalArgumentException e) {
            logger.info("test2 removeById is success");
        }

        manager.removeAll();
    }

    public static void testGetTasksOfEpic() {
        logger.info("--start test getTasksOfEpic--");

        manager.createEpicTask("EpicTask 1", "Some text1.");
        manager.createEpicTask("EpicTask 2", "Some text2.");

        EpicTask task = manager.getEpicById(1);
        manager.createSubtask(task.getId(), "Task 1", "Some text1.");
        manager.createSubtask(task.getId(), "Task 2", "Some text3.");

        if (manager.getTasksOfEpic(1).size() != 2) {
            logger.warning("ERROR test1 getTasksOfEpic is fail.");
            return;
        }

        if (!manager.getTasksOfEpic(2).isEmpty()) {
            logger.warning("ERROR test2 getTasksOfEpic is fail.");
            return;
        }

        manager.removeAll();
        logger.info("--test getTasksOfEpic is success--");
    }
}
