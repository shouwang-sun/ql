package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class LogicGroupConstant {
    private Integer id;

    private Integer logicGroupId;
    
    private String name;
    
    private String description;
    
    private String value;
    
    private Date lastUpdateTime;
    
    public LogicGroupConstant(){};
    
    public LogicGroupConstant(Integer logicGroupId,String name,String description,String value){
    	this.logicGroupId = logicGroupId;
    	this.name = name;
    	this.description = description;
    	this.value = value;
    };
    
    public LogicGroupConstant(Integer id,Integer logicGroupId,String name,String description,String value){
    	this.id = id;
    	this.logicGroupId = logicGroupId;
    	this.name = name;
    	this.description = description;
    	this.value = value;
    };
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
 
	public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
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