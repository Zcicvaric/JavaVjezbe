package vjezba7.MqttBroker.Service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;
import vjezba7.MqttBroker.Dto.MqttMessageDto;
import vjezba7.MqttBroker.Interface.IMqttService;


@Service
public class MqttService implements IMqttService {

    @Override
    public void send(MqttMessageDto mqttMessage) throws Exception {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient mqttClient = new MqttClient(mqttMessage.broker, mqttMessage.clientId, persistence);
            MqttConnectOptions connectionOptions = new MqttConnectOptions();
            connectionOptions.setCleanSession(true);
            mqttClient.connect(connectionOptions);
            mqttClient.publish(mqttMessage.topic, new MqttMessage(mqttMessage.message.getBytes()));
            mqttClient.disconnect();
        } catch(MqttException me) {
            throw new Exception("Can't connect to broker!");
        }
    }
}