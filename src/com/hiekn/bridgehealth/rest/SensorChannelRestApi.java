package com.hiekn.bridgehealth.rest;

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
import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.ISensorService;
@Path("sensorChannel")
@Controller("sensorChannel")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class SensorChannelRestApi {
	@Autowired
	private ISensorChannelService iSensorChannelService;
	@Autowired
	private ISensorService iSensorService;
	Gson gson = new Gson();
	
	@POST
	@Path("getSensorChannelList")
	public Response getSensorChannelList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		
		RestResp<SensorChannel> rs = null;
		SensorChannel sensorChannel = gson.fromJson(params, SensorChannel.class); 
		try{
			SearchResult<SensorChannel> searchResult =  iSensorChannelService.getSensorChannelList(index, pageSize, sensorChannel);
			RestData<SensorChannel> restData = new RestData<SensorChannel>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<SensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<SensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("changeSensorChannel")
	public Response changeSensorChannel(
			@FormParam("params") String params){
		
		RestResp<SensorChannel> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params, Map.class);
			int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
			List<SensorChannel> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<SensorChannel>>(){}.getType());
			List<SensorChannel> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<SensorChannel>>(){}.getType());
			//根据ID批量删除
			if(idsArr.length > 0){
				iSensorChannelService.deleteByIds(idsArr);
			}
			//批量添加
			if(insertList.size() > 0){
				for(int i = 0;i < insertList.size(); i++){
					insertList.get(i).setLastUpdateTime(new Date());
					iSensorChannelService.insertSensorChannel(insertList.get(i));
				}
			}
			//根据Id批量更新
			if(updateList.size() > 0){
				for(int i = 0;i < updateList.size(); i++){
					updateList.get(i).setLastUpdateTime(new Date());
					iSensorChannelService.updateSensorChannel(updateList.get(i));
				}
			}
			rs = new RestResp<SensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		
		}catch( Exception e){
			rs = new RestResp<SensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	
	@POST
 	@Path("insertSensorChannel")
 	public Response insertSensorChannel(@FormParam("params")String params){
 		RestResp<SensorChannel> rs = null;
 		try{
			List<SensorChannel> list = gson.fromJson(params,new TypeToken<ArrayList<SensorChannel>>(){}.getType());
			List<SensorChannel> resultList = new ArrayList<SensorChannel>();
			for(int i = 0;i < list.size(); i++){
				list.get(i).setLastUpdateTime(new Date());
				SensorChannel sensorChannel = iSensorChannelService.insertSensorChannel(list.get(i));
				resultList.add(sensorChannel);
			}
			rs = new RestResp<SensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,resultList);
			
		}catch( Exception e){
			rs = new RestResp<SensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 
	@POST
 	@Path("findSensorChannelById")
 	public Response findById(@QueryParam("id")Integer id){
 		RestResp<SensorChannel> rs = null;
		try {
			SensorChannel sensorChannel= iSensorChannelService.findById(id);
			rs = new RestResp<SensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sensorChannel);
		} catch (Exception e) {
			rs = new RestResp<SensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("updateSensorChannelByIds")
	public Response updateSensorChannelByIds(
			@FormParam("params") String params){
		RestResp<SensorChannel> rs = null;
		try {
			List<SensorChannel> list = gson.fromJson(params,new TypeToken<ArrayList<SensorChannel>>(){}.getType());
			for(int i = 0;i < list.size(); i++){
				iSensorChannelService.updateSensorChannel(list.get(i));
			}
			rs = new RestResp<SensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<SensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("deleteSensorChannelByIds")
	public Response deleteByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<SensorChannel> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  iSensorChannelService.deleteByIds(ids);
			rs = new RestResp<SensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<SensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
