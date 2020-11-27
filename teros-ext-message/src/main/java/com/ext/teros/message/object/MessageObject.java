package com.ext.teros.message.object;

import com.ext.teros.message.model.message.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class MessageObject implements Serializable {
    String messageObjectName = "";
    ArrayList<Table> tableList = null;
    ConcurrentHashMap<String, String> optionMap = null;

    public MessageObject() {
        tableList = new ArrayList<Table>();
        optionMap = new ConcurrentHashMap<>();
    }

    public void setOption(String key, String value) {
        if (value == null)
            value = "";
        optionMap.put(key, value);
    }
}
