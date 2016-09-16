package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.Sensor;

public interface SensorMapper {
	List<Sensor> sensors (Integer sensorTypeId);
}
