package com.teros.data_connector.file.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeConfig {
    String fileReadPath;
    String fileReadRename;
    String fileReadTimeout;
    String messageFormat;
}
