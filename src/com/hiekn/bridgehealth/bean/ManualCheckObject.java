package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class ManualCheckObject {
    private Integer id;

    private String name;

    private Date lastUpdateTime;
    
    private String originName;
    
    private Integer manualCheckFieldId;
    
    public ManualCheckObject(){}
    
    public ManualCheckObject(Integer id,String name,String originName){
    	this.id = id;
    	this.name = name;
    	this.originName = originName;
    }

    public ManualCheckObject(String name,Integer manualCheckFieldId,String originName){
    	this.name = name;
    	this.originName = originName;
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

	/**
	 * @return the manualCheckFieldId
	 */
	public Integer getManualCheckFieldId() {
		return manualCheckFieldId;
	}

	/**
	 * @param manualCheckFieldId the manualCheckFieldId to set
	 */
	public void setManualCheckFieldId(Integer manualCheckFieldId) {
		this.manualCheckFieldId = manualCheckFieldId;
	}
     
	
}