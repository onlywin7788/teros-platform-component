package com.ext.teros.message_connector.kafka.information;

import lombok.Getter;

@Getter
public class ProgramInformation {
    private final String type = "KAFKA";
    private final String version = "1.0.0";
}
