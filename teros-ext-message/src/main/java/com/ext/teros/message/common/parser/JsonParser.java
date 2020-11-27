package com.ext.teros.message.common.parser;

import com.google.gson.*;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    Gson gson = null;
    JsonElement json = null;

    public JsonParser() {
        gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    }

    public void loadString(String contents) {
        JsonObject jsonObject = gson.fromJson(contents, JsonObject.class);
        json = jsonObject;
    }

    public String getTextFromElement(String path) throws Exception {
        String returnString = "";

        JsonElement jsonElement = getJsonElementFromPath(path);
        returnString = jsonElement.getAsString();

        if (returnString == null)
            returnString = "";

        return returnString;
    }

    public JsonElement getJsonElementFromPath(String path) throws Exception {

//        String[] parts = path.split("\\.|\\[|\\]");
        String[] parts = path.split("\\/|\\[|\\]");

        JsonElement returnElement = json;

        for (String key : parts) {

            key = key.trim();
            if (key.isEmpty())
                continue;

            if (returnElement == null) {
                returnElement = JsonNull.INSTANCE;
                break;
            }

            if (returnElement.isJsonObject()) {
                returnElement = ((JsonObject) returnElement).get(key);
            } else if (returnElement.isJsonArray()) {
                int ix = Integer.valueOf(key) - 1;
                returnElement = ((JsonArray) returnElement).get(ix);
            } else break;
        }

        return returnElement;
    }

    public JsonArray addJsonArray(JsonArray acquireJsonArray, JsonObject jsonObject) {

        JsonArray jsonArray = acquireJsonArray;
        try {
            if (jsonArray == null)
                jsonArray = new JsonArray();

            jsonArray.add(jsonObject);

        } catch (Exception e) {
            throw e;
        }

        return jsonArray;
    }


    public JsonObject addJsonObject(JsonObject acquireJsonObject, String key, JsonElement jsonElement) {

        JsonObject jsonObject = acquireJsonObject;
        try {
            if (jsonObject == null)
                jsonObject = new JsonObject();

            jsonObject.add(key, jsonElement);
        } catch (Exception e) {
            throw e;
        }
        return jsonObject;
    }
}