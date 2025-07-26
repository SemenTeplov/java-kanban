package endpoints.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.EpicTask;

import java.lang.reflect.Type;

public class EpictaskSerializer implements JsonSerializer<EpicTask> {
    @Override
    public JsonElement serialize(EpicTask task, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.addProperty("id", task.getId());
        object.addProperty("name", task.getName());
        object.addProperty("description", task.getDescription());
        object.addProperty("duration", !task.getDuration().isZero() ? task.getDuration().toString() : "");
        object.addProperty("startTime", !task.getStartTime().isEmpty() ? task.getStartTime() : "");
        object.addProperty("status", task.getStatus().toString());
        object.addProperty("type", task.getType().toString());

        return object;
    }
}
