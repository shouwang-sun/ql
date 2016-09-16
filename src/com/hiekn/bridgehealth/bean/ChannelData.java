package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class ChannelData {
    private Integer id;

    private Double value;

    private Long time;

    private Integer sensorChannelId;

    private Integer status;
    
    private String threshold;
    
    private String sensorChannelName;
    
    private Integer bridgeId;
    
    private Integer worksectionId;
    
    private Integer sensorTypeId;

    public ChannelData(){
    	
    }
    
    public ChannelData(Double value,Long time,Integer sensorChannelId,Float threshold,Integer bridgeId,String sensorChannelName){
    	this.value = value;
    	this.time  = time;
    	this.sensorChannelId = sensorChannelId;
    	this.bridgeId = bridgeId;
    	this.sensorChannelName = sensorChannelName;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    
    public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getSensorChannelId() {
		return sensorChannelId;
	}

	public void setSensorChannelId(Integer sensorChannelId) {
		this.sensorChannelId = sensorChannelId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
 

	public String getSensorChannelName() {
		return sensorChannelName;
	}

	public void setSensorChannelName(String sensorChannelName) {
		this.sensorChannelName = sensorChannelName;
	}

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public Integer getWorksectionId() {
		return worksectionId;
	}

	public void setWorksectionId(Integer worksectionId) {
		this.worksectionId = worksectionId;
	}

	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

}