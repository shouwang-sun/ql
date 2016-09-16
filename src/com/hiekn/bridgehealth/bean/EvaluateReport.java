package com.hiekn.bridgehealth.bean;

public class EvaluateReport {
    private Integer id;

    private String name;

    private Integer year;

    private Integer month;

    private Integer bridgeId;

    private String totalContent;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public String getTotalContent() {
		return totalContent;
	}

	public void setTotalContent(String totalContent) {
		this.totalContent = totalContent;
	}

}