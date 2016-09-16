package com.hiekn.bridgehealth.rest;

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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.LogicGroupSensorChannel;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ILogicGroupConstantService;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
import com.hiekn.bridgehealth.service.ILogicGroupSensorChannelService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.ISensorChannelService;

@Controller("logicGroupSensorChannel")
@Path("logicGroupSensorChannel")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LogicGroupSensorChannelApi {
	static final Logger log = Logger.getLogger(LogicGroupSensorChannelApi.class);
	@Autowired
	private ILogicGroupSensorChannelService iLogicGroupSensorChannelService;
	
	Gson gson = new Gson();
	
	@POST
	@Path("getLogicGroupSensorChannelList")
	public Response getSensorList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		
		RestResp<Sensor> rs = null;
		LogicGroupSensorChannel logicGroupSensorChannel = gson.fromJson(params, LogicGroupSensorChannel.class);
		try{
			SearchResult<LogicGroupSensorChannel> searchResult =  iLogicGroupSensorChannelService.getLogicGroupSensorChannelList(index, pageSize, logicGroupSensorChannel);
			RestData<LogicGroupSensorChannel> restData = new RestData<LogicGroupSensorChannel>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<Sensor>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<Sensor>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("changeLogicGroupSensorChannel")
 	public Response changeLogicGroupSensorChannel(@FormParam("params")String params){
 		RestResp<LogicGroupSensorChannel> rs = null;
		try {
			Map<String, Object> map = gson.fromJson(params, Map.class);
			int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
			List<LogicGroupSensorChannel> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<LogicGroupSensorChannel>>(){}.getType());
			List<LogicGroupSensorChannel> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<LogicGroupSensorChannel>>(){}.getType());
			
			if(idsArr.length > 0){
				iLogicGroupSensorChannelService.deleteByIds(idsArr);
			}
			List<LogicGroupSensorChannel> list = new ArrayList<LogicGroupSensorChannel>();
			
			if(insertList.size() > 0){
				iLogicGroupSensorChannelService.insertArray(insertList);
			} 
			
			if(updateList.size() > 0){
				for(LogicGroupSensorChannel logicGroupSensorChannel : updateList){
					iLogicGroupSensorChannelService.updateById(logicGroupSensorChannel);
				}
			}
			
			rs = new RestResp<LogicGroupSensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			 //   System.out.println(e);
			rs = new RestResp<LogicGroupSensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
	
	@POST
 	@Path("insertLogicGroupSensorChannel")
 	public Response insertLogicGroupSensorChannel(@FormParam("params")String params){
 		RestResp<LogicGroupSensorChannel> rs = null;
		try {
			LogicGroupSensorChannel logicGroupSensorChannel = gson.fromJson(params, LogicGroupSensorChannel.class);
			LogicGroupSensorChannel logicGroupSensorChannel_ = iLogicGroupSensorChannelService.insertLogicGroupSensorChannel(logicGroupSensorChannel);
			rs = new RestResp<LogicGroupSensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,logicGroupSensorChannel);
		} catch (Exception e) {
			 //   System.out.println(e);
			rs = new RestResp<LogicGroupSensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteLogicGroupSensorChannelByIds")
	public Response deleteLogicGroupByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<LogicGroupSensorChannel> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  iLogicGroupSensorChannelService.deleteByIds(ids);
			rs = new RestResp<LogicGroupSensorChannel>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<LogicGroupSensorChannel>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
}
