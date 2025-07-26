package endpoints.utiles;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import models.AbstractTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageCode {
    public static void sendText(HttpExchange httpExchange, String text) throws IOException {
        writeResponse(httpExchange, text, 200);
    }

    public static void sendNotFound(HttpExchange httpExchange) throws IOException {
        writeResponse(httpExchange, "Not found", 404);
    }

    public static void sendHasOverlaps(HttpExchange httpExchange, AbstractTask task, TaskManager tManager) throws IOException {
        if (task.getStartTime() != null && tManager.isTasksOverlay(task.getStartTime())) {
            writeResponse(httpExchange, "Task Overlap", 406);
        } else {
            sendUpdate(httpExchange);
        }
    }

    private static void sendUpdate(HttpExchange httpExchange) throws IOException {
        writeResponse(httpExchange, "Update done", 201);
    }

    private static void writeResponse(HttpExchange httpExchange, String response, int code) throws IOException {
        byte[] resp = response.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(code, resp.length);
        httpExchange.getResponseBody().write(resp);
        httpExchange.close();
    }
}
