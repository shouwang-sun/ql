package com.hiekn.bridgehealth.test;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bouncycastle.crypto.modes.CTSBlockCipher;
import org.python.antlr.PythonParser.eval_input_return;
import org.python.antlr.PythonParser.return_stmt_return;
import org.python.modules._hashlib.Hash;
import org.python.modules.itertools.starmap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.vote.ConsensusBased;

import com.codahale.metrics.health.HealthCheck.Result;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.EvaluateProject;
import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.LogicGroupSensorChannel;
import com.hiekn.bridgehealth.bean.ManualCheckObject;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorTypeAttribute;
import com.hiekn.bridgehealth.bean.StructureWarningResult;
import com.hiekn.bridgehealth.bean.User;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.service.IBridgeService;
import com.hiekn.bridgehealth.service.IChannelDataService;
import com.hiekn.bridgehealth.service.IEvaluateProjectResultService;
import com.hiekn.bridgehealth.service.IEvaluateProjectService;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;
import com.hiekn.bridgehealth.service.ILogicGroupSensorChannelService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.IManualCheckObjectService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.ISensorTypeAttributeService;
import com.hiekn.bridgehealth.service.IStructureWarningResultService;
import com.hiekn.bridgehealth.service.IUserLogService;
import com.hiekn.bridgehealth.service.IUserService;
import com.hiekn.bridgehealth.service.IWorkSectionService;

import jnr.ffi.Struct.int16_t;
import jnr.ffi.annotations.In;
import junit.framework.TestCase;

