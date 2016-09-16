package com.hiekn.bridgehealth.bean;

import java.util.Date;


public class SensorTypeAttribute {
    private Integer id;

    private String name;

    private String value;
    
    private String type;
    
    private Integer sensorTypeId;
    
    private Date  lastUpdateTime;
    
    private String originName;
    
    public SensorTypeAttribute(){
    	
    }
    
    public SensorTypeAttribute(String name,String value,String type,Integer sensorTypeId){
    	this.name = name;
    	this.value = value;
    	this.type = type;
    	this.sensorTypeId = sensorTypeId;
    }
    
    public SensorTypeAttribute(String name,String type){
    	this.name = name;
    	this.type = type;
    }
    
    
    public SensorTypeAttribute(Integer id ,String value){
    	this.id = id;
    	this.value = value;
    }
    /**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
    
	
}