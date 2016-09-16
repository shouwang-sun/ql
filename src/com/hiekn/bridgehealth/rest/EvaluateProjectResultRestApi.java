package com.hiekn.bridgehealth.rest;

import java.util.Date;
import java.util.List;
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
import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IEvaluateProjectResultService;

@Controller("evaluateProjectResult")
@Path("evaluateProjectResult")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class EvaluateProjectResultRestApi {
	@Autowired
	private IEvaluateProjectResultService iEvaluateProjectResultService;
	Gson gson = new Gson();
	@POST
	@Path("getEvaluateProjectResultList")
	public Response getEvaluateProjectResultList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params") String params){
		RestResp<EvaluateProjectResult> rs = null;
		try{
			Date startTime = null;
			Date endTime = null;
			EvaluateProjectResult evaluateProjectResult = null;
			if(params != null && !params.equals("")){
				evaluateProjectResult = gson.fromJson(params,EvaluateProjectResult.class);
			}
			SearchResult<EvaluateProjectResult> searchResult =  iEvaluateProjectResultService.getEvaluateProjectResultList(index, pageSize, startTime, endTime, evaluateProjectResult);
			RestData<EvaluateProjectResult> restData = new RestData<EvaluateProjectResult>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<EvaluateProjectResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<EvaluateProjectResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	} 
	
	@POST
 	@Path("getEvaluateProjectResultYear")
 	public Response getEvaluateProjectResultYear(){
 		RestResp<EvaluateProjectResult> rs = null;
		try {
			List<Integer> list = iEvaluateProjectResultService.findAllYear();
			RestData<Integer> restData = new RestData<Integer>(list);
			rs = new RestResp<EvaluateProjectResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		} catch (Exception e) {
			rs = new RestResp<EvaluateProjectResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("getEvaluateProjectResultMonth")
 	public Response getEvaluateProjectResultMonth(){
 		RestResp<EvaluateProjectResult> rs = null;
		try {
			List<Integer> list = iEvaluateProjectResultService.findAllMonth();
			RestData<Integer> restData = new RestData<Integer>(list);
			rs = new RestResp<EvaluateProjectResult>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		} catch (Exception e) {
			rs = new RestResp<EvaluateProjectResult>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
}
