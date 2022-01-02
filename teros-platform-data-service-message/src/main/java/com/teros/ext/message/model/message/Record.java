package com.teros.ext.message.model.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class Record implements Serializable {

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
