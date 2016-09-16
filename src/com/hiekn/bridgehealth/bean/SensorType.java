package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class SensorType {
    private Integer id;

    private String name;

    private String description;
    
    private Date lastUpdateTime;
    
    private String image;

    public SensorType (){};
    
    public SensorType (String name,String description,String image){
    	this.name = name;
    	this.description = description;
    	this.image = image;
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

    
}