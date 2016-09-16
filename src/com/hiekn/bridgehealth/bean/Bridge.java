package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;

public class Bridge {
    private Integer id;

    private String name;

    private Float positionX;

    private Float positionY;

    private String image;

    private String description;

    private Integer status;
    
    private Date lastUpdateTime;
    
    private List<WorkSection>workSectionList;
    
    public Bridge(){
    	
    }
    
    public Bridge(String name,Float positionX ,Float positionY,String image,String description ){
    	this.name = name;
    	this.positionY = positionY;
    	this.positionY = positionY;
    	this.image = image;
    	this.description = description;
    }
    public Bridge(Integer id, String name,Float positionX ,Float positionY,String image,String description ){
    	this.id = id;
    	this.name = name;
    	this.positionY = positionY;
    	this.positionY = positionY;
    	this.image = image;
    	this.description = description;
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

	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<WorkSection> getWorkSectionList() {
		return workSectionList;
	}

	public void setWorkSectionList(List<WorkSection> workSectionList) {
		this.workSectionList = workSectionList;
	}
	
    
}