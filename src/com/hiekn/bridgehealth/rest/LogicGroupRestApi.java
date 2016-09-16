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
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ILogicGroupConstantService;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.ISensorChannelService;

@Controller("logicGroup")
@Path("logicGroup")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LogicGroupRestApi {
	static final Logger log = Logger.getLogger(LogicGroupRestApi.class);
	@Autowired
	private ILogicGroupService iLogicGroupService;
	@Autowired
	private ILogicGroupOutputService iLogicGroupOutputService;
	@Autowired
	private ILogicGroupConstantService iLogicGroupConstantService;
	@Autowired
	private ISensorChannelService iSensorChannelService;
	
	Gson gson = new Gson();
	
	@POST
	@Path("getLogicGroupList")
	public Response getLogicGroupList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params) {
		RestResp<LogicGroup> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		try {
			Integer bridgeId ;
		 
			if( map.get("bridgeId") == null ||  map.get("bridgeId").equals("-1")){
				bridgeId = null;
			}else{
				bridgeId = Integer.valueOf((String) map.get("bridgeId"));
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
			SearchResult<LogicGroup> sResult = iLogicGroupService.getLogicGroupList(index,pageSize,bridgeId,startTime,endTime);
			RestData<LogicGroup> sData = new RestData<LogicGroup>(sResult.getRsList(),sResult.getRsCount());
			rs = new RestResp<LogicGroup>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG, sData);
		} catch (Exception e) {
			log.error(e);
			rs = new RestResp<LogicGroup>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	
	@POST
 	@Path("insertLogicGroup")
 	public Response insertLogicGroup(@FormParam("params")String params){
 		RestResp<LogicGroup> rs = null;
		try {
			LogicGroup logicGroup = gson.fromJson(params, LogicGroup.class);
			logicGroup.setLastUpdateTime(new Date());
			logicGroup.setNextRunTime(new Date().getTime());
			LogicGroup logicGroup_ = iLogicGroupService.insertLogicGroup(logicGroup);
			rs = new RestResp<LogicGroup>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,logicGroup_);
		} catch (Exception e) {
			 //   System.out.println(e);
			rs = new RestResp<LogicGroup>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findLogicGroupById")
 	public Response findLogicGroupById(@QueryParam("id")Integer id){
 		RestResp<LogicGroup> rs = null;
		try {
			LogicGroup logicGroup= iLogicGroupService.findById(id);
			rs = new RestResp<LogicGroup>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,logicGroup);
		} catch (Exception e) {
			rs = new RestResp<LogicGroup>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateLogicGroupById")
 	public Response updateLogicGroupByIds(@FormParam("params")String  params){
 		RestResp<LogicGroup> rs = null;
		try {
			LogicGroup logicGroup = gson.fromJson(params, LogicGroup.class);
			logicGroup.setLastUpdateTime(new Date());
			iLogicGroupService.updateLogicGroup(logicGroup);
			rs = new RestResp<LogicGroup>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<LogicGroup>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteLogicGroupByIds")
	public Response deleteLogicGroupByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<LogicGroup> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  iLogicGroupService.deleteByIds(ids);
			rs = new RestResp<LogicGroup>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<LogicGroup>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
}
