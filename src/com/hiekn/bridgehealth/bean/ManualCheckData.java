package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class ManualCheckData {
    private Integer id;

    private String name;

    private Integer bridgeId;
    
    private Date lastUpdateTime;
    
    public ManualCheckData(){
    	
    }
    
    public ManualCheckData(Integer id, String name, Integer bridgeId){
    	this.id  = id;
    	this.name = name;
    	this.bridgeId = bridgeId;
    }
    
    public ManualCheckData(  String name, Integer bridgeId){
    	this.name = name;
    	this.bridgeId = bridgeId;
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

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
    
    

    
}