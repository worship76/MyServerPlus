package com.nuc.server.service.impl;

import com.nuc.server.bean.Sensor;
import com.nuc.server.dao.SensorDao;
import com.nuc.server.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sensorService")
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorDao sensorDao;

    @Override
    public Long getAllSensor(Map<String,Object> map) {
        return sensorDao.getAllSensor(map);
    }

    @Override
    public List<Sensor> selectSensor(Map<String, Object> map) {
        return sensorDao.selectSensor(map);
    }

    @Override
    public List<Sensor> selectSensorAndroid(String date,String sid) {
        return sensorDao.selectSensorAndroid(date,sid);
    }


    @Override
    public int deleteSensor(Integer id) {
        return sensorDao.deleteSensor(id);
    }
}
