import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;


class Sensor implements Runnable{
    private String sensor_name;
    private int value;
    private int minimum_value;
    private int maximum_value;
    private Device device;
    private MqttClient mqttclient;
    private int update_interval;
    private Thread t;
    private String unit;
    public Sensor(String name,int min_value,int max_value,int update_interval,String unit,Device device){
        this.sensor_name=name;
        this.minimum_value=min_value;
        this.maximum_value=max_value;
        this.device=device;
        this.update_interval=update_interval;
        this.mqttclient=device.get_client();
        this.unit=unit;
    }
    private void read_value(){
        this.value=ThreadLocalRandom.current().nextInt(minimum_value, maximum_value + 1);
    }
    public void run(){
        while(true){
            try {
                Thread.sleep(update_interval*1000);
                read_value();
                String content=" = "+Integer.toString(value);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(2);
                mqttclient.publish(device.get_name()+"/"+sensor_name, message);
                //System.out.println("Message published from sensor ["+sensor_name+"]" );
            } catch (MqttException|InterruptedException ex) {
                //Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
    }
    public void start_publishing(){
        if (t == null) {
            t = new Thread (this, "senzori");
            t.start ();
        }
        System.out.println("Sensor: ["+this.sensor_name+"] started");
    }
    public void stop_publishing(){
        if (t != null) {
            t.interrupt ();
        }
        System.out.println("Sensor: ["+this.sensor_name+"] stopped");
    }
    public JSONObject export_to_json_object(){
        JSONObject this_sensor = new JSONObject();
        this_sensor.put("sensor_name", this.sensor_name);
        this_sensor.put("minimum_value", this.minimum_value);
        this_sensor.put("maximum_value", this.maximum_value);
        this_sensor.put("update_interval", this.update_interval);
        this_sensor.put("unit", this.unit);
        this_sensor.put("device", this.device.get_name());
        return this_sensor; 
    }
}
