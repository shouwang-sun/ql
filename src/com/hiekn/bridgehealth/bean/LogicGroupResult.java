package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class LogicGroupResult {
    private Integer id;

    private Double result;

    private Long addTime;
    
    private Integer bridgeId;

    private Integer logicGroupId;

    private Integer logicGroupOutputId;

    private String picSrc;

    private String outString;
    
    private String threshold;
    
    private LogicGroupOutput logicGroupOutput;
    
    public LogicGroupResult(){
    	
    }

    public LogicGroupResult(Double result,Long addTime,Integer bridgeId,Integer logicGroupId,Integer logicGroupOutputId,String picSrc,String outString,String threshold){
    	this.result = result;
    	this.addTime = addTime;
    	this.bridgeId = bridgeId;
    	this.logicGroupId = logicGroupId;
    	this.logicGroupOutputId = logicGroupId;
    	this.picSrc = picSrc;
    	this.outString = outString;
    	this.threshold = threshold;
    }

    public LogicGroupResult(Integer bridgeId,Integer logicGroupId,Integer logicGroupOutputId){
    	this.bridgeId = bridgeId;
    	this.logicGroupId = logicGroupId;
    	this.logicGroupOutputId = logicGroupOutputId;
    }
    
    
    public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getResult() {
        return result;
    }

    public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public void setResult(Double result) {
        this.result = result;
    }

    public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
	}

	public String getPicSrc() {
		return picSrc;
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

	public void setPicSrc(String picSrc) {
		this.picSrc = picSrc;
	}


	public String getOutString() {
		return outString;
	}

	public void setOutString(String outString) {
		this.outString = outString;
	}

	public LogicGroupOutput getLogicGroupOutput() {
		return logicGroupOutput;
	}

	public void setLogicGroupOutput(LogicGroupOutput logicGroupOutput) {
		this.logicGroupOutput = logicGroupOutput;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
	
	
	
	 
}