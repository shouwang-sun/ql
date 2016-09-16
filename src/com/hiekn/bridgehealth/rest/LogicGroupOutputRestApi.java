package com.hiekn.bridgehealth.rest;

import java.util.ArrayList;
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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;

@Controller("logicGroupOutput")
@Path("logicGroupOutput")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class LogicGroupOutputRestApi {
	static final Logger log = Logger.getLogger(LogicGroupOutputRestApi.class);
	@Autowired 
	private ILogicGroupOutputService iLogicGroupOutputService;
	
	Gson gson = new Gson();
	@POST
	@Path("getLogicGroupOutputList")
	public Response getOutputList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		RestResp<LogicGroupOutput> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer logicGroupId ;
		if((String) map.get("logicGroupId") == null ||  map.get("logicGroupId").equals("-1")){
			logicGroupId = null;
		}else{
			logicGroupId = Integer.valueOf((String) map.get("logicGroupId"));
		};
		try {
			SearchResult<LogicGroupOutput> sResult =  iLogicGroupOutputService.getLogicGroupOutputList(index, pageSize, logicGroupId);
			RestData<LogicGroupOutput> sData = new RestData<LogicGroupOutput>(sResult.getRsList());
			rs = new RestResp<LogicGroupOutput>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,sData);
		} catch (Exception e) {
			log.error(e);
			rs = new RestResp<LogicGroupOutput>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}

	@POST
	@Path("changelogicGroupOutput")
	public Response changelogicGroupOutput(
			@FormParam("params") String params){
		
		RestResp<LogicGroupOutput> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params, Map.class);
			int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
			List<LogicGroupOutput> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<LogicGroupOutput>>(){}.getType());
			List<LogicGroupOutput> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<LogicGroupOutput>>(){}.getType());
			//根据ID批量删除
			if(idsArr.length > 0){
				iLogicGroupOutputService.deleteByIds(idsArr);
			}
			//批量添加
			if(insertList.size() > 0){
				for(int i = 0;i < insertList.size(); i++){
					insertList.get(i).setLastUpdateTime(new Date());
					iLogicGroupOutputService.insertlogicGroupOut(insertList.get(i));
				}
			}
			//根据Id批量更新
			if(updateList.size() > 0){
				for(int i = 0;i < updateList.size(); i++){
					updateList.get(i).setLastUpdateTime(new Date());
					iLogicGroupOutputService.updateLogicGroupOutput(updateList.get(i));
				}
			}
			rs = new RestResp<LogicGroupOutput>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		
		}catch( Exception e){
			rs = new RestResp<LogicGroupOutput>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
