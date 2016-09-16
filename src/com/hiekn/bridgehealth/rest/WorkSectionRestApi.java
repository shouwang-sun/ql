package com.hiekn.bridgehealth.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jnr.ffi.annotations.In;

import org.python.antlr.PythonParser.else_clause_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.IChannelDataService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.ISensorService;
import com.hiekn.bridgehealth.service.IWorkSectionService;

@Path("workSection")
@Controller("workSection")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class WorkSectionRestApi {
	@Autowired
	private IWorkSectionService iWorkSectionService;
	@Autowired
	private ISensorService sensorService;
	@Autowired
	private ISensorChannelService sensorChannelService;
	@Autowired
	private IChannelDataService iChannelDataService;
	Gson gson = new Gson();
	@POST
	@Path("getWorkSectionList")
	public Response getAttributeList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		
		RestResp<WorkSection> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer bridgeId ;
		if((String) map.get("bridgeId") == null ||  map.get("bridgeId").equals("-1")){
			bridgeId = null;
		}else{
			bridgeId = Integer.valueOf((String) map.get("bridgeId"));
		};
		 
		try{
			SearchResult<WorkSection> searchResult =  iWorkSectionService.getWorkSectionList(index, pageSize, bridgeId,null);
			RestData<WorkSection> restData = new RestData<WorkSection>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("insertWorkSecton")
 	public Response insertWorkSection(@FormParam("params")String params){
 		RestResp<WorkSection> rs = null;
		try {
			WorkSection workSection = gson.fromJson(params, WorkSection.class);
			workSection.setLastUpdateTime(new Date());
			WorkSection workSection_ = iWorkSectionService.insertWorkSection(workSection);
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,workSection_);
		} catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findWorkSectionById")
 	public Response findById(@QueryParam("id")Integer id){
 		RestResp<WorkSection> rs = null;
		try {
			WorkSection workSection= iWorkSectionService.findById(id);
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,workSection);
		} catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateWorkSectionById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<WorkSection> rs = null;
		try {
			WorkSection workSection = gson.fromJson(params, WorkSection.class);
			workSection.setLastUpdateTime(new Date());
			iWorkSectionService.updateWorkSection(workSection);
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteWorkSectionByIds")
	public Response deleteByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<WorkSection> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String idsStr = map.get("ids");
 		int [] ids = gson.fromJson(idsStr, int[].class);
		try{
			int count =  iWorkSectionService.deleteByIds(ids);
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("showWorksectionNextChildType")
	public Response showNextChildType(
			@FormParam("params") String pid) throws Exception{
		RestResp<WorkSection> rs = null;
		int index = 0;
		int pageSize = 9999;
		
		try{
			SearchResult<WorkSection> searchResult = iWorkSectionService.getWorkSectionList(index, pageSize, null, Integer.valueOf(pid));
			
			if(searchResult.getRsList().size() > 0){
				RestData<WorkSection> restData = new RestData<WorkSection>(searchResult.getRsList(),Long.parseLong("1"));
				rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
			}else{
				Sensor sensor = new Sensor();
				sensor.setWorkSectionId(Integer.valueOf(pid));
				SearchResult<Sensor> searchResultSensor = sensorService.getSensorList(index, pageSize, sensor);
				
				if(searchResultSensor.getRsList().size() > 0){
					RestData<Sensor> restData = new RestData<Sensor>(searchResultSensor.getRsList(),Long.parseLong("2"));
					rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
				}else{
					rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
				}
			}
		}catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	
	@POST
	@Path("findSensorChannelAndChannelDataByWorkSectionId")
	public Response findSensorChannelAndChannelDataByWorkSectionId (@QueryParam("id")Integer id){
		RestResp<WorkSection> rs = null;
		WorkSection workSection = iWorkSectionService.findByPageAndSensorType(id);
//		List<ChannelData>channelDatasList = new ArrayList<ChannelData>();
		try {
			if(workSection != null){
//				观测截面
					if(workSection.getSensorList().size() > 0){
// 							观测点
						for(Sensor sensor : workSection.getSensorList()){
							if(sensor.getSensorChannelList().size() > 0){
// 									观测点通道
								for(SensorChannel sensorChannel : sensor.getSensorChannelList()){
									if(sensor.getSensorChannelList().size() > 0){
										ChannelData channelData = MongoDBService.getTheRecentlyChannelDataBySensorChannelId(sensorChannel.getId());
										sensorChannel.setLatestChannelData(channelData);
										/*channelDatasList = MongoDBService.getChannelDataListBySensorChannelId(0, 500, null, null, sensorChannel.getId());
										if(channelDatasList.size() > 0){
											sensorChannel.setChannelDataList(channelDatasList);
										}*/
									}
								}
							}
						}
				}
			}
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,workSection);
		} catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}
		
		return Response.ok().entity(rs).build();
	}
}
