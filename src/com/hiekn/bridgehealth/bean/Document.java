package com.hiekn.bridgehealth.bean;

import java.util.Date;

public class Document {
    private Integer id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private String fileUrl;

    private Date uploadTime;
    	
    public Document() {
    	
	}
	
	public Document(String fileName, String fileType,Long fileSize,String fileUrl) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
	}
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

    
}