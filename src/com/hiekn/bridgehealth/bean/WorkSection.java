package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class WorkSection {
    private Integer id;

    private String name;

    private Float positionX;

    private Float positionY;

    private String description;

    private String image;

    private String sensorLayoutImage;

    private Integer bridgeId;

    private Integer pid;
    
    private Date lastUpdateTime;
    
    private List<Sensor> sensorList;
    
    
    public WorkSection(){
    	
    }
    
    public WorkSection(String name,Float positionX, Float positionY, String description,String image,Integer bridgeId,Integer pid ){
    	this.name = name;
    	this.positionX = positionX;
    	this.positionY = positionY;
    	this.description = description;
    	this.image = image;
    	this.bridgeId = bridgeId;
    	this.pid = pid;
    }

    public WorkSection(Integer id,String name,Float positionX, Float positionY, String description,String image,Integer bridgeId,Integer pid ){
    	this.id = id;
    	this.name = name;
    	this.positionX = positionX;
    	this.positionY = positionY;
    	this.description = description;
    	this.image = image;
    	this.bridgeId = bridgeId;
    	this.pid = pid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSensorLayoutImage() {
        return sensorLayoutImage;
    }

    public void setSensorLayoutImage(String sensorLayoutImage) {
        this.sensorLayoutImage = sensorLayoutImage;
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

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public List<Sensor> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<Sensor> sensorList) {
		this.sensorList = sensorList;
	}

	
}