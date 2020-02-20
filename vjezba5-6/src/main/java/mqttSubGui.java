import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONArray;
import org.json.JSONObject;

public class mqttSubGui  extends JFrame implements MqttCallback  {
    
    private MqttClient subscriber;
    String device_name;
    String broker;
    static mqttSubGui ssp;
    ArrayList<JPanel> list_of_JPanels=new ArrayList<>();
    Map<String, JLabel> jpanel_map = new HashMap<String, JLabel>();
    
    public mqttSubGui(String json_path) {
        super("mqtt Subscriber");
        
        //data members initialization
        this.JsonInit(json_path);
        
        //outter panel initialization
        JPanel outter_panel = new JPanel();
        outter_panel.setLayout(new BoxLayout(outter_panel,BoxLayout.PAGE_AXIS));
        
        //device name added
        outter_panel.add( createDevicePanel() );

        //sensors added
        for (int i = 0; i < this.list_of_JPanels.size(); i++) {
            outter_panel.add(list_of_JPanels.get(i));
        }
        
        getContentPane().add(outter_panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        //pack();
        setVisible(true);
        this.doDemo();
    }
    private JPanel createDevicePanel(){
        JPanel inner_panel = new JPanel(new FlowLayout());
        inner_panel.add(new JLabel(this.device_name));            
        return inner_panel;
    }
    private JPanel createSingleSensorPanel(String sensor_name,String sensor_unit){
        JPanel inner_panel = new JPanel(new FlowLayout());
        JLabel value=new JLabel();
        inner_panel.add(new JLabel(sensor_name+" = "));
        inner_panel.add(value);
        inner_panel.add(new JLabel(sensor_unit));
        this.addJLabelToMap(sensor_name,value);
        return inner_panel;
    }
    private void addJLabelToMap(String topic_name,JLabel jlabel){
    
    this.jpanel_map.put(topic_name, jlabel);
    }
    public void doDemo() {
        try {
            subscriber = new MqttClient(this.broker, "Sending");
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
            String[] tokens = topic.split("[/]");
            
            System.out.println(tokens[1] + message);
            this.jpanel_map.get(tokens[1]).setText(message.toString());
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
    }
    
    private void JsonInit(String file_path){
        //reading from file, if succesful creates JSON object
        File file = new File(file_path);
        String content;
        JSONObject device=new JSONObject(); 
        try {
            content = FileUtils.readFileToString(file, "utf-8");
            device = new JSONObject(content);  
        } catch (IOException ex) {
            Logger.getLogger(mqttSubGui.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        //initialization of device name
        this.device_name=device.getString("Device_name");
        
        //initialization of broker address
        this.broker=device.getString("Broker");
        
        //initialization of sensors array
        JSONArray sensors_json_array = device.getJSONArray("Sensors");
        for (int i = 0; i < sensors_json_array.length(); i++) {
            ArrayList<String> sensor_info=new ArrayList<>();
            JSONObject temp_object = sensors_json_array.getJSONObject(i);
            this.list_of_JPanels.add(createSingleSensorPanel(temp_object.getString("sensor_name"),temp_object.getString("unit")));
        }
        
    };
    public static void main(String[] args) throws Exception {
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                            ssp = new mqttSubGui("./mjerac_protoka_vode.json");
                    }
            });
    }
}
    

