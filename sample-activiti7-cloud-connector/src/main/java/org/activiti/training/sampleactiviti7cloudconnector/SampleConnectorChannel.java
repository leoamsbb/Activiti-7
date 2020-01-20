package org.activiti.training.sampleactiviti7cloudconnector;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SampleConnectorChannel {
    String SAMPLE_CONNECTOR_CONSUMER = "sampleConnectorConsumer";

    @Input(SAMPLE_CONNECTOR_CONSUMER)
    SubscribableChannel sampleConnectorConsumer();
}
