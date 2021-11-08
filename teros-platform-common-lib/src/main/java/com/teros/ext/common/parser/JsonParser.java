package com.teros.ext.common.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class JsonParser {
    Gson gson = null;
    JsonElement json = null;

    public JsonParser() {
        this.gson = (new GsonBuilder()).disableHtmlEscaping().setPrettyPrinting().create();
    }

    public void loadString(String contents) {
        JsonObject jsonObject = (JsonObject)this.gson.fromJson(contents, JsonObject.class);
        this.json = jsonObject;
    }

    public String getTextFromElement(String path) throws Exception {
        String returnString = "";
        JsonElement jsonElement = this.getJsonElementFromPath(path);
        returnString = jsonElement.getAsString();
        if (returnString == null) {
            returnString = "";
        }

        return returnString;
    }

    public String getJsonString() throws Exception {
        return this.json.toString();
    }

    public JsonElement getJsonElementFromPath(String path) throws Exception {
        String[] parts = path.split("\\/|\\[|\\]");
        JsonElement returnElement = this.json;
        String[] var4 = parts;
        int var5 = parts.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String key = var4[var6];
            key = key.trim();
            if (!key.isEmpty()) {
                if (returnElement == null) {
                    returnElement = JsonNull.INSTANCE;
                    break;
                }

                if (((JsonElement)returnElement).isJsonObject()) {
                    returnElement = ((JsonObject)returnElement).get(key);
                } else {
                    if (!((JsonElement)returnElement).isJsonArray()) {
                        break;
                    }

                    int ix = Integer.valueOf(key) - 1;
                    returnElement = ((JsonArray)returnElement).get(ix);
                }
            }
        }

        if (returnElement != null && ((JsonElement)returnElement).isJsonNull()) {
            returnElement = null;
        }

        return (JsonElement)returnElement;
    }

    public JsonElement getJsonElementFromPath(JsonElement acquireElement, String path) throws Exception {
        String[] parts = path.split("\\/|\\[|\\]");
        JsonElement returnElement = acquireElement;
        if (acquireElement == null) {
            returnElement = this.json;
        }

        String[] var4 = parts;
        int var5 = parts.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String key = var4[var6];
            key = key.trim();
            if (!key.isEmpty()) {
                if (returnElement == null) {
                    returnElement = JsonNull.INSTANCE;
                    break;
                }

                if (((JsonElement)returnElement).isJsonObject()) {
                    returnElement = ((JsonObject)returnElement).get(key);
                } else {
                    if (!((JsonElement)returnElement).isJsonArray()) {
                        break;
                    }

                    int ix = Integer.valueOf(key) - 1;
                    returnElement = ((JsonArray)returnElement).get(ix);
                }
            }
        }

        if (returnElement != null && ((JsonElement)returnElement).isJsonNull()) {
            returnElement = null;
        }

        return (JsonElement)returnElement;
    }

    public JsonElement getElementFromArrayMember(JsonArray acquireJsonArray, String memberName) {
        JsonArray jsonArray = acquireJsonArray;
        JsonElement returnElement = null;

        for(int i = 0; i < jsonArray.size(); ++i) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonElement element = jsonObject.get(memberName);
            if (element != null) {
                returnElement = element;
            }
        }

        return returnElement;
    }

    public JsonArray addJsonArray(JsonArray acquireJsonArray, JsonObject jsonObject) {
        JsonArray jsonArray = acquireJsonArray;

        try {
            if (jsonArray == null) {
                jsonArray = new JsonArray();
            }

            jsonArray.add(jsonObject);
            return jsonArray;
        } catch (Exception var5) {
            throw var5;
        }
    }

    public JsonObject addJsonObject(JsonObject acquireJsonObject, String key, JsonElement jsonElement) {
        JsonObject jsonObject = acquireJsonObject;

        try {
            if (jsonObject == null) {
                jsonObject = new JsonObject();
            }

            jsonObject.add(key, jsonElement);
            return jsonObject;
        } catch (Exception var6) {
            throw var6;
        }
    }

    public JsonObject addJsonObject(JsonObject acquireJsonObject, String key, String value) {
        JsonObject jsonObject = acquireJsonObject;

        try {
            if (jsonObject == null) {
                jsonObject = new JsonObject();
            }

            jsonObject.addProperty(key, value);
            return jsonObject;
        } catch (Exception var6) {
            throw var6;
        }
    }
}
