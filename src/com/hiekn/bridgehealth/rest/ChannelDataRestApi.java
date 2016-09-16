package com.hiekn.bridgehealth.rest;

import java.text.SimpleDateFormat;
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

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IChannelDataService;

@Controller("channelData")
@Path("channelData")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class ChannelDataRestApi {
	static final Logger log = Logger.getLogger(BridgeRestApi.class);
	@Autowired
	private IChannelDataService channelDataService;
	Gson gson = new Gson();
	
	@POST
	@Path("getChannelDataList")
	public Response getChannelDataList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("flag") Integer flag,
			@FormParam("params") String params){
		RestResp<ChannelData> rs = null;
		Map<String,String> map = gson.fromJson(params,Map.class);
		Long createTime = null;
		Long endTime = null;
		Integer sensorChannelId =null;
		
		if(map.get("createTime") != null || !map.get("createTime").equals("-1")){
			createTime = Long.valueOf((String)map.get("createTime"));
		}
		
		if(map.get("endTime") != null || !map.get("endTime").equals("-1")){
			endTime = Long.valueOf((String)map.get("endTime"));
		}
		
		if(map.get("sensorChannelId") != null || !map.get("sensorChannelId").equals("-1")){
			sensorChannelId = Integer.valueOf((String)map.get("sensorChannelId"));
		}
		
		try{
			SearchResult<ChannelData> searchResult = channelDataService.getChannelDataList(index,pageSize,createTime,endTime,sensorChannelId,flag);
			RestData<ChannelData>sData = new RestData<ChannelData>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<ChannelData>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sData);
		}catch( Exception e){
			log.error(e);
			rs = new RestResp<ChannelData>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
