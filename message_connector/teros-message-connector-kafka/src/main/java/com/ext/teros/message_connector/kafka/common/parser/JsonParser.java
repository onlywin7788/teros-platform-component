package com.ext.teros.message_connector.kafka.common.parser;

import com.google.gson.*;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    public JsonElement getJsonElementFromPath(String jsonString, String path) {

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonElement json = jsonObject;

        String[] parts = path.split("\\.|\\[|\\]");
        JsonElement result = json;

        for (String key : parts) {

            key = key.trim();
            if (key.isEmpty())
                continue;

            if (result == null) {
                result = JsonNull.INSTANCE;
                break;
            }

            if (result.isJsonObject()) {
                result = ((JsonObject) result).get(key);
            } else if (result.isJsonArray()) {
                int ix = Integer.valueOf(key) - 1;
                result = ((JsonArray) result).get(ix);
            } else break;
        }

        return result;
    }
}