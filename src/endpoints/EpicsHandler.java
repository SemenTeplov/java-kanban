package endpoints;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import endpoints.utiles.MessageCode;
import endpoints.utiles.Serialization;
import managers.TaskManager;
import models.AbstractTask;
import models.EpicTask;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    public EpicsHandler(TaskManager tManager) {
        super(tManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String[] path = httpExchange.getRequestURI().getPath().split("/");
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET" -> {
                if (path.length == 2) {
                    sendTasks(httpExchange, tManager.getAllEpics());
                } else if (path.length == 3 && Character.isDigit(path[2].charAt(0))) {
                    try {
                        sendTask(httpExchange, tManager.getEpicById(Integer.parseInt(path[2])));
                    } catch (RuntimeException e) {
                        MessageCode.sendNotFound(httpExchange);
                    }
                } else if (path.length == 4 && Character.isDigit(path[2].charAt(0))) {
                    new SubtasksHandler(httpExchange, tManager, Integer.parseInt(path[2]));
                }
            }
            case "POST" -> {
                if (path.length == 2) {
                    EpicTask task = Serialization
                            .deserializedEpic()
                            .fromJson(getBodyText(httpExchange.getRequestBody()), EpicTask.class);
                    tManager.createEpicTask(task);

                    MessageCode.sendHasOverlaps(httpExchange, task, tManager);
                }
            }
            case "DELETE" -> {
                delete(httpExchange, Integer.parseInt(path[2]));
            }
        }
    }

    private void sendTask(HttpExchange httpExchange, EpicTask task) throws IOException {
        String str = Serialization.serialized(task);
        MessageCode.sendText(httpExchange, str);
    }

    private void sendTasks(HttpExchange httpExchange, Map<Integer, EpicTask> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();

        tasks.values().forEach(v -> sb.append(Serialization.serialized(v)));
        MessageCode.sendText(httpExchange, sb.toString());
    }
}
