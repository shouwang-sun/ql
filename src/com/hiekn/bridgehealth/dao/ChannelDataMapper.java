package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.ChannelData;

public interface ChannelDataMapper{
		List<ChannelData> findBySensorChannel(Integer sensorChannelId);
}
