package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.SensorChannel;


public interface SensorChannelMapper {
	List<SensorChannel> sensorChannels (Integer sensorId);
}
