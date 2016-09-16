package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class ManualCheckField {
    private Integer id;

    private String name;

    private String type;

    private String value;
    
    private Date lastUpdateTime;
    
    private String originName;
    
    private Integer manualCheckDataId;
    
    private Integer manualCheckObjectId;
    
    private ManualCheckObject manualCheckObject;
    
    public ManualCheckField(){}
    
    public ManualCheckField(Integer id,String name,String type,String value,Integer manualCheckDataId){
    	this.id = id;
    	this.name = name;
    	this.type = type;
    	this.value = value;
    	this.manualCheckDataId =manualCheckDataId;
    }

    public ManualCheckField(String name,String type,String value,Integer manualCheckDataId){
    	this.name = name;
    	this.type = type;
    	this.value = value;
    	this.manualCheckDataId =manualCheckDataId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public Integer getManualCheckDataId() {
		return manualCheckDataId;
	}

	public void setManualCheckDataId(Integer manualCheckDataId) {
		this.manualCheckDataId = manualCheckDataId;
	}

	public ManualCheckObject getManualCheckObject() {
		return manualCheckObject;
	}

	public void setManualCheckObject(ManualCheckObject manualCheckObject) {
		this.manualCheckObject = manualCheckObject;
	}

	public Integer getManualCheckObjectId() {
		return manualCheckObjectId;
	}

	public void setManualCheckObjectId(Integer manualCheckObjectId) {
		this.manualCheckObjectId = manualCheckObjectId;
	}
	
}