package com.ext.teros.message_connector.kafka.config.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Connection {
    String host;
    String port;
    String topic;
}
