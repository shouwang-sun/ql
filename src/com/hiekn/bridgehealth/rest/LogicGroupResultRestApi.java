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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.ChannelDataHistoryFile;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;

@Controller("logicGroupResult")
@Path("logicGroupResult")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class LogicGroupResultRestApi {
	static final Logger log = Logger.getLogger(LogicGroupOutputRestApi.class);
	@Autowired 
	private ILogicGroupResultService logicGroupResultService;
	Gson gson = new Gson();
	
	@POST
	@Path("getLogicResultList")
	public Response getOutputList(
				@QueryParam("index") Integer index,
				@QueryParam("pageSize") Integer pageSize,
				@QueryParam("flag") Integer flag,
				@FormParam("params") String params) throws Exception{
		
		RestResp<LogicGroupResult> rs = null;
		List<LogicGroupResult> list = null;
		
		Map<String,String> map = gson.fromJson(params,Map.class);
		String nodeJson = (String) map.get("nodeJson");
		LogicGroupResult logicGroupResult = new LogicGroupResult(null,null, null);
		if(!nodeJson.equals("-1")){
			logicGroupResult = gson.fromJson(nodeJson, LogicGroupResult.class);
		} 
		
		Long createTime = null;
		Long endTime = null;
		
//		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(map.get("createTime") != null || !map.get("createTime").equals("-1")){
			createTime = Long.valueOf((String)map.get("createTime"));
		}
		
		if(map.get("endTime") != null || !map.get("endTime").equals("-1")){
			endTime = Long.valueOf((String)map.get("endTime"));
		}
		
		try{
			SearchResult<LogicGroupResult> srResult = logicGroupResultService.getLogicResultList(index, pageSize, createTime, endTime, logicGroupResult,flag);
			RestData<LogicGroupResult> restData = new RestData(srResult.getRsList(),srResult.getRsCount());
			rs = new RestResp<LogicGroupResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch(Exception e){
			
			rs = new RestResp<LogicGroupResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}  
		
		return Response.ok().entity(rs).build();
	}
}
