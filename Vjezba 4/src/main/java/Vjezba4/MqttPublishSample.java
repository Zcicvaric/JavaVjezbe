package Vjezba4;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.google.gson.Gson;

    public class MqttPublishSample {
            

    public static void main(String[] args) {

        Gson gson = new Gson();

        String json = "{'sensors':[{'factor' : '10','minValue' : '-3266.8','maxValue' : '3266.8','unitOfMeasure' : '°C'}," +
                                  "{'factor' : '1000','minValue' : '0','maxValue' : '65.336','unitOfMeasure' : 'Bar'}," +
                                  "{'factor' : '0','minValue' : '0','maxValue' : '65336','unitOfMeasure' : 'Litra'}," +
                                  "{'factor' : '10','minValue' : '0','maxValue' : '6533.6','unitOfMeasure' : 'm3'}]}";

        WaterFlowMeter m1 =gson.fromJson(json, WaterFlowMeter.class);
        String test= gson.toJson(m1);
        System.out.println(test);


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