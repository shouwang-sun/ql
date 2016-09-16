package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class ChannelDataHistoryFile {
    private Integer id;

    private String name;

    private String datTypeUrl;

    private String zipTypeUrl;

    private Long fileSize;

    private Long dataSize;

    private Date startTime;

    private Date endTime;

    private Integer sensorChannelId;
    
    private Integer status;
    
    private Integer sensorId;
    
    private Integer sensorTypeId;

    private Integer workSectionId;

    private Integer bridgeId;

    
    public ChannelDataHistoryFile(){
    	
    }
    
    public ChannelDataHistoryFile(Integer bridgeId,Integer sensorTypeId,Integer workSectionId,Integer sensorId, Integer sensorChannelId){
    	this.bridgeId = bridgeId;
    	this.sensorTypeId = sensorTypeId;
    	this.workSectionId = workSectionId;
    	this.sensorId = sensorId;
    	this.sensorChannelId = sensorChannelId;
    } 
    
    public ChannelDataHistoryFile(Integer bridgeId ,Long dataSize,Date startTime,Integer sensorChannelId,String name,String datTypeUrl){
    	this.bridgeId = bridgeId;
    	this.dataSize = dataSize;
    	this.startTime = startTime;
    	this.sensorChannelId = sensorChannelId;
    	this.name = name;
    	this.datTypeUrl = datTypeUrl;
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

     
    public String getDatTypeUrl() {
		return datTypeUrl;
	}

	public void setDatTypeUrl(String datTypeUrl) {
		this.datTypeUrl = datTypeUrl;
	}

	public String getZipTypeUrl() {
		return zipTypeUrl;
	}

	public void setZipTypeUrl(String zipTypeUrl) {
		this.zipTypeUrl = zipTypeUrl;
	}


	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getDataSize() {
		return dataSize;
	}

	public void setDataSize(Long dataSize) {
		this.dataSize = dataSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getSensorChannelId() {
		return sensorChannelId;
	}

	public void setSensorChannelId(Integer sensorChannelId) {
		this.sensorChannelId = sensorChannelId;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public Integer getWorkSectionId() {
		return workSectionId;
	}

	public void setWorkSectionId(Integer workSectionId) {
		this.workSectionId = workSectionId;
	}

	public Integer getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(Integer bridgeId) {
		this.bridgeId = bridgeId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}