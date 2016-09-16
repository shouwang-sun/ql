package com.hiekn.bridgehealth.service;

import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.search.SearchResult;
 

public interface ISensorTypeService {
	 
	public SearchResult<SensorType> getSensorTypeList(Integer integer,Integer pageSixe) throws Exception; 

	public SensorType insertSensorType(SensorType senType)throws Exception;
	
	public void updateSensorType(SensorType senType)throws Exception;
	 
	public SensorType findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids);
	
	public List<SensorType>findByIds(Integer [] ids);
}
