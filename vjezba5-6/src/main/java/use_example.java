import java.util.logging.Level;
import java.util.logging.Logger;


public class use_example {
    

    public static void main(String[] args) {
        Device device_1=new Device("mjerac protoka vode","tcp://192.168.1.34","sime","tnnn");
        device_1.add_sensor(new Sensor("Trenutna temperatura vode",-3266,3266,2,"°C",device_1));
        device_1.add_sensor(new Sensor("Trenutni tlak vode",0,65,1,"PSI",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 1 min",0,65336,5,"m^3",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 10 min",0,65336,5,"m^3",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 1 sat",0,65336,5,"m^3",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 1 dan",0,65336,5,"m^3",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 1 tjedan",0,65336,10,"m^3",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 1 mjesec",0,65336,10,"m^3",device_1));
        device_1.add_sensor(new Sensor("Potrosnja u zadnjih 1 godinu",0,65336,10,"m^3",device_1));
        
        device_1.export_to_json_file("./mjerac_protoka_vode.json");
        Device device_2=new Device("./mjerac_protoka_vode.json");
        device_2.start();
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(use_example.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        //device_2.stop();
        
        }
    
}