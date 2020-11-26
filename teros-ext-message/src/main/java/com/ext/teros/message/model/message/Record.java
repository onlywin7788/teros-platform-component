package com.ext.teros.message.model.message;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class Record {

    ArrayList<Field> fieldList;
    ConcurrentHashMap<String, String> optionMap = null;

    public Record() {
        fieldList = new ArrayList<Field>();
        optionMap = new ConcurrentHashMap<>();
    }

    public void addOption(String key, String value) {
        if (value == null)
            value = "";
        optionMap.put(key, value);
    }
}
