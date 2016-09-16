package com.hiekn.bridgehealth.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;
/**
 * 
 * @author govert.peng
 * 
 * @since 2014/12/16
 *
 */
public interface ISensorChannelService {
	
	public SearchResult<SensorChannel> getSensorChannelList(Integer index,Integer pageSize,SensorChannel sensorChannel)throws Exception;
	 
	public SensorChannel insertSensorChannel (SensorChannel sensorChannel)throws Exception;
	
	public int deleteByIds(int [] ids);
	
	public int deleteByBridgeIds(int [] bridgeIds);
	
	public int deleteByWorkSectionIds(int [] worksectionIds);
	
	public int deleteBySensorIds(int [] sensorIds);
	
	public SensorChannel findById(Integer id)throws Exception;
	
	public String findByName(Integer id);
	
	public void updateSensorChannel(SensorChannel sensorChannel)throws Exception;
	
	public void updateThresholdById(Integer id,String threshold)throws Exception;
	
}
