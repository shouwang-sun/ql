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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.SensorTypeAttribute;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.ISensorTypeAttributeService;

@Path("sensorTypeAttribute")
@Controller("sensorTypeAttribute")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class SensorTypeAttributeRestApi {
	@Autowired
	private ISensorTypeAttributeService iSensorTypeAttributeService;
	Gson gson = new Gson();
	
	@POST
	@Path("getSensorTypeAttributeList")
	public Response getSensorTypeAttributeList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params")String params){
		RestResp<SensorTypeAttribute> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer sensorTypeId ;
		if((String) map.get("sensorTypeId") == null ||  map.get("sensorTypeId").equals("-1")){
			sensorTypeId = null;
		}else{
			sensorTypeId = Integer.valueOf((String) map.get("sensorTypeId"));
		};
		try{
			SearchResult<SensorTypeAttribute> searchResult =  iSensorTypeAttributeService.getSensorTypeAttributeList(index, pageSize,sensorTypeId);
			RestData<SensorTypeAttribute> restData = new RestData<SensorTypeAttribute>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
		
	}
	
	@POST
	@Path("changeSensorTypeAttribute")
	public Response changeSensorTypeAttribute(
			@FormParam("params") String params,
			@QueryParam("type")Integer type){
		RestResp<SensorTypeAttribute> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params, Map.class);
			if(type == 1){
				//单个添加
				SensorTypeAttribute attribute = gson.fromJson((String) map.get("singleParam"),SensorTypeAttribute.class);
				attribute.setLastUpdateTime(new Date());
				SensorTypeAttribute attribute_ = iSensorTypeAttributeService.insertSensorTypeAttribute(attribute);
				rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,attribute_);
			}else if(type == 2){
				//根据名称更新
				SensorTypeAttribute attribute = gson.fromJson((String) map.get("singleParam"),SensorTypeAttribute.class);
				attribute.setLastUpdateTime(new Date());
				iSensorTypeAttributeService.updateSensorTypeAttributeByName(attribute);
				rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			}else if(type == 3){
				int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
				List<SensorTypeAttribute> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<SensorTypeAttribute>>(){}.getType());
				List<SensorTypeAttribute> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<SensorTypeAttribute>>(){}.getType());
				//根据ID批量删除
				if(idsArr.length > 0){
					iSensorTypeAttributeService.deleteByIds(idsArr);
				}
				//批量添加
				if(insertList.size() > 0){
					for(int i = 0;i < insertList.size(); i++){
						insertList.get(i).setLastUpdateTime(new Date());
						iSensorTypeAttributeService.insertSensorTypeAttribute(insertList.get(i));
					}
				}
				//根据Id批量更新
				if(updateList.size() > 0){
					for(int i = 0;i < updateList.size(); i++){
					     updateList.get(i).setLastUpdateTime(new Date());
						 iSensorTypeAttributeService.updateSensorTypeAttributeById(updateList.get(i));
					}
				}
				rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			} 
		}catch( Exception e){
			 //   System.out.println(e);
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("deleteSensorTypeAttributeByName")
	public Response deleteSensorTypeAttributeByName(
			@FormParam("params") String params){
		RestResp<SensorTypeAttribute> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String nameListStr = map.get("nameList");
		String [] nameList = gson.fromJson(nameListStr, String[].class);
		try{
			int count = iSensorTypeAttributeService.deleteByNameList(nameList);
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	/*@POST
	@Path("deleteSensorTypeAttributeByIds")
	public Response deleteSensorTypeAttributeByIds(
			@FormParam("params") String params){
		RestResp<SensorTypeAttribute> rs = null;
		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count = iSensorTypeAttributeService.deleteByIds(ids);
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	*/
/*	@POST
	@Path("updateSensorTypeAttributeListByBridgeId")
	public Response updateSensorTypeAttributeListByBridgeId(
			@FormParam("params") String params){
		RestResp<SensorTypeAttribute> rs = null;
		try {
			List<SensorTypeAttribute> list = gson.fromJson(params,new TypeToken<ArrayList<SensorTypeAttribute>>(){}.getType());
			for(int i = 0;i < list.size(); i++){
				 iSensorTypeAttributeService.updateSensorTypeAttribute(list.get(i));
			}
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}*/
/*	@POST
	@Path("updateSensorTypeAttributeById")
	public Response updateSensorTypeAttributeById(
			@FormParam("params") String params){
		RestResp<SensorTypeAttribute> rs = null;
		try {
			SensorTypeAttribute attribute = gson.fromJson(params, SensorTypeAttribute.class);
			attribute.setLastUpdateTime(new Date());
			iSensorTypeAttributeService.updateSensorTypeAttributeById(attribute);
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}*/
	
 	/*@POST
	@Path("updateSensorTypeAttributeByName")
	public Response updateSensorTypeAttributeByName(
			@FormParam("params") String params){
		RestResp<SensorTypeAttribute> rs = null;
		try {
			SensorTypeAttribute attribute = gson.fromJson(params, SensorTypeAttribute.class);
			attribute.setLastUpdateTime(new Date());
			iSensorTypeAttributeService.updateSensorTypeAttributeByName(attribute);
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<SensorTypeAttribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	} */
}
