package com.hiekn.bridgehealth.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
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
import com.hiekn.bridgehealth.bean.EvaluateProject;
import com.hiekn.bridgehealth.bean.EvaluateReport;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IEvaluateProjectService;
import com.hiekn.bridgehealth.service.ILogicGroupService;

@Controller("evaluateProject")
@Path("evaluateProject")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class EvaluateProjectRestApi {
	@Autowired
	private IEvaluateProjectService iEvaluateProjectService;
	@Autowired
	private ILogicGroupService iLogicGroupService;
	Gson gson = new Gson();
	@POST
	@Path("getEvaluateProjectList")
	public Response getEvaluateProjectList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@QueryParam("pid") Integer pid,
			@FormParam("params") String params){
		RestResp<EvaluateProject> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		try{
			EvaluateProject evaluateProject  = new EvaluateProject();
			Date startTime = null;
			Date endTime = null;
			
			if(params != null && !params.equals("")){
				Integer bridgeId ;
				if( map.get("bridgeId") == null ||  map.get("bridgeId").equals("-1")){
					bridgeId = null;
				}else{
					bridgeId = Integer.valueOf((String) map.get("bridgeId"));
				};
				evaluateProject.setBridgeId(bridgeId);
				SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(!((String) map.get("startTime")).equals("-1")){
					startTime = sdf.parse((String) map.get("startTime"));
				}
				
				if(!((String) map.get("endTime")).equals("-1")){
					endTime = sdf.parse((String) map.get("endTime"));
				}
			}
			if(pid != null){
				evaluateProject.setPid(Integer.valueOf(pid));
			}
			
			SearchResult<EvaluateProject> searchResult =  iEvaluateProjectService.getEvaluateProjectList(index, pageSize,startTime,endTime,evaluateProject);
			RestData<EvaluateProject> restData = new RestData<EvaluateProject>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<EvaluateProject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<EvaluateProject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("changeEvaluateProject")
 	public Response insertEvaluateProject(@FormParam("params")String params){
 		RestResp<EvaluateProject> rs = null;
		try {
			Map<String,Object> map = gson.fromJson(params, Map.class);
			List<EvaluateProject> insertList = gson.fromJson((String) map.get("insertArr"),new TypeToken<ArrayList<EvaluateProject>>(){}.getType());
			List<EvaluateProject> updateHealthyList = gson.fromJson((String) map.get("updateArr"),new TypeToken<ArrayList<EvaluateProject>>(){}.getType());
			EvaluateProject evaluateProject = gson.fromJson((String) map.get("updateSingle"), EvaluateProject.class);
			//批量添加
			if(insertList.size() > 0){
				for(int i = 0;i < insertList.size(); i++){
					insertList.get(i).setLastUpdateTime(new Date());
					iEvaluateProjectService.insertEvaluateProject(insertList.get(i));
				}
			}
			//批量更新健康性权值
			if(updateHealthyList.size() > 0){
				for(int i = 0;i < updateHealthyList.size(); i++){
					updateHealthyList.get(i).setLastUpdateTime(new Date());
					iEvaluateProjectService.updateByIdByHealthyRate(updateHealthyList.get(i));
				}
			}
			//单个更新
			if(evaluateProject != null){
				evaluateProject.setLastUpdateTime(new Date());
				iEvaluateProjectService.updateEvaluateProject(evaluateProject);
			}
			rs = new RestResp<EvaluateProject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<EvaluateProject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("findEvaluateProjectById")
 	public Response findEvaluateProjectById(@QueryParam("id")Integer id){
 		RestResp<EvaluateProject> rs = null;
		try {
			EvaluateProject evaluateProject= iEvaluateProjectService.findById(id);
			rs = new RestResp<EvaluateProject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,evaluateProject);
		} catch (Exception e) {
			rs = new RestResp<EvaluateProject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateEvaluateProjectById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<EvaluateProject> rs = null;
		try {
			EvaluateProject evaluateProject = gson.fromJson(params, EvaluateProject.class);
			evaluateProject.setLastUpdateTime(new Date());
			iEvaluateProjectService.updateEvaluateProject(evaluateProject);
			rs = new RestResp<EvaluateProject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<EvaluateProject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteEvaluateProjectByIds")
	public Response deleteEvaluateProjectByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<EvaluateProject> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  iEvaluateProjectService.deleteByIds(ids);
			rs = new RestResp<EvaluateProject>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<EvaluateProject>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
