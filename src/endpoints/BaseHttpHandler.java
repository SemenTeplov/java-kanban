package endpoints;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import endpoints.utiles.MessageCode;
import endpoints.utiles.Serialization;
import managers.TaskManager;
import models.AbstractTask;

abstract public class BaseHttpHandler {
    TaskManager tManager;

    public BaseHttpHandler(TaskManager tManager) {
        this.tManager = tManager;
    }

    protected void sendTasks(HttpExchange httpExchange, Collection<? extends AbstractTask> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();

        tasks.forEach(v -> sb.append(Serialization.serialized(v)));
        MessageCode.sendText(httpExchange, sb.toString());
    }

    protected String getBodyText(InputStream body) {
        Scanner scan = new Scanner(body);
        StringBuilder sb = new StringBuilder();

        while (scan.hasNext()) {
            sb.append(scan.nextLine());
        }

        return sb.toString();
    }

    protected void delete(HttpExchange httpExchange, int id) throws IOException {
        tManager.removeById(id);
        MessageCode.sendText(httpExchange, "Task deleted");
    }
}
