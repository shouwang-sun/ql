package com.hiekn.bridgehealth.rest;

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
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ISensorTypeService;

@Path("sensorType")
@Controller("sensorType")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class SensorTypeRestApi {
	@Autowired
	private ISensorTypeService iSensorTypeService;
	Gson gson = new Gson();
	@POST
	@Path("getSensorTypeList")
	public Response getSensorTypeList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize){
		
		RestResp<SensorType> rs = null;
		try{
			SearchResult<SensorType> searchResult =  iSensorTypeService.getSensorTypeList(index, pageSize);
			RestData<SensorType> restData = new RestData<SensorType>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<SensorType>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<SensorType>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("insertSensorType")
 	public Response insertSensorType(@FormParam("params")String params){
 		RestResp<SensorType> rs = null;
		try {
			SensorType sensorType = gson.fromJson(params, SensorType.class);
			sensorType.setLastUpdateTime(new Date());
			SensorType sensorType_ = iSensorTypeService.insertSensorType(sensorType);
			rs = new RestResp<SensorType>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sensorType_);
		} catch (Exception e) {
			rs = new RestResp<SensorType>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findSensorTypeById")
 	public Response findSensorTypeById(@QueryParam("id")Integer id){
 		RestResp<SensorType> rs = null;
		try {
			SensorType sensorType= iSensorTypeService.findById(id);
			rs = new RestResp<SensorType>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sensorType);
		} catch (Exception e) {
			rs = new RestResp<SensorType>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateSensorTypeById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<SensorType> rs = null;
		try {
			SensorType sensorType = gson.fromJson(params, SensorType.class);
			sensorType.setLastUpdateTime(new Date());
			iSensorTypeService.updateSensorType(sensorType);
			rs = new RestResp<SensorType>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<SensorType>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteSensorTypeByIds")
	public Response deleteSensorTypeByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<SensorType> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String idsStr = map.get("ids");
 		int [] ids = gson.fromJson(idsStr, int[].class);
		try{
			int count =  iSensorTypeService.deleteByIds(ids);
			rs = new RestResp<SensorType>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<SensorType>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
