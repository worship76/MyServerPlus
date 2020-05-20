package com.nuc.server.controller;

import com.nuc.server.bean.PageBean;
import com.nuc.server.bean.Sensor;
import com.nuc.server.service.SensorService;
import com.nuc.server.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;
    private static final Logger log = Logger.getLogger(UserController.class);// 日志文件

    @RequestMapping("/findByDate")
    public String findByDate(HttpServletResponse response,String date,String sid) throws IOException {
        /*Map<String, Object> map = new HashMap<String, Object>();*/
        List<Sensor> sensors;
        /*map.put("sid", sid);
        map.put("date",date);
        System.out.println(sid);*/
        sensors = sensorService.selectSensorAndroid(date,sid);
        System.out.println(sensors);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        if (sensors.isEmpty()) {
            json.put("status", "-1");
        } else {
            json.put("status","1");
            json.put("data",sensors);
        }
        out.write(json.toString());
        out.flush();
        out.close();
        System.out.println(json.toString());
        return json.toString();
    }

    @RequestMapping("/all")
    public String getAllSensor(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows,Sensor s_sensor, HttpServletResponse response) throws Exception{
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));

        Map<String, Object> map = new HashMap<String, Object>();

        List<Sensor> sensors;

        map.put("date",s_sensor.getDate());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());


        if (UserController.loginUserNow.getRole().equals("员工")){
            if (s_sensor.getSid() == null){
                map.put("sid",UserController.loginUserNow.getSid());
            } else {
                if (UserController.loginUserNow.getSid() == s_sensor.getSid()){
                    map.put("sid",s_sensor.getSid());
                }
                else {
                    map.put("sid",999);
                }

            }
        }
        else {
            if(s_sensor.getSid() == null){
                map.put("sid",UserController.loginUserNow.getSid());
                System.out.println(map);
            } else {
                map.put("sid",s_sensor.getSid());
            }
        }

        sensors = sensorService.selectSensor(map);
        Long total = sensorService.getAllSensor(map);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(sensors);
        result.put("rows", jsonArray);
        result.put("total", total);
        //System.out.println(total);
        //System.out.println(result);
        log.info("request: sensor/all , map: " + map.toString());
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception{
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            sensorService.deleteSensor(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        log.info("request: user/delete , ids: " + ids);
        ResponseUtil.write(response, result);
        return null;
    }
}
