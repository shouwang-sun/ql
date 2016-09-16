package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.SensorType;

public interface SensorTypeMapper {
	List<SensorType> getByPage(Integer index,Integer pageSize);
	
	List<SensorType> findByBridge(Integer id);
}
