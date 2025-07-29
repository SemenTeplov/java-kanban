import endpoints.utiles.Serialization;
import models.EpicTask;

import models.Subtask;
import models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskServerTest {
    static HttpTaskServer httpTaskServer;

    static {
        try {
            httpTaskServer = new HttpTaskServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void start() {
        httpTaskServer.start();
    }

    @BeforeEach
    void init() {
        httpTaskServer.deleteAllTasks();
    }

    @Test
    void getEpicTasks() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getEmptyEpicTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getEpicTasksById() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getEpicTasksByWrongId() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

    @Test
    void getSubTasksOfFpic() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        Subtask subtask = new Subtask(2, task, "sub", "text");
        task.addTask(subtask);
        httpTaskServer.getManager().createEpicTask(task);
        httpTaskServer.getManager().createSubtask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/1/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getSubTasksOfFpicWrong() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        Subtask subtask = new Subtask(2, task, "sub", "text");
        task.addTask(subtask);
        httpTaskServer.getManager().createEpicTask(task);
        httpTaskServer.getManager().createSubtask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/3/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

    @Test
    void createFpic() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        String json = Serialization.serialized(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getAllEpics().size());
        assertEquals(1, httpTaskServer.getManager().getAllEpics().get(1).getId());
    }

    @Test
    void deleteFpic() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);

        assertEquals(1, httpTaskServer.getManager().getAllEpics().size());

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0, httpTaskServer.getManager().getAllEpics().size());
    }

    @Test
    void getSubTasks() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        Subtask sub = new Subtask(2, task, "Sub", "Text");
        httpTaskServer.getManager().createEpicTask(task);
        httpTaskServer.getManager().getEpicById(1).addTask(sub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getEmptySubTasks() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getSubTaskById() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        Subtask sub = new Subtask(2, task, "Sub", "Text");
        httpTaskServer.getManager().createEpicTask(task);
        httpTaskServer.getManager().createSubtask(sub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getSubTaskByWrongId() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        Subtask sub = new Subtask(2, task, "Sub", "Text");
        httpTaskServer.getManager().createEpicTask(task);
        httpTaskServer.getManager().createSubtask(sub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

    @Test
    void createSubTask() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);
        Subtask sub = new Subtask(2, task, "Sub", "Text");
        String json = Serialization.serialized(sub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getAllSubtasks().size());
        assertEquals(2, httpTaskServer.getManager().getAllSubtasks().get(2).getId());
    }

    @Test
    void updateSubTask() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);
        Subtask sub = new Subtask(2, task, "Sub", "Text");
        httpTaskServer.getManager().createSubtask(sub);
        Subtask upsub = httpTaskServer.getManager().getSubtaskById(2);
        upsub.setName("upSub");
        String json = Serialization.serialized(upsub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getAllSubtasks().size());
        assertEquals("upSub", httpTaskServer.getManager().getAllSubtasks().get(2).getName());
    }

    @Test
    void deleteSubTask() throws IOException, InterruptedException {
        EpicTask task = new EpicTask(1, "Task", "text");
        httpTaskServer.getManager().createEpicTask(task);
        Subtask sub = new Subtask(2, task, "Sub", "Text");
        httpTaskServer.getManager().createSubtask(sub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0, httpTaskServer.getManager().getAllSubtasks().size());
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        Task task = new Task(1, "Task", "text");
        httpTaskServer.getManager().createTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getEmptyTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getTaskById() throws IOException, InterruptedException {
        Task task = new Task(1, "Task", "text");
        httpTaskServer.getManager().createTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getTaskByWrongId() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

    @Test
    void createTask() throws IOException, InterruptedException {
        Task task = new Task(1, "Task", "text");
        String json = Serialization.serialized(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getAllTasks().size());
        assertEquals(1, httpTaskServer.getManager().getAllTasks().get(1).getId());
    }

    @Test
    void updateTask() throws IOException, InterruptedException {
        Task task = new Task(1, "Task", "text");
        httpTaskServer.getManager().createTask(task);
        Task uptask = httpTaskServer.getManager().getById(1);
        uptask.setName("uptask");
        String json = Serialization.serialized(uptask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getAllTasks().size());
        assertEquals("uptask", httpTaskServer.getManager().getAllTasks().get(1).getName());
    }

    @Test
    void deleteTask() throws IOException, InterruptedException {
        Task task = new Task(1, "Task", "text");
        httpTaskServer.getManager().createTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0, httpTaskServer.getManager().getAllTasks().size());
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        assertEquals(0, httpTaskServer.getManager().getHistory().getHistory().size());

        Task task = new Task(1, "Task", "text");
        httpTaskServer.getManager().createTask(task);
        httpTaskServer.getManager().getById(1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getHistory().getHistory().size());
    }

    @Test
    void getPrioritized() throws IOException, InterruptedException {
        assertEquals(0, httpTaskServer.getManager().getPrioritizedTasks().size());

        Task task = new Task(1, "Task", "text");
        task.setDateTime("26.07.25|11:41:23");
        httpTaskServer.getManager().createTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(1, httpTaskServer.getManager().getPrioritizedTasks().size());
    }
}