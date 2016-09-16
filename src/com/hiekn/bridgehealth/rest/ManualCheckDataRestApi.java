package com.hiekn.bridgehealth.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.ManualCheckField;
import com.hiekn.bridgehealth.bean.ManualCheckData;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IManualCheckDataService;

@Controller("manualCheckData")
@Path("manualCheckData")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class ManualCheckDataRestApi {	
	@Autowired
	private IManualCheckDataService manualCheckDataService;
	Gson gson = new Gson();
	@POST
	@Path("getManualCheckDataList")
	public Response getManualCheckList(
				@QueryParam("index") Integer index,
				@QueryParam("pageSize") Integer pageSize,
				@FormParam("params")String params) throws Exception{
		
 		RestResp<ManualCheckData> rs = null;
		Map<String,String> map = gson.fromJson(params,Map.class);
		Integer bridgeId;
		Date createTime = null;
		Date endTime = null;
		
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!((String) map.get("createTime")).equals("-1")){
			createTime = sdf.parse(map.get("createTime"));
		}
		
		if(!((String) map.get("endTime")).equals("-1")){
			endTime = sdf.parse(map.get("endTime"));
		}
		
		if( map.get("bridgeId").equals("-1")){
			bridgeId = null;
		}else{
			bridgeId = Integer.parseInt((String)map.get("bridgeId"));
		}
		
		try{
			SearchResult<ManualCheckData> srResult = manualCheckDataService.getManualCheckDataList(index, pageSize, createTime, endTime,bridgeId);
			RestData<ManualCheckField> restData = new RestData(srResult.getRsList(),srResult.getRsCount());
			rs = new RestResp<ManualCheckData>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch(Exception e){
			rs = new RestResp<ManualCheckData>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}  
		
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("insertManualCheckData")
 	public Response insertManualCheckData(@FormParam("params")String params){
 		RestResp<ManualCheckData> rs = null;
		try {
			ManualCheckData manualCheckData = gson.fromJson(params, ManualCheckData.class);
			manualCheckData.setLastUpdateTime(new Date());
			ManualCheckData manualCheckData_ = manualCheckDataService.insertManualCheckData(manualCheckData);
			rs = new RestResp<ManualCheckData>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,manualCheckData_);
		} catch (Exception e) {
			rs = new RestResp<ManualCheckData>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findManualCheckDataById")
 	public Response findManualCheckDataById(@QueryParam("id")Integer id){
 		RestResp<ManualCheckData> rs = null;
		try {
			ManualCheckData manualCheckData= manualCheckDataService.findById(id);
			rs = new RestResp<ManualCheckData>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,manualCheckData);
		} catch (Exception e) {
			rs = new RestResp<ManualCheckData>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateManualCheckDataById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<ManualCheckData> rs = null;
		try {
			ManualCheckData manualCheckData = gson.fromJson(params, ManualCheckData.class);
			manualCheckData.setLastUpdateTime(new Date());
			manualCheckDataService.updateManualCheckDataById(manualCheckData);
			rs = new RestResp<ManualCheckData>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<ManualCheckData>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteManualCheckDataByIds")
	public Response deleteManualCheckDataByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<ManualCheckData> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  manualCheckDataService.deleteByIds(ids);
			rs = new RestResp<ManualCheckData>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<ManualCheckData>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
