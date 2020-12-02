package com.ext.teros.message_connector.kafka.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeConfig {

    String connectionHost;
    String connectionPort;
    String connectionTopic;
    String messageFormat;
}
