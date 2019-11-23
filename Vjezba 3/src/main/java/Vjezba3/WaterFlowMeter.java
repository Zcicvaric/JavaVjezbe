package Vjezba3;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class WaterFlowMeter {
    
    List<Sensor> sensors = new ArrayList<Sensor>();

    public WaterFlowMeter() {
        sensors.add(new Sensor(10, -3266.8, 3266.8, "°C"));
        sensors.add(new Sensor(1000, 0, 65.336, "Bar"));
        sensors.add(new Sensor(0, 0, 65336, "Litra"));
        sensors.add(new Sensor(10, 0, 6533.6, "m3"));
    }     
        
    public static String updateSensorReading(Sensor s) {
        if( null == s.getUnitOfMeasure())
            return "Sensor dosen't exist!";
        else switch (s.getUnitOfMeasure()) {
                case "°C":
                    return "Current water temperature: " + s.generateRandomValue();
                case "Bar":
                    return "Current water pressure: " + s.generateRandomValue();
                case "Litra":
                    return "Water usage in 1 minute: " + s.generateRandomValue();
                case "m3":
                    return "Water usage in 1 day: " + s.generateRandomValue();
                default:
                    return "Sensor dosen't exist!";
            }
    }
        
    public void publish(MqttClient client) throws MqttException, InterruptedException {
        int i = 0;
        while(i < 4) {
            String sensorReading = updateSensorReading(sensors.get(i));
            MqttMessage message = new MqttMessage(sensorReading.getBytes());
            message.setQos(2);
            client.publish("Sensors", message);
            i++;
            Thread.sleep(2000);
        }
            
    }
}
