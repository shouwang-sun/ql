package com.hiekn.bridgehealth.service;

import java.util.List;

import com.hiekn.bridgehealth.bean.SensorTypeAttribute;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface ISensorTypeAttributeService {
	public SearchResult<SensorTypeAttribute> getSensorTypeAttributeList(Integer index,Integer pageSiz,Integer sensorTypeId)throws Exception;
	
	public SensorTypeAttribute insertSensorTypeAttribute (SensorTypeAttribute attribute)throws Exception;
	
	public int deleteByNameList(String [] nameList);
	
	public int deleteByIds(int [] ids);
	
	public int deleteBySensorTypeIds(int [] bridgeIds);
	
	public void updateSensorTypeAttributeById(SensorTypeAttribute attribute)throws Exception;
	
	public void updateSensorTypeAttributeByName(SensorTypeAttribute attribute)throws Exception;
}
