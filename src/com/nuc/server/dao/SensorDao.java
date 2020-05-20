package com.nuc.server.dao;

import com.nuc.server.bean.Sensor;

import java.util.List;
import java.util.Map;

public interface SensorDao {

    public Long getAllSensor(Map<String,Object> map);

    public List<Sensor> selectSensor(Map<String,Object> map);

    public List<Sensor> selectSensorAndroid(String date,String sid);

    public int deleteSensor(Integer id);
}
