package com.hiekn.bridgehealth.rest;

import java.lang.ProcessBuilder.Redirect.Type;
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

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Attr;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IAttributeService;

@Path("attribute")
@Controller("attribute")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class AttributeRestApi {
	@Autowired
	private IAttributeService iAttributeService;
	Gson gson = new Gson();
	
	@POST
	@Path("getAttributeList")
	public Response getAttributeList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize,
			@FormParam("params")String params){
		RestResp<Attribute> rs = null;
		Map<String, Object> map = gson.fromJson(params,Map.class);
		Integer bridgeId ;
		if((String) map.get("bridgeId") == null ||  map.get("bridgeId").equals("-1")){
			bridgeId = null;
		}else{
			bridgeId = Integer.valueOf((String) map.get("bridgeId"));
		};
		try{
			SearchResult<Attribute> searchResult =  iAttributeService.getAttributeList(index, pageSize,bridgeId);
			RestData<Attribute> restData = new RestData<Attribute>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		}catch( Exception e){
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
		
	}
	
	@POST
	@Path("changeAttribute")
	public Response changeAttribute(
			@FormParam("params") String params,
			@QueryParam("type")Integer type){
		RestResp<Attribute> rs = null;
		try{
			Map<String, Object> map = gson.fromJson(params, Map.class);
			if(type == 1){
				//单个添加
				Attribute attribute = gson.fromJson((String) map.get("singleParam"),Attribute.class);
				attribute.setLastUpdateTime(new Date());
				Attribute attribute_ = iAttributeService.insertAttribute(attribute);
				rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,attribute_);
			}else if(type == 2){
				//根据名称更新
				Attribute attribute = gson.fromJson((String) map.get("singleParam"),Attribute.class);
				attribute.setLastUpdateTime(new Date());
				iAttributeService.updateAttributeByName(attribute);
				rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			}else if(type == 3){
				int [] idsArr = gson.fromJson((String)map.get("delIdsArr"), int [].class);
				List<Attribute> insertList = gson.fromJson((String)map.get("insertArr"),new TypeToken<ArrayList<Attribute>>(){}.getType());
				List<Attribute> updateList = gson.fromJson((String)map.get("updateArr"),new TypeToken<ArrayList<Attribute>>(){}.getType());
				//根据ID批量删除
				if(idsArr.length > 0){
					iAttributeService.deleteByIds(idsArr);
				}
				//批量添加
				if(insertList.size() > 0){
					for(int i = 0;i < insertList.size(); i++){
						insertList.get(i).setLastUpdateTime(new Date());
						iAttributeService.insertAttribute(insertList.get(i));
					}
				}
				//根据Id批量更新
				if(updateList.size() > 0){
					for(int i = 0;i < updateList.size(); i++){
					     updateList.get(i).setLastUpdateTime(new Date());
						 iAttributeService.updateAttributeById(updateList.get(i));
					}
				}
				rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
			} 
		}catch( Exception e){
			 //   System.out.println(e);
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	@POST
	@Path("deleteAttributeByName")
	public Response deleteAttributeByName(
			@FormParam("params") String params){
		RestResp<Attribute> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String nameListStr = map.get("nameList");
		String [] nameList = gson.fromJson(nameListStr, String[].class);
		try{
			int count = iAttributeService.deleteByNameList(nameList);
			rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	/*@POST
	@Path("deleteAttributeByIds")
	public Response deleteAttributeByIds(
			@FormParam("params") String params){
		RestResp<Attribute> rs = null;
		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count = iAttributeService.deleteByIds(ids);
			rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch( Exception e){
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	*/
/*	@POST
	@Path("updateAttributeListByBridgeId")
	public Response updateAttributeListByBridgeId(
			@FormParam("params") String params){
		RestResp<Attribute> rs = null;
		try {
			List<Attribute> list = gson.fromJson(params,new TypeToken<ArrayList<Attribute>>(){}.getType());
			for(int i = 0;i < list.size(); i++){
				 iAttributeService.updateAttribute(list.get(i));
			}
			rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}*/
/*	@POST
	@Path("updateAttributeById")
	public Response updateAttributeById(
			@FormParam("params") String params){
		RestResp<Attribute> rs = null;
		try {
			Attribute attribute = gson.fromJson(params, Attribute.class);
			attribute.setLastUpdateTime(new Date());
			iAttributeService.updateAttributeById(attribute);
			rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}*/
	
 	/*@POST
	@Path("updateAttributeByName")
	public Response updateAttributeByName(
			@FormParam("params") String params){
		RestResp<Attribute> rs = null;
		try {
			Attribute attribute = gson.fromJson(params, Attribute.class);
			attribute.setLastUpdateTime(new Date());
			iAttributeService.updateAttributeByName(attribute);
			rs = new RestResp<Attribute>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<Attribute>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	} */
}
