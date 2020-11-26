package com.ext.teros.message.model.message;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class Field {

    ConcurrentHashMap<String, String> optionMap = null;
    byte[] value = null;

    public Field() {
        optionMap = new ConcurrentHashMap<>();
    }
    public void addOption(String key, String value) {
        if (value == null)
            value = "";
        optionMap.put(key, value);
    }
}
