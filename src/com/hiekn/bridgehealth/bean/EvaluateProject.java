package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;

public class EvaluateProject {
    private Integer id;

    private String name;

    private Integer pid;

    private Float healthyRate;
    
    private String description;

    private Integer bridgeId;

    private Integer logicGroupId;

    private Integer outputId;

    private Date lastUpdateTime;
    
    private LogicGroup logicGroup;
    
    private EvaluateProject parentEvaluateProject;
    
    private String threshold;
    
    public EvaluateProject(){
    	
    }
//   查找
    public EvaluateProject(Integer pid,Integer bridgeId){
    	this.pid = pid;
    	this.bridgeId = bridgeId;
    }
//    健康性权值
    public EvaluateProject(Integer id,Float healthyRate){
    	this.id = id;
    	this.healthyRate = healthyRate;
    }
    
//    更新
    public EvaluateProject(String threshold,String name,Float healthyRate,String description,Integer bridgeId,Integer logicGroupId,Integer outputId,Integer id,Integer pid){
    	this.threshold = threshold;
    	this.name = name;
    	this.healthyRate = healthyRate;
    	this.description = description;
    	this.bridgeId = bridgeId;
    	this.logicGroupId = logicGroupId;
    	this.outputId = outputId;
    	this.pid = pid;
    	this.id = id;
    }
    
//    添加
    public EvaluateProject(String threshold,String name,Float healthyRate,String description,Integer bridgeId,Integer logicGroupId,Integer outputId,Integer pid){
    	this.threshold = threshold;
    	this.name = name;
    	this.healthyRate = healthyRate;
    	this.description = description;
    	this.bridgeId = bridgeId;
    	this.logicGroupId = logicGroupId;
    	this.outputId = outputId;
    	this.pid = pid;
    }
    
    public Integer getId() {
    	
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

	public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
	}

	public Integer getOutputId() {
		return outputId;
	}

	public void setOutputId(Integer outputId) {
		this.outputId = outputId;
	}
 
	public EvaluateProject getParentEvaluateProject() {
		return parentEvaluateProject;
	}
	 
	public void setParentEvaluateProject(EvaluateProject parentEvaluateProject) {
		this.parentEvaluateProject = parentEvaluateProject;
	}
 
	public Float getHealthyRate() {
		return healthyRate;
	}
	/**
	 * @param healthyRate the healthyRate to set
	 */
	public void setHealthyRate(Float healthyRate) {
		this.healthyRate = healthyRate;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	
	
}