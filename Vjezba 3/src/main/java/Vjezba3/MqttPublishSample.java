package Vjezba3;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

    public class MqttPublishSample {
            

    public static void main(String[] args) {

        WaterFlowMeter m1 = new WaterFlowMeter();
        String broker       = "tcp://127.0.0.1:1883";
        String clientId     = "WaterFlowMeter";
        MemoryPersistence persistence = new MemoryPersistence();
            
        try {
            MqttClient client1 = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker...");
            client1.connect(connOpts);
            System.out.println("Connected");
            while(true) {
                m1.publish(client1);
            }  
            //client1.disconnect();
            //System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("excep "+me);
        } catch (InterruptedException ex) {
            Logger.getLogger(MqttPublishSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}