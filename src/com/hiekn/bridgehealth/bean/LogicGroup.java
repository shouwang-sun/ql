package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;

import org.python.antlr.ast.Str;

public class LogicGroup {
    private Integer id;

    private String name;
    
    private String code;

    private String description;

    private Integer bridgeId;

    private Date lastUpdateTime;
    
    private Long timeInterval;
    
    private String algorithm;
    
    private String timeIntervalUnit;
    
    private Long nextRunTime;
    
    private List<LogicGroupSensorChannel>logicGroupSensorChannelList;
    
    public LogicGroup(){
    	
    }
    
    public LogicGroup(Integer bridgeId,String name,String code,String description,Long timeInterval,String algorithm,String timeIntervalUnit){
    	this.bridgeId = bridgeId;
    	this.name = name;
    	this.description = description;
    	this.code = code;
    	this.timeInterval = timeInterval;
    	this.algorithm = algorithm;
    	this.timeIntervalUnit = timeIntervalUnit;
    }
    
    public LogicGroup(Integer id,Integer bridgeId,String name,String code, String description,Long timeInterval,String algorithm,String timeIntervalUnit){
    	this.id = id;
    	this.bridgeId = bridgeId;
    	this.name = name;
    	this.description = description;
    	this.code = code;
    	this.timeInterval = timeInterval;
    	this.algorithm = algorithm;
    	this.timeIntervalUnit = timeIntervalUnit;
    }
    
    
    
    
	public List<LogicGroupSensorChannel> getLogicGroupSensorChannelList() {
		return logicGroupSensorChannelList;
	}

	public void setLogicGroupSensorChannelList(
			List<LogicGroupSensorChannel> logicGroupSensorChannelList) {
		this.logicGroupSensorChannelList = logicGroupSensorChannelList;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
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

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public LogicGroup(Integer id){
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public String getTimeIntervalUnit() {
		return timeIntervalUnit;
	}

	public void setTimeIntervalUnit(String timeIntervalUnit) {
		this.timeIntervalUnit = timeIntervalUnit;
	}

	public Long getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public Long getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(Long nextRunTime) {
		this.nextRunTime = nextRunTime;
	}
	
	
}