package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class LogicGroupOutput {
    private Integer id;
    private String name;
    private Integer logicGroupId;
    private String unit;
    private Date lastUpdateTime;
    private String threshold;
    private String description;

    public LogicGroupOutput(){
    	
    }
    
    public LogicGroupOutput(String name,String unit,Integer logicGroupId){
    	this.name = name;
    	this.unit = unit;
    	this.logicGroupId = logicGroupId;
    }
    
    public LogicGroupOutput(Integer id,String name,String unit,Integer logicGroupId){
    	this.id = id;
    	this.name = name;
    	this.unit = unit;
    	this.logicGroupId = logicGroupId;
    }
    
    
    public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}



	public LogicGroupOutput(String name){
    	this.name = name;
    }
    
	
    public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
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

    public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
	}

	public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}