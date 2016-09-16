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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.IStructureWarningService;

@Controller("structureWarning")
@Path("structureWarning")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class StructureWarningRestApi {
	@Autowired
	private IStructureWarningService iStructureWarningService;
	@Autowired
	private ISensorChannelService iSensorChannelService;
	@Autowired
	private ILogicGroupOutputService iLogicGroupOutputService;
	
	Gson gson = new Gson();
	
	@POST 
	@Path("getStructureWarningList")
	public Response getStructureWarningList(
				@QueryParam("index") Integer index,
				@QueryParam("pageSize") Integer pageSize,
				@FormParam("params")String params) throws Exception{
		
		RestResp<StructureWarning> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer bridgeId ;
		if((String) map.get("bridgeId") == null ||  map.get("bridgeId").equals("-1")){
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
		try{
			SearchResult<StructureWarning> srResult = iStructureWarningService.getStructureWarningList(index, pageSize,bridgeId,startTime,endTime);
			RestData<StructureWarning> restData = new RestData(srResult.getRsList(),srResult.getRsCount());
			rs = new RestResp<StructureWarning>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch(Exception e){
			rs = new RestResp<StructureWarning>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}  
		
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("insertStructureWarning")
	public Response getStructureWarningList(
			@FormParam("params") String params){
		RestResp<StructureWarning> rs = null;
		try{
			StructureWarning structureWarning = gson.fromJson(params,StructureWarning.class);
			if(structureWarning.getSensorChannelId() != null && structureWarning.getThreshold() != null){
				iSensorChannelService.updateThresholdById(structureWarning.getSensorChannelId(),structureWarning.getThreshold());
			}
			if(structureWarning.getOutputId() !=null && structureWarning.getThreshold() != null){
				iLogicGroupOutputService.updateThresholdById(structureWarning.getOutputId(), structureWarning.getThreshold());
			}
			
			structureWarning.setLastUpdateTime(new Date());
			StructureWarning structureWarning_ = iStructureWarningService.insertStructureWarning(structureWarning);
			rs = new RestResp<StructureWarning>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,structureWarning_);
		}catch( Exception e){
			rs = new RestResp<StructureWarning>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
		
	}
	
	@POST
	@Path("deleteStructureWarningByName")
	public Response deleteStructureWarningByName(
			@FormParam("params") String params){
		RestResp<StructureWarning> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String nameListStr = map.get("nameList");
		String [] nameList = gson.fromJson(nameListStr, String[].class);
		try{
			int count = iStructureWarningService.deleteByNameList(nameList);
			rs = new RestResp<StructureWarning>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<StructureWarning>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	
	@POST
	@Path("deleteStructureWarningByIds")
	public Response deleteStructureWarningByIds(
			@FormParam("params") String  params){
		RestResp<StructureWarning> rs = null;
		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count = iStructureWarningService.deleteByIds(ids);
			rs = new RestResp<StructureWarning>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<StructureWarning>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	@POST
	@Path("updateStructureWarningByName")
	public Response findStructureWarningById(
			@FormParam("params") String params){
		RestResp<StructureWarning> rs = null;
		StructureWarning structureWarning = gson.fromJson(params, StructureWarning.class);
		try{
			iStructureWarningService.updateStructureWarningByName(structureWarning);
			
			if(structureWarning.getSensorChannelId() != null && structureWarning.getThreshold() != null){
				iSensorChannelService.updateThresholdById( structureWarning.getSensorChannelId(),structureWarning.getThreshold());
			}
			if(structureWarning.getOutputId() !=null && structureWarning.getThreshold() != null){
				iLogicGroupOutputService.updateThresholdById(structureWarning.getOutputId(), structureWarning.getThreshold());
			}
			
			rs = new RestResp<StructureWarning>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<StructureWarning>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("updateStructureWarningById")
	public Response updateStructureWarningById(
			@FormParam("params") String params){
		RestResp<StructureWarning> rs = null;
		try{
			StructureWarning structureWarning = gson.fromJson(params, StructureWarning.class);
			if(structureWarning.getSensorChannelId() != null){
				iSensorChannelService.updateThresholdById(structureWarning.getSensorChannelId(),structureWarning.getThreshold());
			}
			structureWarning.setLastUpdateTime(new Date());
			iStructureWarningService.updateStructureWarningById(structureWarning);
			rs = new RestResp<StructureWarning>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<StructureWarning>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
