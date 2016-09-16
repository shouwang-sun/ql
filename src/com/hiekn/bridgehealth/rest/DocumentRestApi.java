package com.hiekn.bridgehealth.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
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
import com.hiekn.bridgehealth.bean.Document;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IDocumentService;

@Controller("document")
@Path("document")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class DocumentRestApi {
    @Autowired 
	private IDocumentService iDocumentService;
	Gson gson = new Gson();
	
	@POST
	@Path("getDocumentList")
	public Response getAttributeList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		
		RestResp<Document> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params,Map.class);
			Long startSize = null;
			Long endSize = null;
			String fileType = null;
			if( map.get("startSize") == null ||  map.get("startSize").equals("-1")){
				startSize = null;
			}else{
				startSize = Long.valueOf((String) map.get("startSize"));
			};
			
			if( map.get("endSize") == null ||  map.get("endSize").equals("-1")){
				endSize = null;
			}else{
				endSize = Long.valueOf((String) map.get("endSize"));
			};
			
			if( map.get("endSize") == null ||  map.get("endSize").equals("-1")){
				endSize = null;
			}else{
				endSize = Long.valueOf((String) map.get("endSize"));
			};
			
			if( map.get("fileType") == null ||  map.get("fileType").equals("-1")){
				fileType = null;
			}else{
				fileType =  (String) map.get("fileType");
			};
			
			Date startTime = null;
			Date endTime = null;
			
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
			if(!((String) map.get("startTime")).equals("-1")){
				startTime = sdf.parse((String) map.get("startTime"));
			}
			
			if(!((String) map.get("endTime")).equals("-1")){
				endTime = sdf.parse((String) map.get("endTime"));
			}
			SearchResult<Document> searchResult =  iDocumentService.getDocumentList(index, pageSize, startTime, endTime, startSize, endSize,fileType);
			RestData<Document> restData = new RestData<Document>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<Document>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<Document>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	
	@POST
	@Path("deleteDocumentByIds")
	public Response deleteByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<Document> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  iDocumentService.deleteByIdList(ids);
			rs = new RestResp<Document>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<Document>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("insertDocument")
 	public Response insertBridge(@FormParam("params")String params){
 		RestResp<Document> rs = null;
		try {
			Document document = gson.fromJson(params, Document.class);
			document.setUploadTime(new Date()); 
			Document document_ = iDocumentService.insertDocument(document);
			rs = new RestResp<Document>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,document_);
		} catch (Exception e) {
			 //   System.out.println(e);
			rs = new RestResp<Document>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
	
	@POST
 	@Path("findDocumentTypeList")
 	public Response findDocumentTypeList(){
 		RestResp<Document> rs = null;
		try {
			SearchResult<String> searchResult =  iDocumentService.findDocumentTypeList();
			RestData<String> restData = new RestData<String>(searchResult.getRsList());
			rs = new RestResp<Document>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		} catch (Exception e) {
			 //   System.out.println(e);
			rs = new RestResp<Document>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
	
	
}
