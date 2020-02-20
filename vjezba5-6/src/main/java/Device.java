import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONArray;

public class Device {
     String device_name;
    private String broker;// for json export use
    private String client_id;// for json export use
    private String password;// for json export use
    private ArrayList<Sensor> list_of_sensors;
    private MqttClient sampleClient;
    private MqttConnectOptions connOpts;
    
    public Device(String DeviceName,String Broker,String ClientId,String Password){
        list_of_sensors=new ArrayList<>();
        device_name=DeviceName;
        broker=Broker;
        client_id=ClientId;
        password=Password;
        
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(ClientId);
            connOpts.setPassword(Password.toCharArray());
            sampleClient = new MqttClient(Broker, ClientId, persistence);
        } catch (MqttException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Device(String file_path){
        //reading from file, if succesful creates JSON object
        File file = new File(file_path);
        String content;
        JSONObject device=new JSONObject(); 
        
        try {
             content = FileUtils.readFileToString(file, "utf-8");
             device = new JSONObject(content);  
        } catch (IOException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Device could NOT be created");
        }
        
        //initialization of simple device data members
        list_of_sensors=new ArrayList<>();
        device_name=device.getString("Device_name");
        broker=device.getString("Broker");
        client_id=device.getString("ClientId");
        password=device.getString("Password");
        
        //mmqt members initialization
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(client_id);
            connOpts.setPassword(password.toCharArray());
            sampleClient = new MqttClient(broker, client_id, persistence);
        } catch (MqttException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //initialization of sensors array
        JSONArray sensors_json_array = device.getJSONArray("Sensors");
        for (int i = 0; i < sensors_json_array.length(); i++) {
            JSONObject temp_object = sensors_json_array.getJSONObject(i);
            String sensor_name=temp_object.getString("sensor_name");
            int minimum_value=temp_object.getInt("minimum_value");
            int maximum_value=temp_object.getInt("maximum_value");
            int update_interval=temp_object.getInt("update_interval");
            String unit=temp_object.getString("unit");
            
            list_of_sensors.add(new Sensor(sensor_name,minimum_value,maximum_value,update_interval,unit,this));
        }
        
    }
    public void start(){ 
        System.out.println("Device: ["+device_name+"] started");
        try {
            sampleClient.connect(connOpts);
            for (Sensor sensor: list_of_sensors) {
                sensor.start_publishing();
            }
        } catch (MqttException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void stop(){
        for (Sensor sensor: list_of_sensors) {
            sensor.stop_publishing();
        }   
        System.out.println("Device: ["+device_name+"] stopped");
    }
    public void add_sensor(Sensor sensor){
        list_of_sensors.add(sensor);
    }
    public String get_name(){
        return device_name;
    }
    public MqttClient get_client(){
        return sampleClient;
    }
    public void export_to_json_file(String file_path){
        //new json object with data needed for constructor of this class
        JSONObject this_device = new JSONObject();
        this_device.put("Device_name",device_name);
        this_device.put("Broker",broker);
        this_device.put("ClientId",client_id);
        this_device.put("Password",password);
        
        //new json array  which will hold sensor data (json objects)
        JSONArray sensors_array_list = new JSONArray();
        for (Sensor sensor: list_of_sensors) {
            sensors_array_list.put(sensor.export_to_json_object());
        }
        //adding json array to json object
        this_device.put("Sensors", sensors_array_list);
        
        //saving to file
        File file = new File(file_path);
        try {
            FileUtils.write(file,this_device.toString(),"utf-8");
        } catch (IOException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}