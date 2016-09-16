package com.hiekn.bridgehealth.service;

import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;
/**
 * 
 * @author govert.peng
 * 
 * @since 2014/12/16
 *
 */
public interface ISensorService {
	
	public SearchResult<Sensor> getSensorList(Integer index,Integer pageSize,Sensor sensor)throws Exception;

	public Sensor insertSensor(Sensor sensor)throws Exception;
	
	public void updateSensor(Sensor sensor)throws Exception;
	 
	public Sensor findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids) throws Exception;
	
	public int deleteByBridgeIds(int [] bridgeIds);
	
	public int deleteByWorkSectionIds(int [] workSectionIds);
}
