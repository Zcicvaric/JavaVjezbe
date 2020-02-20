package vjezba7.MqttBroker.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vjezba7.MqttBroker.Dto.MqttMessageDto;
import vjezba7.MqttBroker.Interface.IMqttService;



@Controller
@RequestMapping(path="/mqtt")
public class MqttController {

    @Autowired
    private IMqttService mqttService;
    @RequestMapping(path = "/send")
    @ResponseBody
    public ResponseEntity sendMessage(@RequestBody MqttMessageDto mqttMessage) {
        try {
            mqttService.send(mqttMessage);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}