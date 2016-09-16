package com.hiekn.bridgehealth.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.IBridgeService;
import com.hiekn.bridgehealth.service.IChannelDataService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.ISensorService;
import com.hiekn.bridgehealth.service.ISensorTypeService;
import com.hiekn.bridgehealth.service.IStructureWarningService;
import com.hiekn.bridgehealth.service.IWorkSectionService;

@Controller("bridge")
@Path("bridge")
@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
public class BridgeRestApi {
	static final Logger log = Logger.getLogger(BridgeRestApi.class);
	@Autowired 
	private IBridgeService bridgeService;
	@Autowired 
	private ISensorTypeService sensorTypeService;
	@Autowired
	private ISensorService sensorService;
	@Autowired
	private ISensorChannelService sensorChannelService;
	@Autowired
	private IWorkSectionService workSectionService;
	@Autowired
	private ILogicGroupService logicGroupService;
	@Autowired
	private ILogicGroupOutputService outputService;
	@Autowired
	private IChannelDataService iChannelDataService;
	@Autowired
	private IStructureWarningService iStructureWarningService;
	
	Gson gson = new Gson();
	
	@POST
	@Path("buildTree") 
	public Response buildTree(@FormParam("params") String params){
//		senType :
//			1 --观测点类别 2,--观测点观测截面
//	 	nodeType:
//			bridge--桥梁
//	 		senType-- 观测点类型
//	 		workSec-- 观测观测截面
//	 		sen--观测点
//	 		senChan--观测点通道
//	 		chanData--观测点数据
		RestResp<Object> rs = null;
//		"senType":senType,
//		"nodeType":nodeType
		Map<String,Object> map = gson.fromJson(params,Map.class);
 		String showType = (String)map.get("showType");
		String nodeStr = (String)map.get("nodeStr");
		String extra = (String)map.get("extra");
		String nodeType = nodeStr.split("_")[0];
		String pid = nodeStr.split("_")[1];
		String extraType = "";
		String extraValue = "";
		if(extra != null){
			extraType = extra.split("_")[0];
			extraValue = extra.split("_")[1];
		}
		Integer index = 0;
		Integer pageSize = 9999;
		List<Object> list = new ArrayList<Object>();
		String singleData = "";
		try {
			if(nodeType.equals("init")){
				boolean packageFlag = true;
				if(showType.equals("structureWarningResult") || showType.equals("manualCheck")){
					packageFlag = false;
				}
				if(extra != null && extraType.equals("1")){
					Bridge bridge = bridgeService.findById(Integer.valueOf(extraValue));
					singleData = "{id:'bridge_"+bridge.getId()+"',pid:"+pid+",name:'"+bridge.getName()+"',imageUrl:'"+bridge.getImage()+"',isParent:"+packageFlag+",open:true'}";
					Object obj = gson.fromJson(singleData,Object.class);
					list.add(obj);
				}else{
					SearchResult<Bridge> bridgeList =  bridgeService.getBridgeList(index,pageSize);
					for(int i =0;i < bridgeList.getRsList().size();i++){
						singleData = "{id:'bridge_"+bridgeList.getRsList().get(i).getId()+"',pid:"+pid+",name:'"+bridgeList.getRsList().get(i).getName()+"',imageUrl:'"+bridgeList.getRsList().get(i).getImage()+"',isParent:"+packageFlag+",open:true'}";
						Object obj = gson.fromJson(singleData,Object.class);
						list.add(obj);
					}
				}
			} 
			
			if(nodeType.equals("bridge")){
				if(showType.equals("senStyle")){
					Sensor sensor = new Sensor();
					sensor.setBridgeId(Integer.valueOf(pid));
					SearchResult<Sensor> searchResult =  sensorService.getSensorList(index,pageSize,sensor);
					List<Sensor> sensorList = searchResult.getRsList();
					List<Integer> sensorTypeIdsList = new ArrayList<Integer>();
					for(Sensor sensor_ : sensorList){
						sensorTypeIdsList.add(sensor_.getSensorTypeId());
					} 
					HashSet<Integer> h = new HashSet<Integer>(sensorTypeIdsList);
					sensorTypeIdsList.clear();
					sensorTypeIdsList.addAll(h);
					if(sensorTypeIdsList.size() > 0){
						Integer[] arr = (Integer[])sensorTypeIdsList.toArray(new Integer [sensorTypeIdsList.size()]);
						 List<SensorType> lSensorTypes = sensorTypeService.findByIds(arr);
							for(int i =0;i < lSensorTypes.size();i++){
								singleData = "{id:'senType_"+lSensorTypes.get(i).getId()+"',pid:"+pid+",name:'"+lSensorTypes.get(i).getName()+"',isParent:true,open:true'}";
								Object obj = gson.fromJson(singleData,Object.class);
								list.add(obj);
							}
					}
				}else if(showType.equals("senWork")){
					SearchResult<WorkSection> workSectionList = workSectionService.getWorkSectionList(index,pageSize,Integer.valueOf(pid),-1);
					for(int i =0;i < workSectionList.getRsList().size();i++){
						singleData = "{id:'workSec_"+workSectionList.getRsList().get(i).getId()+"',pid:"+pid+",imageUrl:'"+workSectionList.getRsList().get(i).getImage()+"',name:'"+workSectionList.getRsList().get(i).getName()+"',isParent:true,open:true'}";
						Object obj = gson.fromJson(singleData,Object.class);
						list.add(obj);
					}
				}else if(showType.equals("logicGroup")){
					SearchResult<LogicGroup> logicGroupList = logicGroupService.getLogicGroupList(index,pageSize,Integer.valueOf(pid),null,null);
					for(int i =0;i < logicGroupList.getRsList().size();i++){
						singleData = "{id:'logicGroup_"+logicGroupList.getRsList().get(i).getId()+"',pid:"+pid+",name:'"+logicGroupList.getRsList().get(i).getName()+"',isParent:true,open:true'}";
						Object obj = gson.fromJson(singleData,Object.class);
						list.add(obj);
					}
				}else if(showType.equals("structure")){
					Integer bridgeId = null;
					if(!extraValue.equals("-1")){
						bridgeId = Integer.valueOf(extraValue);
					}
					SearchResult<StructureWarning> searchResult = iStructureWarningService.getStructureWarningList(index, pageSize,bridgeId,null,null);
					for(int i =0;i < searchResult.getRsList().size();i++){
						singleData = "{id:'structure_"+searchResult.getRsList().get(i).getId()+"',pid:"+pid+",name:'"+searchResult.getRsList().get(i).getName()+"',bridgeId:'"+searchResult.getRsList().get(i).getBridgeId()+"',isParent:false,open:true'}";
						Object obj = gson.fromJson(singleData,Object.class);
						list.add(obj);
					}
				}
				
			}else if(nodeType.equals("senType") || nodeType.equals("workSec")){
				Boolean flag = true;
				if(nodeType.equals("workSec")){
					SearchResult<WorkSection> workSectionList = workSectionService.getWorkSectionList(index, pageSize,null,Integer.valueOf(pid));
					if(workSectionList.getRsList().size() > 0){
						for(int i =0;i < workSectionList.getRsList().size();i++){
							singleData = "{id:'workSec_"+workSectionList.getRsList().get(i).getId()+"',pid:"+pid+",imageUrl:'"+workSectionList.getRsList().get(i).getImage()+"',name:'"+workSectionList.getRsList().get(i).getName()+"',isParent:true,open:true'}";
							Object obj = gson.fromJson(singleData,Object.class);
							list.add(obj);
						}
						flag  = false;
					}
				}
				 if (extra != null && extraType == null){
					 flag  = false;
				 }
				if(flag){
					Sensor sensor = null;
					if(nodeType.equals("senType")){
						sensor = new Sensor(null,Integer.valueOf(pid),null);
					}else if(nodeType.equals("workSec")){
						sensor = new Sensor(null,null,Integer.valueOf(pid));
					}
					
					SearchResult<Sensor> sensorList = sensorService.getSensorList(index,pageSize,sensor);
 					for(int i =0;i < sensorList.getRsList().size();i++){
						singleData = "{id:'sen_"+sensorList.getRsList().get(i).getId()+"',pid:"+pid+",name:'"+sensorList.getRsList().get(i).getName()+"',isParent:true,open:true'}";
						Object obj = gson.fromJson(singleData,Object.class);
						list.add(obj);
					}
				}
			}else if(nodeType.equals("sen")){
				//sensorId
				SensorChannel sensorChannel = new SensorChannel(Integer.valueOf(pid));
				SearchResult<SensorChannel> sensorChanaList = sensorChannelService.getSensorChannelList(index,pageSize,sensorChannel);
				for(int i =0;i < sensorChanaList.getRsList().size();i++){
					singleData = "{id:'senChan_"+sensorChanaList.getRsList().get(i).getId()+"',pid:"+pid+",threshold:'"+sensorChanaList.getRsList().get(i).getThreshold()+"',name:'"+sensorChanaList.getRsList().get(i).getName()+"',unit:'"+sensorChanaList.getRsList().get(i).getUnit()+"',isParent:false,open:true'}";
					Object obj = gson.fromJson(singleData,Object.class);
					list.add(obj);
				} 
			}else if(nodeType.equals("logicGroup")){
				//logicGroupId
				SearchResult<LogicGroupOutput> outputList = outputService.getLogicGroupOutputList(index,pageSize,Integer.valueOf(pid));
				for(int i =0;i < outputList.getRsList().size();i++){
					singleData = "{id:'logicOutput_"+outputList.getRsList().get(i).getId()+"',Id:"+outputList.getRsList().get(i).getId()+",pid:"+pid+",logicGroupId:"+outputList.getRsList().get(i).getLogicGroupId()+",name:'"+outputList.getRsList().get(i).getName()+"',isParent:false,open:true'}";
					Object obj = gson.fromJson(singleData,Object.class);
					list.add(obj);
				}
			}
			rs = new RestResp<Object>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,list);
		} catch (Exception e) {
			log.error(e);
			rs = new RestResp<Object>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
 	@POST
	@Path("getBridgeList")
	public Response getBridgeList(
			@QueryParam("index")Integer index,
			@QueryParam("pageSize")Integer pageSize){
		RestResp<Bridge> rs = null;
		try {
			SearchResult<Bridge>searchResult = bridgeService.getBridgeList(index, pageSize);
			RestData<Bridge> restData = new RestData<Bridge>(searchResult.getRsList(),searchResult.getRsCount());
			rs = new RestResp<Bridge>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		} catch (Exception e) {
			rs = new RestResp<Bridge>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	} 
 	
 	@POST
 	@Path("insertBridge")
 	public Response insertBridge(@FormParam("params")String params){
 		RestResp<Bridge> rs = null;
		try {
			Bridge bridge = gson.fromJson(params, Bridge.class);
			bridge.setLastUpdateTime(new Date());
			Bridge bridge_ = bridgeService.insertBridge(bridge);
			rs = new RestResp<Bridge>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,bridge_);
		} catch (Exception e) {
			rs = new RestResp<Bridge>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findBridgeById")
 	public Response findById(@QueryParam("id")Integer id){
 		RestResp<Bridge> rs = null;
		try {
			Bridge bridge= bridgeService.findById(id);
			rs = new RestResp<Bridge>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,bridge);
		} catch (Exception e) {
			rs = new RestResp<Bridge>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateBridgeById")
 	public Response findById(@FormParam("params")String  params){
 		RestResp<Bridge> rs = null;
		try {
			Bridge bridge = gson.fromJson(params, Bridge.class);
			bridge.setLastUpdateTime(new Date());
			bridgeService.updateBridge(bridge);
			rs = new RestResp<Bridge>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<Bridge>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
	@Path("findSensorChannelAndChannelDataByBridgeId")
	public Response findSensorChannelAndChannelDataByBridgeId(@QueryParam("id")Integer id){
 		Bridge bridge = bridgeService.findByPageAndWorkSection(id);
 		RestResp<Bridge> rs = null;
 		try {
	 		if(bridge != null){
	// 			桥梁
	 				if(bridge.getWorkSectionList().size() > 0){
	// 					观测截面
	 					for(WorkSection workSection : bridge.getWorkSectionList()){
	 						if(workSection.getSensorList().size() > 0){
	// 							观测点
	 							for(Sensor sensor : workSection.getSensorList()){
	 								if(sensor.getSensorChannelList().size() > 0){
	// 									观测点通道
	 									for(SensorChannel sensorChannel : sensor.getSensorChannelList()){
	 										if(sensor.getSensorChannelList().size() > 0){
//	 											channelDataList = MongoDBService.getChannelDataListBySensorChannelId(0, 500, null, null, sensorChannel.getId());
	 											ChannelData channelData = MongoDBService.getTheRecentlyChannelDataBySensorChannelId(sensorChannel.getId());
	 											sensorChannel.setLatestChannelData(channelData);
	 										}
	 									}
	 								}
	 							}
	 						}
	 					}
	 				}
	 		}
	 		RestData<Bridge> restData = new RestData<Bridge>(bridge);
			rs = new RestResp<Bridge>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,restData);
		} catch (Exception e) {
			rs = new RestResp<Bridge>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}
 		
 		return Response.ok().entity(rs).build();
 	}
 	
 	
 	
 	
 	@POST
	@Path("deleteBridgeByIds")
	public Response deleteByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<WorkSection> rs = null;
		Map<String, String> map = gson.fromJson(params, Map.class);
		String idsStr = map.get("ids");
 		int [] ids = gson.fromJson(idsStr, int[].class);
		try{
			int count =  bridgeService.deleteByIds(ids);
			rs = new RestResp<WorkSection>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<WorkSection>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
}
