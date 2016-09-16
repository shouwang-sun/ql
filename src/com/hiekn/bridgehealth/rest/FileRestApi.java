package com.hiekn.bridgehealth.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IFileService;
import com.hiekn.bridgehealth.util.CommonResource;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Controller("file")
@Path("file")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class FileRestApi {
	@Autowired
	private IFileService fileService;
	
	Gson gson = new Gson();
	
	@POST
	@Path("uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json;charset=utf-8")
	public Response upload(@FormDataParam("fileData") InputStream fileIn, 
			@FormDataParam("fileData") FormDataContentDisposition fileInfo) throws UnsupportedEncodingException {
        
//		type = 1 pic; 
//		type = 2 file; 
//		type = 3 algorithm; 
//		type = 4 history; 
		
		String finalFileName = fileInfo.getFileName();
		finalFileName = new String(finalFileName.getBytes("ISO8859-1"),"UTF-8");
		RestResp resp = null;
		String fileLocation = CommonResource.FILE_ABSOLUTE_FILE_PATH;
		 
		try {
			String fileName = fileService.upload(fileIn, fileInfo,fileLocation,finalFileName);
			resp = new RestResp(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,fileName);
		} catch (Exception e) {
			resp = new RestResp(ErrorInfo.FILE_UPLOAD_ERROR_CODE,ErrorInfo.FILE_UPLOAD_ERROR_MSG);
		}

		return Response.ok().entity(resp).build();
	}
	
	
	@POST
	@Path("uploadSensorChannelData")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json;charset=utf-8")
	public Response uploadSensorChannelData(@FormDataParam("fileData") InputStream fileIn, 
			@FormDataParam("fileData") FormDataContentDisposition fileInfo) throws UnsupportedEncodingException {
        
//		type = 1 pic; 
//		type = 2 file; 
//		type = 3 algorithm; 
//		type = 4 history; 
		
		String finalFileName = fileInfo.getFileName();
		finalFileName = new String(finalFileName.getBytes("ISO8859-1"),"UTF-8");
		RestResp resp = null;
		String fileLocation = CommonResource.MONITOR_SENSORCHANNEL_STRING;
		 
		try {
			String fileName = fileService.upload(fileIn, fileInfo,fileLocation,finalFileName);
			resp = new RestResp(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,fileName);
		} catch (Exception e) {
			resp = new RestResp(ErrorInfo.FILE_UPLOAD_ERROR_CODE,ErrorInfo.FILE_UPLOAD_ERROR_MSG);
		}

		return Response.ok().entity(resp).build();
	}

	@GET
	@Path("downloadHistoryFiles")
//	@FormParam("params") String params
	public Response downloadHistoryFiles(@QueryParam("url") String url){
//		Map<String,String> map = gson.fromJson(params,Map.class);
//		String filePath = CommonResource.historyFileDir +  (String) map.get("filePath");
		String filePath = CommonResource.FILE_ABSOLUTE_FILE_PATH +  url;
		RestResp resp = null;
		File file = new File(filePath);
		byte[] buffer;
		try {
			buffer = fileService.downloadFiles(file);
			String fileName = file.getName();
			String encodedFileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			resp = new RestResp(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			return  Response.ok(buffer,MediaType.TEXT_HTML).header("Content-Disposition", "attachment; filename="+encodedFileName)
					.header("Content-Encoding","utf-8").build();
		} catch (IOException e) {
			resp = new RestResp(ErrorInfo.FILE_DOWNLOAD_ERROR_CODE,ErrorInfo.FILE_DOWNLOAD_ERROR_MSG);
		}
		return Response.ok().entity(resp).build();
	}
}
