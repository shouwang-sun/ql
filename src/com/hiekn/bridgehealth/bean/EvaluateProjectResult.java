package com.hiekn.bridgehealth.bean;

import java.util.Date;
import java.util.List;

public class EvaluateProjectResult {
    private Integer id;

    private Integer evaluateProjectId;
    
    private Integer evaluateProjectPid;

    private Double healthyValue;

    private Integer level;

    private String advice;

    private String name;

    private Integer projectYear;

    private Integer projectMonth;

    private Integer bridgeId;
    
    private Date lastUpdateTime;
    
    private List<EvaluateProjectResult> children;
    
    private EvaluateProject evaluateProject;
    
    public EvaluateProjectResult(){};
    
    public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
	public Integer getProjectYear() {
		return projectYear;
	}

	public void setProjectYear(Integer projectYear) {
		this.projectYear = projectYear;
	}

	public Integer getProjectMonth() {
		return projectMonth;
	}

	public void setProjectMonth(Integer projectMonth) {
		this.projectMonth = projectMonth;
	}

	public Integer getEvaluateProjectId() {
		return evaluateProjectId;
	}

	public void setEvaluateProjectId(Integer evaluateProjectId) {
		this.evaluateProjectId = evaluateProjectId;
	}
	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getHealthyValue() {
		return healthyValue;
	}

	public void setHealthyValue(Double healthyValue) {
		this.healthyValue = healthyValue;
	}

	public Integer getEvaluateProjectPid() {
		return evaluateProjectPid;
	}

	public void setEvaluateProjectPid(Integer evaluateProjectPid) {
		this.evaluateProjectPid = evaluateProjectPid;
	}

	
	/**
	 * @return the children
	 */
	public List<EvaluateProjectResult> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<EvaluateProjectResult> children) {
		this.children = children;
	}

	/**
	 * @return the evaluateProject
	 */
	public EvaluateProject getEvaluateProject() {
		return evaluateProject;
	}

	/**
	 * @param evaluateProject the evaluateProject to set
	 */
	public void setEvaluateProject(EvaluateProject evaluateProject) {
		this.evaluateProject = evaluateProject;
	}
	
}