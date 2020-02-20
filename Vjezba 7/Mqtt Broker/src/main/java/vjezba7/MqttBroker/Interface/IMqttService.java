package vjezba7.MqttBroker.Interface;

import vjezba7.MqttBroker.Dto.MqttMessageDto;

public interface IMqttService {
    void send(MqttMessageDto mqttMessage) throws Exception;
}
