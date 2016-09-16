package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;

public class Sensor {
    private Integer id;

    private String name;

    private String image;

    private Float positionX;

    private Float positionY;

    private String description;

    private Integer sensorTypeId;

    private Integer workSectionId;

    private Integer logicGroupId;
    
    private Integer bridgeId;
    
    private Date lastUpdateTime;
    
    private List<SensorChannel> sensorChannelList;
    
    private SensorType sensorType;
    
    public Sensor(){
    	
    }
    
    public Sensor(String name){
    	this.name = name;
    }
    
    public Sensor(Integer id){
    	this.id = id;
    }
    
    public Sensor(Integer bridgeId,Integer sensorTypeId,Integer workSectionId){
    	this.bridgeId = bridgeId;
    	this.sensorTypeId = sensorTypeId;
    	this.workSectionId = workSectionId;
    }
    
    public Sensor(Integer bridgeId,Integer sensorTypeId,Integer workSectionId,String name,String image,String description,Float positionX,Float positionY){
    	this.bridgeId = bridgeId;
    	this.sensorTypeId = sensorTypeId;
    	this.workSectionId = workSectionId;
    	this.name = name;
    	this.image = image;
    	this.description = description;
    	this.positionX = positionX;
    	this.positionY = positionY;
    }
    
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Float getPositionX() {
		return positionX;
	}

	public void setPositionX(Float positionX) {
		this.positionX = positionX;
	}

	public Float getPositionY() {
		return positionY;
	}

	public void setPositionY(Float positionY) {
		this.positionY = positionY;
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

	public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
	}

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public List<SensorChannel> getSensorChannelList() {
		return sensorChannelList;
	}

	public void setSensorChannelList(List<SensorChannel> sensorChannelList) {
		this.sensorChannelList = sensorChannelList;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

}