package endpoints.adapters;

import com.google.gson.*;
import models.Status;
import models.Task;
import models.Types;

import java.lang.reflect.Type;
import java.time.Duration;

public class TaskDeserializer implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement jsonElement, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Task task = new Task(jsonObject.get("id").getAsInt(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString());

        if (!jsonObject.get("duration").getAsString().isBlank()) {
            task.setDuration(Duration.parse(jsonObject.get("duration").getAsString()));
        }

        if (!jsonObject.get("startTime").getAsString().isBlank()) {
            task.setDateTime(jsonObject.get("startTime").getAsString());
        }

        task.setStatus(Status.valueOf(jsonObject.get("status").getAsString()));
        task.setType(Types.valueOf(jsonObject.get("type").getAsString()));

        return task;
    }
}
