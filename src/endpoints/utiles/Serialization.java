package endpoints.utiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import endpoints.adapters.*;
import models.AbstractTask;
import models.EpicTask;
import models.Subtask;
import models.Task;

public class Serialization {
    static public String serialized(EpicTask object) {
        return new GsonBuilder()
                .registerTypeAdapter(EpicTask.class, new EpictaskSerializer())
                .create()
                .toJson(object);
    }

    static public String serialized(AbstractTask object) {
        return new GsonBuilder()
                .registerTypeAdapter(Task.class, new TaskSerializer())
                .create()
                .toJson(object);
    }

    static public String serialized(Subtask object) {
        return new GsonBuilder()
                .registerTypeAdapter(Subtask.class, new SubtaskSerializer())
                .create()
                .toJson(object);
    }

    static public Gson deserializedEpic() {
        return new GsonBuilder()
                .registerTypeAdapter(EpicTask.class, new EpicDeserializer())
                .create();
    }

    static public Gson deserializedSubtask() {
        return new GsonBuilder()
                .registerTypeAdapter(Subtask.class, new SubtaskDeserializer())
                .create();
    }

    static public Gson deserializedTask() {
        return new GsonBuilder()
                .registerTypeAdapter(Task.class, new TaskDeserializer())
                .create();
    }
}
