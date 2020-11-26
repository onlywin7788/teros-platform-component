package com.ext.teros.message.model.message;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class Table {

    ArrayList<Record> recordList = null;
    ConcurrentHashMap<String, String> optionMap = null;

    public Table() {
        recordList = new ArrayList<Record>();
        optionMap = new ConcurrentHashMap<>();
    }

    public void addOption(String key, String value) {
        if (value == null)
            value = "";
        optionMap.put(key, value);
    }
}
