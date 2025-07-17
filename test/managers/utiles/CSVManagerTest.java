package managers.utiles;

import managers.FileBackedTaskManager;
import managers.exceptions.ManagerLoadException;
import managers.exceptions.ManagerSaveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVManagerTest {
    FileBackedTaskManager fbtm  = new FileBackedTaskManager("resourses/backup.csv");

    @Test
    void saveErrorPath() {
        Assertions.assertThrows(ManagerSaveException.class,
                () -> CSVManager.save("/somePath.csv", fbtm), "не тот путь");
    }

    @Test
    void loadFromFileErrorPath() {
        Assertions.assertThrows(ManagerLoadException.class,
                () -> CSVManager.loadFromFile("/somePath.csv"), "не тот путь");
    }
}