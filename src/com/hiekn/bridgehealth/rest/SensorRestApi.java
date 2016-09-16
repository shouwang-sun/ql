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
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ISensorService;

@Path("sensor")
@Controller("sensor")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class SensorRestApi {
	@Autowired
	private ISensorService iSensorService;
	Gson gson = new Gson();
	
	@POST
	@Path("getSensorList")
	public Response getSensorList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		
		RestResp<Sensor> rs = null;
		Sensor sensor = gson.fromJson(params, Sensor.class);
		try{
			SearchResult<Sensor> searchResult =  iSensorService.getSensorList(index, pageSize, sensor);
			RestData<Sensor> restData = new RestData<Sensor>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("insertSensor")
 	public Response insertSensor(@FormParam("params")String params){
 		RestResp<Sensor> rs = null;
		try {
			Sensor sensor = gson.fromJson(params, Sensor.class);
			sensor.setLastUpdateTime(new Date());
			Sensor sensor_ = iSensorService.insertSensor(sensor);
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sensor_);
		} catch (Exception e) {
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findSensorById")
 	public Response findSensorById(@QueryParam("id")Integer id){
 		RestResp<Sensor> rs = null;
		try {
			Sensor sensor= iSensorService.findById(id);
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sensor);
		} catch (Exception e) {
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateSensorById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<Sensor> rs = null;
		try {
			Sensor sensor = gson.fromJson(params, Sensor.class);
			sensor.setLastUpdateTime(new Date());
			iSensorService.updateSensor(sensor);
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteSensorByIds")
	public Response deleteSensorByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<Sensor> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String idsStr = map.get("ids");
 		int [] ids = gson.fromJson(idsStr, int[].class);
		try{
			int count =  iSensorService.deleteByIds(ids);
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
