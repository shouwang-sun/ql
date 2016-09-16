package com.hiekn.bridgehealth.error;

public class ErrorInfo {
	public static final Integer SUCCESS_CODE = 200;
	public static final String SUCCESS_MSG = "成功";
	
	public static final Integer JSON_TRANSFROM_ERROR_CODE = 401;
	public static final String JSON_TRANSFROM_ERROR_MSG = "JSON 转化失败";
	
	public static final int MYSQL_SERVER_ERROR_CODE = 511;
	public static final String MYSQL_SERVER_ERROR_MSG = "数据库错误";
	
	public static final int FILE_UPLOAD_ERROR_CODE = 423;
	public static final String FILE_UPLOAD_ERROR_MSG = "文件上传失败";
	
	public static final int FILE_DOWNLOAD_ERROR_CODE = 424;
	public static final String FILE_DOWNLOAD_ERROR_MSG = "文件下载失败";
	
}
