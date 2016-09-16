package com.hiekn.bridgehealth.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.StructureWarningResult;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IBridgeService;
import com.hiekn.bridgehealth.service.IEvaluateProjectResultService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.IStructureWarningResultService;
import com.hiekn.bridgehealth.service.IStructureWarningService;

@Controller("structureWarningResult")
@Path("structureWarningResult")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class StructureWarningResultRestApi {
	@Autowired
	private IStructureWarningResultService structureWarningResultService;
	@Autowired
	private IStructureWarningService iStructureWarningService;
	@Autowired
	private ILogicGroupService logicGroupService;
	@Autowired
	private ISensorChannelService sensorChannelService;
	@Autowired
	private IBridgeService iBridgeService;
	@Autowired
	private IEvaluateProjectResultService iEvaluateProjectResultService;
	
	Gson gson = new Gson();
	
	@POST
	@Path("getStructureWarningResultList")
	public Response getStructureWarningResultList(
				@QueryParam("index") Integer index,
				@QueryParam("pageSize") Integer pageSize,
				@FormParam("params") String params) throws Exception{
		
		RestResp<StructureWarningResult> rs = null;
		List<StructureWarningResult> list = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		try{
			Integer bridgeId;
			Integer dealResult;
			
			if( map.get("bridgeId") == null ||  map.get("bridgeId").equals("-1")){
				bridgeId = null;
			}else{
				bridgeId = Integer.valueOf((String) map.get("bridgeId"));
			};
			
			if(map.get("dealResult") == null ||  map.get("dealResult").equals("-1")){
				dealResult = null;
			}else{
				dealResult = Integer.valueOf((String) map.get("dealResult"));
			}
			
			Date createTime = null;
			Date endTime = null;
			
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!((String) map.get("createTime")).equals("-1")){
				createTime = sdf.parse((String) map.get("createTime"));
			}
			
			if(!((String) map.get("endTime")).equals("-1")){
				endTime = sdf.parse((String) map.get("endTime"));
			}
			
			StructureWarningResult structureWarningResult = new StructureWarningResult();
			structureWarningResult.setBridgeId(bridgeId);
			structureWarningResult.setDealResult(dealResult);
			SearchResult<StructureWarningResult> srResult = structureWarningResultService.getStructureWarningResultList(index, pageSize, createTime, endTime, structureWarningResult); 
			RestData<StructureWarningResult> restData = new RestData(srResult.getRsList(),srResult.getRsCount());
			rs = new RestResp<StructureWarningResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch(Exception e){
			rs = new RestResp<StructureWarningResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}  
		
		return Response.ok().entity(rs).build();
	}
	
	@POST
 	@Path("getStructureWarningResultById")
 	public Response insertStructureWarningResult(@QueryParam("id") Integer id){
 		RestResp<StructureWarningResult> rs = null;
		try {
			StructureWarningResult structureWarningResult = structureWarningResultService.findById(id);
			rs = new RestResp<StructureWarningResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,structureWarningResult);
		} catch (Exception e) {
			rs = new RestResp<StructureWarningResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
	
	@POST
 	@Path("insertStructureWarningResult")
 	public Response insertStructureWarningResult(@FormParam("params")String params){
 		RestResp<StructureWarningResult> rs = null;
		try {
			StructureWarningResult structureWarningResult = gson.fromJson(params, StructureWarningResult.class);
			structureWarningResult.setLastUpdateTime(new Date());
			StructureWarningResult structureWarningResult_ = structureWarningResultService.insertStructureWarningResult(structureWarningResult);
			rs = new RestResp<StructureWarningResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,structureWarningResult_);
		} catch (Exception e) {
			rs = new RestResp<StructureWarningResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("updateStructureWarningResultById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<StructureWarningResult> rs = null;
		try {
			StructureWarningResult structureWarningResult = gson.fromJson(params, StructureWarningResult.class);
			structureWarningResult.setLastUpdateTime(new Date());
			structureWarningResultService.updateStructureWarningResultById(structureWarningResult);
			rs = new RestResp<StructureWarningResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<StructureWarningResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("getStructureWarningResultListGroupByBridgeId")
 	public Response getStructureWarningResultListGroupByBridgeId(
 			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize){
 		RestResp<StructureWarningResult> rs = null;
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			StructureWarningResult structureWarningResult = new StructureWarningResult();
			SearchResult<Bridge> searchResult = iBridgeService.getBridgeList(0, 99999999);
			List<Bridge>bridgeList = searchResult.getRsList();
			Long endTime = new Date().getTime();
			Long curTime = endTime - 1000 * 60 * 60 * 24 * 7;
			Integer bInteger;
			for(Bridge bridge : bridgeList){
				bInteger = bridge.getId();
				structureWarningResult.setBridgeId(bInteger);
				structureWarningResult.setDealResult(0);
				Map<String, Object> map = new HashMap<String, Object>();
				SearchResult<StructureWarningResult> eResult_ =  structureWarningResultService.getStructureWarningResultList(index, pageSize, new Date(curTime), new Date(endTime), structureWarningResult);
				List<StructureWarningResult> structureWarningResultList = eResult_.getRsList();
				map.put("bridge", iBridgeService.findById(bInteger));
				map.put("list", structureWarningResultList);
				Date date = new Date();
				Integer year = date.getYear() + 1900;;
				Integer month = date.getMonth() + 1;
				EvaluateProjectResult evaluateProjectResult = new EvaluateProjectResult();
				evaluateProjectResult.setBridgeId(bInteger);
				evaluateProjectResult.setProjectYear(year);
				evaluateProjectResult.setProjectMonth(month);
				evaluateProjectResult.setEvaluateProjectPid(0);
				SearchResult<EvaluateProjectResult> eResult =  iEvaluateProjectResultService.getEvaluateProjectResultList(0, 9999999, null, null, evaluateProjectResult);
				map.put("evaluateProjectResult",eResult.getRsList());
				list.add(map);
			}
			SearchResult<Map<String, Object>>searchResult_ = new SearchResult<Map<String, Object>>();
			searchResult_.setRsCount(Long.valueOf(list.size()));
			searchResult_.setRsList(list);
			RestData<StructureWarningResult> restData = new RestData(searchResult_.getRsList(),searchResult_.getRsCount());
			rs = new RestResp<StructureWarningResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		} catch (Exception e) {
			rs = new RestResp<StructureWarningResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
}
