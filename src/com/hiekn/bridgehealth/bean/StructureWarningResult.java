package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class  StructureWarningResult{
    private Integer id;

    private Double value;

    private Date startTime;
    
    private Integer dealResult;
    
    private String description;

    private Integer level;

    private Integer bridgeId;
    
    private Integer logicGroupId;
    
    private Integer structureWarningId;
    
    private Integer sensorId;
    
    private Sensor sensor;
    
    private Integer sensorChannelId;
    
    private Date lastUpdateTime;
    
    private String threshold;
    
    private LogicGroup logicGroup;
    
    private Integer logicGroupOutputId;
    
    private LogicGroupOutput logicGroupOutput;
    
    private SensorChannel sensorChannel;
    
    private StructureWarning structureWarning;
    
    public StructureWarningResult(){
    	
    }
    
    public StructureWarningResult(Integer id,String description,Integer dealResult){
    	this.id = id;
    	this.description = description;
    	this.dealResult = dealResult;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public LogicGroupOutput getLogicGroupOutput() {
		return logicGroupOutput;
	}

	public void setLogicGroupOutput(LogicGroupOutput logicGroupOutput) {
		this.logicGroupOutput = logicGroupOutput;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getDealResult() {
		return dealResult;
	}

	public void setDealResult(Integer dealResult) {
		this.dealResult = dealResult;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
	}

	public Integer getStructureWarningId() {
		return structureWarningId;
	}

	public void setStructureWarningId(Integer structureWarningId) {
		this.structureWarningId = structureWarningId;
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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the logicGroupOutputId
	 */
	public Integer getLogicGroupOutputId() {
		return logicGroupOutputId;
	}

	/**
	 * @param logicGroupOutputId the logicGroupOutputId to set
	 */
	public void setLogicGroupOutputId(Integer logicGroupOutputId) {
		this.logicGroupOutputId = logicGroupOutputId;
	}

	/**
	 * @return the logicGroup
	 */
	public LogicGroup getLogicGroup() {
		return logicGroup;
	}

	/**
	 * @param logicGroup the logicGroup to set
	 */
	public void setLogicGroup(LogicGroup logicGroup) {
		this.logicGroup = logicGroup;
	}

	/**
	 * @return the sensorChannel
	 */
	public SensorChannel getSensorChannel() {
		return sensorChannel;
	}

	/**
	 * @param sensorChannel the sensorChannel to set
	 */
	public void setSensorChannel(SensorChannel sensorChannel) {
		this.sensorChannel = sensorChannel;
	}

	/**
	 * @return the structureWarning
	 */
	public StructureWarning getStructureWarning() {
		return structureWarning;
	}

	/**
	 * @param structureWarning the structureWarning to set
	 */
	public void setStructureWarning(StructureWarning structureWarning) {
		this.structureWarning = structureWarning;
	}
	
	
    
}