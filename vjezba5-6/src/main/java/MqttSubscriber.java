import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttSubscriber implements MqttCallback {
    private  MqttClient subscriber;
    public void doDemo() {
        try {
            subscriber = new MqttClient("tcp://127.0.0.1:1883", "Sending");
            subscriber.connect();
            subscriber.setCallback(this);
            subscriber.subscribe("#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub
    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
        throws Exception {
            System.out.println(topic + message);
            //this.topic_and_message.put("topic", topic);
            //this.topic_and_message.put("message", message.toString());
            
            //System.out.println(topic_and_message.get("topic"));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
    }

}
//https://github.com/eclipse/paho.mqtt.java/blob/master/org.eclipse.paho.sample.mqttv3app/src/main/java/org/eclipse/paho/sample/mqttv3app/Sample.java