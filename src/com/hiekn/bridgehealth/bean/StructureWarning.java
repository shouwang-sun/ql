package com.hiekn.bridgehealth.bean;

import java.util.Date;


public class StructureWarning {
    private Integer id;

    private String name;

    private String threshold;

    private Integer logicGroupId;

    private String description;

    private Integer bridgeId;

    private Integer outputId;
    
    private Integer sensorId;
    
    private Integer sensorChannelId;

    private Date lastUpdateTime;
    
    private String originName;
    
    public StructureWarning(){
    	
    }
    
    public StructureWarning(String name, String threshold,Integer logicGroupId,Integer bridgeId,Integer sensorId,String description,String originName,Integer sensorChannelId){
    	this.name = name;
    	this.sensorId = sensorId;
    	this.threshold = threshold;
    	this.logicGroupId = logicGroupId;
    	this.bridgeId = bridgeId;
    	this.description = description;
    	this.originName = originName;
    	this.sensorChannelId = sensorChannelId;
    }
    
    public StructureWarning(String name, String threshold,Integer logicGroupId,Integer bridgeId,Integer sensorId,String description,Integer id,Integer sensorChannelId){
    	this.name = name;
    	this.threshold = threshold;
    	this.sensorId = sensorId;
    	this.logicGroupId = logicGroupId;
    	this.bridgeId = bridgeId;
    	this.description = description;
    	this.sensorChannelId = sensorChannelId;
    	this.id = id;
    }
    
	public String getOriginName() {
		return originName;
	}
   
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public StructureWarning(Integer id){
    	this.id = id;
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
    
    public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

	public Integer getOutputId() {
		return outputId;
	}

	public void setOutputId(Integer outputId) {
		this.outputId = outputId;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	/**
	 * @return the sensorChannelId
	 */
	public Integer getSensorChannelId() {
		return sensorChannelId;
	}

	/**
	 * @param sensorChannelId the sensorChannelId to set
	 */
	public void setSensorChannelId(Integer sensorChannelId) {
		this.sensorChannelId = sensorChannelId;
	}
	
	
}