package endpoints.adapters;

import com.google.gson.*;
import models.*;

import java.lang.reflect.Type;
import java.time.Duration;

public class SubtaskDeserializer implements JsonDeserializer<Subtask> {
    @Override
    public Subtask deserialize(JsonElement jsonElement, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Subtask task = new Subtask(jsonObject.get("id").getAsInt(),
                new EpicTask(jsonObject.get("owner").getAsInt(), "Epic", "Text"),
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
