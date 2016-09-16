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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ILogicGroupConstantService;

@Controller("logicGroupConstant")
@Path("logicGroupConstant")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class LogicGroupConstantRestApi {
	static final Logger log = Logger.getLogger(LogicGroupConstantRestApi.class);
	@Autowired 
	private ILogicGroupConstantService iLogicGroupConstantService;
	
	Gson gson = new Gson();
	@POST
	@Path("getLogicGroupConstantList")
	public Response getOutputList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		RestResp<LogicGroupConstant> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer logicGroupId ;
		if((String) map.get("logicGroupId") == null ||  map.get("logicGroupId").equals("-1")){
			logicGroupId = null;
		}else{
			logicGroupId = Integer.valueOf((String) map.get("logicGroupId"));
		};
		try {
			SearchResult<LogicGroupConstant> sResult =  iLogicGroupConstantService.getLogicGroupConstantList(index, pageSize,logicGroupId);
			RestData<LogicGroupConstant> sData = new RestData<LogicGroupConstant>(sResult.getRsList());
			rs = new RestResp<LogicGroupConstant>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sData);
		} catch (Exception e) {
			log.error(e);
			rs = new RestResp<LogicGroupConstant>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}

	@POST
	@Path("changelogicGroupConstant")
	public Response changelogicGroupConstant(
			@FormParam("params") String params){
		
		RestResp<LogicGroupConstant> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params, Map.class);
			int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
			List<LogicGroupConstant> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<LogicGroupConstant>>(){}.getType());
			List<LogicGroupConstant> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<LogicGroupConstant>>(){}.getType());
			//根据ID批量删除
			if(idsArr.length > 0){
				iLogicGroupConstantService.deleteByIds(idsArr);
			}
			//批量添加
			if(insertList.size() > 0){
				for(int i = 0;i < insertList.size(); i++){
					insertList.get(i).setLastUpdateTime(new Date());
					iLogicGroupConstantService.insertLogicGroupConstant(insertList.get(i));
				}
			}
			//根据Id批量更新
			if(updateList.size() > 0){
				for(int i = 0;i < updateList.size(); i++){
					updateList.get(i).setLastUpdateTime(new Date());
					iLogicGroupConstantService.updateLogicGroupConstant(updateList.get(i));
				}
			}
			rs = new RestResp<LogicGroupConstant>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		
		}catch( Exception e){
			rs = new RestResp<LogicGroupConstant>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
