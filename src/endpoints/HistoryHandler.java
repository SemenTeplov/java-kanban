package endpoints;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    public HistoryHandler(TaskManager tManager) {
        super(tManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String[] path = httpExchange.getRequestURI().getPath().split("/");
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET") && path.length == 2) {
            sendTasks(httpExchange, tManager.getHistory().getHistory());
        }
    }
}
