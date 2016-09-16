package com.hiekn.bridgehealth.rest;

import java.util.Date;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IChannelDataHistoryFileService;
import com.hiekn.bridgehealth.util.CommonResource;

@Controller("channelDataHistory")
@Path("channelDataHistory")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class ChannelDataHistoryFileRestApi {
	static final Logger log = Logger.getLogger(BridgeRestApi.class);
	@Autowired
	private IChannelDataHistoryFileService channelDataHistoryFileService;
	Gson gson = new Gson();
	
	@POST
	@Path("getChannelDataHistoryFileList")
	public Response findByPage(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params")String params) throws Exception{
		
		RestResp<ChannelDataHistoryFile> rs = null;
		
		Map<String,String> map = gson.fromJson(params,Map.class);
		String nodeJson = (String) map.get("nodeJson");
		ChannelDataHistoryFile channelDataHistoryFile = new ChannelDataHistoryFile(null,null,null,null,null,null);
		if(!nodeJson.equals("-1")){
			channelDataHistoryFile = gson.fromJson(nodeJson, ChannelDataHistoryFile.class);
		} 
		Date createTime = null;
		Date endTime = null;
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!((String) map.get("createTime")).equals("-1")){
			createTime = sdf.parse(map.get("createTime"));
		}
		
		if(!((String) map.get("endTime")).equals("-1")){
			endTime = sdf.parse(map.get("endTime"));
		}
		
		try{
			SearchResult<ChannelDataHistoryFile> srResult = channelDataHistoryFileService.getChannelDataHistoryFileList(index, pageSize, createTime, endTime,channelDataHistoryFile);
			RestData<ChannelDataHistoryFile> restData = new RestData(srResult.getRsList(),srResult.getRsCount());
			rs = new RestResp<ChannelDataHistoryFile>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch(Exception e){
			rs = new RestResp<ChannelDataHistoryFile>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		} 
		
		return Response.ok().entity(rs).build();
	}
}
