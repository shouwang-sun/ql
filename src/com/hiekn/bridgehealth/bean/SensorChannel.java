package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;

import org.python.antlr.ast.Str;

public class SensorChannel {
    private Integer id;

    private String name;

    private String description;

    private Integer sensorId;
    
    private Integer sensorTypeId;

    private Integer workSectionId;

    private Integer bridgeId;
    
    private Date lastUpdateTime;
    
    private Sensor sensor;
    
    private String threshold;
    
    private List<ChannelData> channelDataList ;
    
    private ChannelData latestChannelData;
    
    private String unit;
    
    public SensorChannel (){
    	
    }
    
    public SensorChannel (String name,String description,Integer sensorId,Integer workSectionId,Integer bridgeId,Integer sensorTypeId){
    	this.name = name;
    	this.description = description;
    	this.sensorId = sensorId;
    	this.workSectionId = workSectionId;
    	this.sensorTypeId = sensorTypeId;
    	this.bridgeId =bridgeId;
    }
    
    public SensorChannel (Integer id,String name,String description,Integer sensorId,Integer workSectionId,Integer bridgeId,Integer sensorTypeId){
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.sensorId = sensorId;
    	this.workSectionId = workSectionId;
    	this.sensorTypeId = sensorTypeId;
    	this.bridgeId =bridgeId;
    }

	/**
	 * @return the sensor
	 */
	public Sensor getSensor() {
		return sensor;
	}
    
   public SensorChannel (String name){
    	this.name = name;
    }
 
    public SensorChannel (Integer sensorId){
    	this.sensorId = sensorId;
    }
	    
	/**
	 * @param sensor the sensor to set
	 */
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public Integer getWorkSectionId() {
		return workSectionId;
	}

	public void setWorkSectionId(Integer workSectionId) {
		this.workSectionId = workSectionId;
	}

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	/**
	 * @return the threshold
	 */
	public String getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public List<ChannelData> getChannelDataList() {
		return channelDataList;
	}

	public void setChannelDataList(List<ChannelData> channelDataList) {
		this.channelDataList = channelDataList;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public ChannelData getLatestChannelData() {
		return latestChannelData;
	}

	public void setLatestChannelData(ChannelData latestChannelData) {
		this.latestChannelData = latestChannelData;
	}

	
}