public class test extends TestCase{
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static IEvaluateProjectResultService iEvaluateProjectResultService = (IEvaluateProjectResultService) ctx.getBean("evaluateProjectResultService");
	private static IStructureWarningResultService iStructureWarningResultService = (IStructureWarningResultService) ctx.getBean("structureWarningResultService");
	private static ISensorChannelService iSensorChannelService = (ISensorChannelService) ctx.getBean("sensorChannelService");
	private static IBridgeService iBridgeService = (IBridgeService)ctx.getBean("bridgeService");
	private static IWorkSectionService iWorkSectionService = (IWorkSectionService) ctx.getBean("workSectionService");
	private static IEvaluateProjectService iEvaluateProjectService =(IEvaluateProjectService)ctx.getBean("evaluateProjectService");
	private static ILogicGroupResultService iLogicGroupResultService = (ILogicGroupResultService)ctx.getBean("logicGroupResultService");
	private static IUserService iUserService = (IUserService)ctx.getBean("userService");
	private static IManualCheckObjectService iManualCheckObjectService = (IManualCheckObjectService)ctx.getBean("manualCheckObjectService");
	private static IChannelDataService iChannelDataService = (IChannelDataService)ctx.getBean("channelDataService");
	private static ILogicGroupSensorChannelService iLogicGroupSensorChannelService = (ILogicGroupSensorChannelService)ctx.getBean("logicGroupSensorChannelService");
	private static ILogicGroupService iLogicGroupService = (ILogicGroupService)ctx.getBean("logicGroupService");
	private static ISensorTypeAttributeService iSensorTypeAttributeService = (ISensorTypeAttributeService)ctx.getBean("sensorTypeAttributeService");
	/*public void test(){
		Date date = new Date();
		Integer year = date.getYear() + 1900;
		Integer month = date.getMonth() + 1;
		EvaluateProjectResult evaluateProjectResult = new EvaluateProjectResult();
		evaluateProjectResult.setBridgeId(78);
		 //   System.out.println("year--->" + year);
		 //   System.out.println("month--->" + month);
		evaluateProjectResult.setYear(year);
		evaluateProjectResult.setMonth(month);
		try {
			SearchResult<EvaluateProjectResult> eResult =  iEvaluateProjectResultService.getEvaluateProjectResultList(0, 9999999, null, null, evaluateProjectResult);
			List<EvaluateProjectResult> list = eResult.getRsList();
			 //   System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*public void test2(){
		try {
			SearchResult<StructureWarningResult> searchResult = iStructureWarningResultService.getStructureWarningResultList(0, 9999999, null, null, null);
			List<StructureWarningResult> slList =  searchResult.getRsList();
			 //   System.out.println(slList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*public void test2(){
		try {
			SensorChannel sensorChannel = new SensorChannel();
			SearchResult<SensorChannel> searchResult = iSensorChannelService.getSensorChannelList(0, 9999999, sensorChannel);
			List<SensorChannel> slList =  searchResult.getRsList();
			 //   System.out.println(slList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} */
	 /* public void test2(){
		try {
			List<Bridge> bridgeList=iBridgeService.findByPageAndWorkSection(1);
			 //   System.out.println(bridgeList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } */
		/*public void test2(){
			try {
				List<WorkSection> workSectionList=iWorkSectionService.findByPageAndSensorType(5);
				 //   System.out.println(workSectionList.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }*/
	/*public static List<EvaluateProject> evaluateProjectTotalList = new ArrayList<EvaluateProject>();
	
	  public void test2(){
		try {
			EvaluateProject evaluateProject = new EvaluateProject();
			evaluateProject.setBridgeId(1);
			evaluateProject.setPid(0);
			SearchResult<EvaluateProject> evaSearchResult =   iEvaluateProjectService.getEvaluateProjectList(0, 999999, null, null, evaluateProject);
			List<EvaluateProject> evaluateProjectList = evaSearchResult.getRsList();
			evaluateProjectTotalList.addAll(evaluateProjectList);
			digui(evaluateProjectList);
//           找到这棵树的所有的节点
			Long startTime = new Date().getTime();
			List<Integer> pidList = new ArrayList<Integer>();
			 for(EvaluateProject evaluateProject_ : evaluateProjectTotalList){
				 pidList.add(evaluateProject_.getPid());
				 //   System.out.println("id--->" + evaluateProject_.getId()+"   pid--->" + evaluateProject_.getPid()+"   name--->" + evaluateProject_.getName());
			 } 
			 Long endTime = new Date().getTime();
			 
//			 将所有EvaluateProject的pid放入一个列表中,并去重
			 List<Integer> newList = new ArrayList<Integer>();
			  for(int i = 0; i<pidList.size();i++){
				 if(!newList.contains(pidList.get(i))){
					 newList.add(pidList.get(i));
				 }
			 } 
 //			  倒叙,从根节点往上算
			  Collections.reverse(newList);
			   //   System.out.println(newList);
			  
			  List<EvaluateProject> evaluateProjects = new ArrayList<EvaluateProject>();
			  Integer parentId= 0;
			  Float healthyRate =  (float) 0L;
			  String curStartTime="";
			  String curEndTime="";
			  String resultName= "";
			  int level = 0;
			  String advice ="";
			  
			  SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Calendar calendar = Calendar.getInstance();
			  int year = calendar.get(Calendar.YEAR); 
			  int month = calendar.get(Calendar.MONTH) + 1;
			  if(month > 9){
				    curStartTime = year+"-"+month+"-01 00:00:00";
				    curEndTime = year+"-"+month+"-"+returnStr(year,month);
				}else{
					curStartTime = year+"-"+("0"+month)+"-01 00:00:00";
					curEndTime = year+"-"+("0"+month)+"-"+returnStr(year,month);
				}
//			             所有pid的列表
			  for(Integer integer : newList){
//				  找到所有该节点的子节点
				  evaluateProjects = iEvaluateProjectService.findByPid(integer);
				  if(evaluateProjects.size() > 0){
					  for(EvaluateProject evaluateProject_ : evaluateProjects){
//						  根据pid判断是否有子节点,如果有的话,把子节点的值相加然后赋值,如果没有,则计算值
						  EvaluateProjectResult evaluateProjectResult = new EvaluateProjectResult();
						  evaluateProjectResult.setEvaluateProjectPid(evaluateProject_.getId());//把当前节点的id传入参数当做子节点的pid
						  SearchResult<EvaluateProjectResult> eResult = iEvaluateProjectResultService.getEvaluateProjectResultList(0, 9999999, null, null, evaluateProjectResult);
						  Double healthyValue = (double) 0;
						  if(eResult.getRsList().size() > 0){ //如果列表大于0,说明有子节点,把子节点相加
							  for(EvaluateProjectResult evaluateProjectResult_ : eResult.getRsList()){
								  healthyValue += evaluateProjectResult_.getHealthyValue();
								   //   System.out.println("子节点值---->" + evaluateProjectResult_.getHealthyValue());
							  }
							   //   System.out.println("父节点  : pid--->"+evaluateProject_.getId()+"   healthyValue----->" + healthyValue);
							   //   System.out.println("------------------------------------------------------------");
						  }else{ //否则就计算根节点
							  Date startTimeLG = null;
							  Date endTimeLG = null;
							  startTimeLG = sdf.parse(curStartTime);
							  endTimeLG = sdf.parse(curEndTime);
							  healthyRate = evaluateProject_.getHealthyRate(); //健康权重值
							  LogicGroupResult logicGroupResult = new LogicGroupResult();
							  logicGroupResult.setBridgeId(evaluateProject_.getBridgeId());
							  logicGroupResult.setLogicGroupOutputId(evaluateProject_.getOutputId());
							  healthyValue = iLogicGroupResultService.getAvgValue(startTimeLG, endTimeLG, logicGroupResult) * healthyRate;
							   //   System.out.println("根节点  : id--->"+evaluateProject_.getId()+"   healthyValue----->" + healthyValue);
							   //   System.out.println("原始数据 ： data-->" + iLogicGroupResultService.getAvgValue(startTimeLG, endTimeLG, logicGroupResult));
							   //   System.out.println("------------------------------------------------------------");
						  }
						  
						  if(healthyValue > 80){
							  level = 1;
							  advice = "正常巡检,养护";
						  }else{
							  level=2;
							  advice = "启动专家检测";
						  }
						  
						  resultName = year+"年"+month+"月["+evaluateProject_.getName()+"]在线评估结果";
						  EvaluateProjectResult evaluateProjectResult_ = new EvaluateProjectResult();
						  evaluateProjectResult_.setAdvice(advice);
						  evaluateProjectResult_.setBridgeId(evaluateProject_.getBridgeId());
						  evaluateProjectResult_.setEvaluateProjectId(evaluateProject_.getId());
						  evaluateProjectResult_.setEvaluateProjectPid(evaluateProject_.getPid());
						  evaluateProjectResult_.setHealthyValue(healthyValue);
						  evaluateProjectResult_.setLastUpdateTime(new Date());
						  evaluateProjectResult_.setProjectMonth(month);
						  evaluateProjectResult_.setProjectYear(year);
						  evaluateProjectResult_.setName(resultName);
						  iEvaluateProjectResultService.insertEvaluateProjectResult(evaluateProjectResult_);
						   //   System.out.println("添加成功!");
					  }
				  }
			  } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
 	
	public void digui(List<EvaluateProject> evaluateProjects){
		List<EvaluateProject> evaluateProjects2 =  new ArrayList<EvaluateProject>();
		if(evaluateProjects.size() > 0){
			for(EvaluateProject evaluateProject : evaluateProjects){
				evaluateProjects2 = iEvaluateProjectService.findByPid(evaluateProject.getId());
				if(evaluateProjects2.size() > 0){
					evaluateProjectTotalList.addAll(evaluateProjects2);
					digui(evaluateProjects2);
				}
			}
		}
	}
	
	public  String returnStr(int year,int month){
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(4);
		list.add(6);
		list.add(9);
		list.add(11);
		String end = "";
		if(isLeapYear(year)){
			if(month == 2){
				end += "29 23:59:59";
			}else{
				if(list.contains(month)){
					end += "30 23:59:59";
				}else{
					end += "31 23:59:59";
				}
			}
		}else{
			if(month == 2){
				end += "28 23:59:59";
			}else{
				if(list.contains(month)){
					end += "30 23:59:59";
				}else{
					end += "31 23:59:59";
				}
			}
		}
		return end;
	}
	
	public boolean isLeapYear(int year){
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
		    return true;//闰年
		}else{
			return false;
		}
	}*/
	
