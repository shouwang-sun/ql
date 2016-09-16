package com.hiekn.bridgehealth.rest;

import java.security.Timestamp;
import java.text.DateFormat;
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
import com.hiekn.bridgehealth.bean.ManualCheckField;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IManualCheckFieldService;

@Controller("manualCheckField")
@Path("manualCheckField")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class ManualCheckFieldRestApi {
	@Autowired
	private IManualCheckFieldService iManualCheckFieldService;
	Gson gson = new Gson();
	@POST
	
	@Path("getManualCheckFieldList")
	public Response getManualCheckFieldList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params")String params){
		RestResp<ManualCheckField> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer manualCheckDataId ;
		if((String) map.get("manualCheckDataId") == null ||  map.get("manualCheckDataId").equals("-1")){
			manualCheckDataId = null;
		}else{
			manualCheckDataId = Integer.valueOf((String) map.get("manualCheckDataId"));
		};
		
		try{
			SearchResult<ManualCheckField> searchResult =  iManualCheckFieldService.getManualCheckFieldList(index, pageSize, manualCheckDataId);
			RestData<ManualCheckField> restData = new RestData<ManualCheckField>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<ManualCheckField>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<ManualCheckField>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
		
	}
	
	@POST
	@Path("changeManualCheckField")
	public Response changeManualCheckField(
			@FormParam("params") String params,
			@QueryParam("type")Integer type){
		RestResp<ManualCheckField> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params, Map.class);
			if(type == 1){
				//单个添加
				ManualCheckField manualCheckField = gson.fromJson((String) map.get("singleParam"),ManualCheckField.class);
				manualCheckField.setLastUpdateTime(new Date());
				ManualCheckField manualCheckField_ = iManualCheckFieldService.insertManualCheckField(manualCheckField);
				rs = new RestResp<ManualCheckField>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,manualCheckField_);
			}else if(type == 2){
				//根据名称更新
				ManualCheckField manualCheckField = gson.fromJson((String) map.get("singleParam"),ManualCheckField.class);
				manualCheckField.setLastUpdateTime(new Date());
				iManualCheckFieldService.updateManualCheckFieldByName(manualCheckField);
				rs = new RestResp<ManualCheckField>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			}else if(type == 3){
				int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
				List<ManualCheckField> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<ManualCheckField>>(){}.getType());
				List<ManualCheckField> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<ManualCheckField>>(){}.getType());
				//根据ID批量删除
				if(idsArr.length > 0){
					iManualCheckFieldService.deleteByIds(idsArr);
				}
				//批量添加
				if(insertList.size() > 0){
					for(int i = 0;i < insertList.size(); i++){
						insertList.get(i).setLastUpdateTime(new Date());
						iManualCheckFieldService.insertManualCheckField(insertList.get(i));
					}
				}
				//根据Id批量更新
				if(updateList.size() > 0){
					for(int i = 0;i < updateList.size(); i++){
					     updateList.get(i).setLastUpdateTime(new Date());
						 iManualCheckFieldService.updateManualCheckFieldById(updateList.get(i));
					}
				}
				rs = new RestResp<ManualCheckField>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			} 
		}catch( Exception e){
			 //   System.out.println(e);
			rs = new RestResp<ManualCheckField>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("deleteManualCheckFieldByName")
	public Response deleteManualCheckFieldByName(
			@FormParam("params") String params){
		RestResp<ManualCheckField> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String nameListStr = map.get("nameList");
		String [] nameList = gson.fromJson(nameListStr, String[].class);
		try{
			int count = iManualCheckFieldService.deleteByNameList(nameList);
			rs = new RestResp<ManualCheckField>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<ManualCheckField>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
