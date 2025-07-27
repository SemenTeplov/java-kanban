import com.sun.net.httpserver.HttpServer;
import endpoints.*;

import java.io.IOException;
import java.net.InetSocketAddress;

import managers.Managers;
import managers.TaskManager;

public class HttpTaskServer {
    private final TaskManager tManager;
    private final HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        tManager = Managers.getDefault();
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/tasks", new TasksHandler(tManager));
        httpServer.createContext("/subtasks", new SubtasksHandler(tManager));
        httpServer.createContext("/epics", new EpicsHandler(tManager));
        httpServer.createContext("/history", new HistoryHandler(tManager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(tManager));
    }

   public void start() {
       httpServer.start();
   }

    public void stop() {
        httpServer.stop(0);
    }

    public void deleteAllTasks() {
        tManager.removeAll();
    }

    public TaskManager getManager() {
        return tManager;
    }
}
