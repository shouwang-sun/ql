package com.hiekn.bridgehealth.rest;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.ManualCheckObject;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IManualCheckObjectService;

@Controller("manualCheckObject")
@Path("manualCheckObject")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class ManualCheckObjectRestApi {
	@Autowired
	private IManualCheckObjectService iManualCheckObjectService;
	Gson gson = new Gson();
	@POST
	
	@Path("getManualCheckObjectList")
	public Response getManualCheckObjectList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize){
		RestResp<ManualCheckObject> rs = null;
		try{
			SearchResult<ManualCheckObject> searchResult =  iManualCheckObjectService.getManualCheckObjectList(index, pageSize);
			RestData<ManualCheckObject> restData = new RestData<ManualCheckObject>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<ManualCheckObject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<ManualCheckObject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
		
	}
	
	
	@POST
	@Path("deleteManualCheckObjectByName")
	public Response deleteManualCheckObjectByName(
			@FormParam("params") String params){
		RestResp<ManualCheckObject> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String nameListStr = map.get("nameList");
		String [] nameList = gson.fromJson(nameListStr, String[].class);
		try{
			int count = iManualCheckObjectService.deleteByNameList(nameList);
			rs = new RestResp<ManualCheckObject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<ManualCheckObject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("insertManualCheckObject")
 	public Response insertSensor(@FormParam("params")String params){
 		RestResp<ManualCheckObject> rs = null;
		try {
			ManualCheckObject manualCheckObject = gson.fromJson(params, ManualCheckObject.class);
			manualCheckObject.setLastUpdateTime(new Date());
			ManualCheckObject manualCheckObject_ = iManualCheckObjectService.insertManualCheckObject(manualCheckObject);
			rs = new RestResp<ManualCheckObject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,manualCheckObject_);
		} catch (Exception e) {
			rs = new RestResp<ManualCheckObject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateManualCheckObjectById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<Sensor> rs = null;
		try {
			ManualCheckObject manualCheckObject = gson.fromJson(params, ManualCheckObject.class);
			manualCheckObject.setLastUpdateTime(new Date());
			iManualCheckObjectService.updateManualCheckObjectByName(manualCheckObject);
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
}
