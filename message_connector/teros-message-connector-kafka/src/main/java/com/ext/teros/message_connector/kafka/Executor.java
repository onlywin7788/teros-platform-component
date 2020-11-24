package com.ext.teros.message_connector.kafka;

import com.ext.teros.message_connector.kafka.config.Loader;
import com.ext.teros.message_connector.kafka.information.ProgramInformation;
import com.ext.teros.message_connector.kafka.config.model.Connection;
import com.ext.teros.message_connector.spec.MessageConnectorSpec;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Executor implements MessageConnectorSpec {

    private Properties properties = null;
    private  Connection config = null;
    private Producer kafkaProducer = null;
    private String message = "";

    // config
    private Connection connection = null;
    private Loader loader = new Loader();

    //extra
    private ProgramInformation programInformation;

    public Executor() {
        properties = new Properties();
        loader = new Loader();
        programInformation = new ProgramInformation();
    }

    @Override
    public void loadConfig(String configPath) throws Exception {
        loader.loadConfig(configPath);
        connection = loader.getConnection();
    }
    @Override
    public void initialize() throws Exception {

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connection.getHost() + ":" + connection.getPort());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(properties);
    }

    @Override
    public void connect() throws Exception {

    }

    @Override
    public void open() throws Exception {

    }

    @Override
    public void input() throws Exception {

    }

    @Override
    public void output() throws Exception {

        ProducerRecord<String, String> record = new ProducerRecord<>(connection.getTopic(), this.message);
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.out.println("kafka data send failed.");
            }
        });
        kafkaProducer.flush();

    }

    @Override
    public void commit() throws Exception {
    }

    @Override
    public void rollback() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void disconnect() throws Exception {

    }

    @Override
    public void uninitialize() throws Exception {

    }

    @Override
    public String getData() throws Exception {
        return message;
    }

    @Override
    public void setData(String s) throws Exception {
        this.message = s;
    }

    @Override
    public String getConnectorType() throws Exception {
        return programInformation.getType();
    }

    @Override
    public String getConnectorVersion() throws Exception {
        return programInformation.getVersion();
    }
}
