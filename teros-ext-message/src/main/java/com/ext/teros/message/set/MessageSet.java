package com.ext.teros.message.set;

import com.ext.teros.message.model.message.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class MessageSet {

    ArrayList<Table> tableList = null;
    ConcurrentHashMap<String, String> optionMap = null;

    public MessageSet() {
        tableList = new ArrayList<Table>();
        optionMap = new ConcurrentHashMap<>();
    }

    public void setOption(String key, String value) {
        if (value == null)
            value = "";
        optionMap.put(key, value);
    }
}