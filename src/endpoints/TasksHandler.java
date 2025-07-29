package endpoints;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import endpoints.utiles.MessageCode;
import endpoints.utiles.Serialization;
import managers.TaskManager;
import models.AbstractTask;
import models.Task;

import java.io.IOException;
import java.util.Map;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    public TasksHandler(TaskManager tManager) {
        super(tManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String[] path = httpExchange.getRequestURI().getPath().split("/");
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET" -> {
                if (path.length == 2) {
                    sendTasks(httpExchange, tManager.getAllTasks());
                } else if (path.length == 3 && Character.isDigit(path[2].charAt(0))) {
                    try {
                        sendTask(httpExchange, tManager.getById(Integer.parseInt(path[2])));
                    } catch (RuntimeException e) {
                        MessageCode.sendNotFound(httpExchange);
                    }
                }
            }
            case "POST" -> {
                if (path.length == 2) {
                    Task task = Serialization
                            .deserializedTask()
                            .fromJson(getBodyText(httpExchange.getRequestBody()), Task.class);
                    tManager.createTask(task);

                    MessageCode.sendHasOverlaps(httpExchange, task, tManager);
                } else if (path.length == 3 && Character.isDigit(path[2].charAt(0))) {
                    try {
                        Task task = Serialization
                                .deserializedTask()
                                .fromJson(getBodyText(httpExchange.getRequestBody()), Task.class);
                        task.setId(Integer.parseInt(path[2]));
                        tManager.updateTask(task);

                        MessageCode.sendHasOverlaps(httpExchange, task, tManager);
                    } catch (RuntimeException e) {
                        MessageCode.sendNotFound(httpExchange);
                    }
                }
            }
            case "DELETE" -> {
                delete(httpExchange, Integer.parseInt(path[2]));
            }
        }
    }

    private void sendTask(HttpExchange httpExchange, AbstractTask task) throws IOException {
        String str = Serialization.serialized(task);
        MessageCode.sendText(httpExchange, str);
    }

    private void sendTasks(HttpExchange httpExchange, Map<Integer, Task> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();

        tasks.values().forEach(v -> sb.append(Serialization.serialized(v)));
        MessageCode.sendText(httpExchange, sb.toString());
    }
}
