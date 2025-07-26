package endpoints;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import endpoints.utiles.MessageCode;
import endpoints.utiles.Serialization;
import managers.TaskManager;
import models.Subtask;

import java.io.IOException;
import java.util.Map;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    public SubtasksHandler(TaskManager tManager) {
        super(tManager);
    }

    public SubtasksHandler(HttpExchange httpExchange, TaskManager tManager, int epicId ) throws IOException {
        super(tManager);
        try {
            sendTasks(httpExchange, tManager.getTasksOfEpic(epicId));
        } catch (IOException | NullPointerException e) {
            MessageCode.sendNotFound(httpExchange);
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String[] path = httpExchange.getRequestURI().getPath().split("/");
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET" -> {
                if (path.length == 2) {
                    sendTasks(httpExchange, tManager.getAllSubtasks());
                } else if (path.length == 3 && Character.isDigit(path[2].charAt(0))) {
                    try {
                        sendTask(httpExchange, tManager.getSubtaskById(Integer.parseInt(path[2])));
                    } catch (RuntimeException e) {
                        MessageCode.sendNotFound(httpExchange);
                    }
                }
            }
            case "POST" -> {
                if (path.length == 2) {
                    Subtask task = Serialization.deserializedSubtask().fromJson(getBodyText(httpExchange.getRequestBody()), Subtask.class);

                    if (tManager.getAllEpics().containsKey(task.getIdOwner())) {
                        task.setOwner(tManager.getEpicById(task.getIdOwner()));
                        tManager.getEpicById(task.getIdOwner()).addTask(task);
                    }

                    tManager.createSubtask(task);

                    MessageCode.sendHasOverlaps(httpExchange, task, tManager);
                } else if (path.length == 3 && Character.isDigit(path[2].charAt(0))) {
                    try {
                        Subtask task = Serialization.deserializedSubtask().fromJson(getBodyText(httpExchange.getRequestBody()), Subtask.class);
                        task.setId(Integer.parseInt(path[2]));
                        tManager.updateSubtask(task);

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

    private void sendTask(HttpExchange httpExchange, Subtask task) throws IOException {
        String str = Serialization.serialized(task);
        MessageCode.sendText(httpExchange, str);
    }

    private void sendTasks(HttpExchange httpExchange, Map<Integer, Subtask> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();

        tasks.values().forEach(v -> sb.append(Serialization.serialized(v)));
        MessageCode.sendText(httpExchange, sb.toString());
    }
}
