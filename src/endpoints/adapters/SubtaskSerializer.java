package endpoints.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Subtask;

import java.lang.reflect.Type;

public class SubtaskSerializer implements JsonSerializer<Subtask> {
    @Override
    public JsonElement serialize(Subtask subtask, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.addProperty("id", subtask.getId());
        object.addProperty("name", subtask.getName());
        object.addProperty("description", subtask.getDescription());
        object.addProperty("duration", !subtask.getDuration().isZero() ? subtask.getDuration().toString() : "");
        object.addProperty("startTime", !subtask.getStartTime().isEmpty() ? subtask.getStartTime() : "");
        object.addProperty("status", subtask.getStatus().toString());
        object.addProperty("type", subtask.getType().toString());
        object.addProperty("owner", subtask.getIdOwner());

        return object;
    }
}