	/*public void test3() throws ParseException{
//		 
		try {
			List<StructureWarningResult> list = new ArrayList<StructureWarningResult>();
			StructureWarningResult structureWarningResult = new StructureWarningResult();
			structureWarningResult.setValue(223.34);
			structureWarningResult.setStartTime(new Date());
			structureWarningResult.setDealResult(0);
			structureWarningResult.setBridgeId(1);
			structureWarningResult.setStructureWarningId(23);
			structureWarningResult.setSensorChannelId(38);
			structureWarningResult.setLastUpdateTime(new Date());
			structureWarningResult.setLogicGroupOutputId(23);
			structureWarningResult.setThreshold("23.45");
			list.add(structureWarningResult);
			 //   System.out.println(list);
			iStructureWarningResultService.insertArrayStructureWarningResult(list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Float Float(double d) {
		// TODO Auto-generated method stub
		return null;
	}*/
//	public void test4() throws ParseException{
//		/*ManualCheckObject manualCheckObject = new ManualCheckObject();
//		manualCheckObject.setName("测试字段666");
//		manualCheckObject.setOriginName("测试字段!");*/
//		
//		try {
////			SearchResult<ChannelData> searchResult= iChannelDataService.getChannelDataList(0, 10, null, null, 103,1);
////			Long count = searchResult.getRsCount();
////			 //   System.out.println(count);
//			List<LogicGroupSensorChannel> list = new ArrayList<LogicGroupSensorChannel>();
//			
//			LogicGroupSensorChannel logicGroupSensorChannel = new LogicGroupSensorChannel();
//			logicGroupSensorChannel.setLogicGroupId(189);
//			logicGroupSensorChannel.setSensorChannelId(260);
//			
//			LogicGroupSensorChannel logicGroupSensorChannel_ = new LogicGroupSensorChannel();
//			logicGroupSensorChannel_.setLogicGroupId(190);
//			logicGroupSensorChannel_.setSensorChannelId(270);
//			
//			list.add(logicGroupSensorChannel);
//			list.add(logicGroupSensorChannel_);
//			iLogicGroupSensorChannelService.insertArray(list);
////			SearchResult<LogicGroupSensorChannel> lResult = iLogicGroupSensorChannelService.getLogicGroupSensorChannelList(0, 999999, logicGroupSensorChannel);
////			 //   System.out.println(lResult.getRsList().get(0).getLogicGroup().getName());
////			 //   System.out.println(lResult.getRsList().get(0).getSensorChannel().getName());
////			LogicGroupSensorChannel logicGroupSensorChannel_ = iLogicGroupSensorChannelService.insertLogicGroupSensorChannel(logicGroupSensorChannel);
////			SearchResult<LogicGroup> searchResult = iLogicGroupService.getLogicGroupList(0, 99999, null, null, null);
////			 //   System.out.println(searchResult.getRsList().get(0).getLogicGroupSensorChannelList().size());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public void test4() {
//		int [] arr = {0};
//		int count = iBridgeService.deleteByIds(arr);
//		System.out.println(count);
//		System.out.println("helliWorld");
//		int [] arr = {161};
//		try {
//			int count = iBridgeService.deleteByIds(arr);
//			System.out.println(count);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		LogicGroupSensorChannel logicGroupSensorChannel = new LogicGroupSensorChannel();
//		logicGroupSensorChannel.setId(58);
//		logicGroupSensorChannel.setLogicGroupId(58);
//		logicGroupSensorChannel.setSensorChannelId(58);
//		logicGroupSensorChannel.setNickName("测试");
//		List<LogicGroupSensorChannel> list = new ArrayList<LogicGroupSensorChannel>();
//		list.add(logicGroupSensorChannel);
//		iLogicGroupSensorChannelService.updateArray(list);
		/*try {
			StructureWarningResult structureWarningResult = new StructureWarningResult();
			SearchResult<StructureWarningResult> lResult = iStructureWarningResultService.getStructureWarningResultList(0, 9999, null, null, structureWarningResult);
			System.out.println(lResult.getRsList().size());
		} catch (Exception e) {
			System.err.println(e);
		}*/
		SensorTypeAttribute sensorTypeAttribute = new SensorTypeAttribute();
		sensorTypeAttribute.setName("自定义属性");
		sensorTypeAttribute.setSensorTypeId(-1);
		sensorTypeAttribute.setType("string");
		
		try {
//			iSensorTypeAttributeService.insertSensorTypeAttribute(sensorTypeAttribute);
			int [] arr = {1}; 
			iSensorTypeAttributeService.deleteByIds(arr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
