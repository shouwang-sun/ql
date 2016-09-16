package com.hiekn.bridgehealth.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.map.HashedMap;
import org.python.antlr.PythonParser.else_clause_return;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.EvaluateProject;
import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.service.IBridgeService;
import com.hiekn.bridgehealth.service.IEvaluateProjectResultService;
import com.hiekn.bridgehealth.service.IEvaluateProjectService;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;
import com.puppycrawl.tools.checkstyle.gui.Main;

public class TaskForSetEvaluateResult extends TimerTask {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static IEvaluateProjectResultService iEvaluateProjectResultService = (IEvaluateProjectResultService) ctx.getBean("evaluateProjectResultService");
	private static IBridgeService iBridgeService = (IBridgeService)ctx.getBean("bridgeService");
	private static IEvaluateProjectService iEvaluateProjectService =(IEvaluateProjectService)ctx.getBean("evaluateProjectService");
	private static ILogicGroupResultService iLogicGroupResultService = (ILogicGroupResultService)ctx.getBean("logicGroupResultService");
	
	public static List<EvaluateProject> evaluateProjectTotalList = new ArrayList<EvaluateProject>();
	
	@Override
	public void run() {
//		找到所有桥梁
//		判断是否有该桥梁的评估报告
//  	在每座桥梁下面找到其子节点，直到根节点
//		分别由下至上根据节点的值（逻辑组输出的值--逻辑组输出结果的平局值） * 权重计算出当前节点的健康值
//		存入结果值
		try {
			Integer index = 0;
			Integer pageSizeLong = 99999;
//			找到所有的桥梁
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			
			SearchResult<Bridge>bridgeList = iBridgeService.getBridgeList(index, pageSizeLong);
			if(bridgeList.getRsList().size() > 0){
				for(Bridge bridge : bridgeList.getRsList()){
					EvaluateProjectResult evaluateProjectResult = new EvaluateProjectResult();
					evaluateProjectResult.setBridgeId(bridge.getId());
					evaluateProjectResult.setProjectMonth(month);
					evaluateProjectResult.setProjectYear(year);
					evaluateProjectResult.setEvaluateProjectPid(0);
//					判断该是否存在该桥梁的体系报告
					
					EvaluateProject evaluateProject = new EvaluateProject();
					evaluateProject.setBridgeId(bridge.getId());
					evaluateProject.setPid(0);
					Boolean boolean_ = iEvaluateProjectResultService.judgeItemExist(evaluateProjectResult);
					Boolean boolean__ = iEvaluateProjectService.judgeItemExist(evaluateProject);
					if(!boolean_){
//						如果并没有体系结构结果, 
						EvaluateProjectResult evaluateProjectResult_ = new EvaluateProjectResult();
						String name = year+"年"+month+"月[☆"+bridge.getName()+"]在线评估结果";
						evaluateProjectResult_.setName(name);
						evaluateProjectResult_.setBridgeId(bridge.getId());
						evaluateProjectResult_.setLastUpdateTime(new Date());
						evaluateProjectResult_.setEvaluateProjectPid(0);
						evaluateProjectResult_.setProjectMonth(month);
						evaluateProjectResult_.setProjectYear(year);
						iEvaluateProjectResultService.insertEvaluateProjectResult(evaluateProjectResult_);
						 //   System.out.println("新建桥梁体系结构结果");
					}
					
					 if(!boolean__){
//						如果并没有体系结构, 
						EvaluateProject evaluateProject_ = new EvaluateProject();
						evaluateProject_.setBridgeId(bridge.getId());
						evaluateProject_.setDescription(bridge.getName());
						evaluateProject_.setHealthyRate((float) 1.0);
						evaluateProject_.setLastUpdateTime(new Date());
						evaluateProject_.setName(bridge.getName());
						evaluateProject_.setPid(0);
						iEvaluateProjectService.insertEvaluateProject(evaluateProject_);
						 //   System.out.println("新建桥梁体系结构");
					}
					calculateVal(bridge.getId());
				}
			}
		} catch (Exception e) {
			 //   System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void calculateVal(Integer bridgeId){
		try {
			evaluateProjectTotalList = new ArrayList<EvaluateProject>();
			EvaluateProject evaluateProject = new EvaluateProject();
			evaluateProject.setBridgeId(bridgeId);
			evaluateProject.setPid(0);
			SearchResult<EvaluateProject> evaSearchResult =   iEvaluateProjectService.getEvaluateProjectList(0, 999999, null, null, evaluateProject);
			List<EvaluateProject> evaluateProjectList = evaSearchResult.getRsList();
			evaluateProjectTotalList.addAll(evaluateProjectList);
			digui(evaluateProjectList);
			
//           找到这棵树的所有的节点
			List<Integer> pidList = new ArrayList<Integer>();
			 for(EvaluateProject evaluateProject_ : evaluateProjectTotalList){
				 pidList.add(evaluateProject_.getPid());
				  //   System.out.println("id--->" + evaluateProject_.getId()+"   pid--->" + evaluateProject_.getPid()+"   name--->" + evaluateProject_.getName());
			 } 
			 
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
			  Float threshold1 = (float)0;
			  Float threshold2 = (float)0;
			  Float threshold3 = (float)0;
			  
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
				  	EvaluateProject evaluateProjectObj= new EvaluateProject();
				  	evaluateProjectObj.setBridgeId(bridgeId);
				  	evaluateProjectObj.setPid(integer);
					SearchResult<EvaluateProject> evaluateProjects_ =   iEvaluateProjectService.getEvaluateProjectList(0, 999999, null, null, evaluateProjectObj);
					List<EvaluateProject> evaluateProjects2 = evaluateProjects_.getRsList();
					 //   System.out.println("evaluateProjects2.size()----->" + evaluateProjects2.size());
				  if(evaluateProjects2.size() > 0){
					  for(EvaluateProject evaluateProject_ : evaluateProjects2){
						   //   System.out.println(evaluateProject_.getName());
//						  根据pid判断是否有子节点,如果有的话,把子节点的值相加然后赋值,如果没有,则计算值
						  EvaluateProjectResult evaluateProjectResult = new EvaluateProjectResult();
						  evaluateProjectResult.setEvaluateProjectPid(evaluateProject_.getId());//把当前节点的id传入参数当做子节点的pid
						  evaluateProjectResult.setBridgeId(evaluateProject_.getBridgeId());
						  SearchResult<EvaluateProjectResult> eResult = iEvaluateProjectResultService.getEvaluateProjectResultList(0, 9999999, null, null, evaluateProjectResult);
						  Double healthyValue = (double) 0;
						   //   System.out.println("eResult.getRsList()--------------------->" + eResult.getRsList());
						  if(eResult.getRsList().size() > 0 || evaluateProject_.getPid() == 0){ //如果列表大于0,说明有子节点,把子节点相加
							  for(EvaluateProjectResult evaluateProjectResult_ : eResult.getRsList()){
								  healthyValue += evaluateProjectResult_.getHealthyValue();
								   //   System.out.println("子节点值---->" + evaluateProjectResult_.getHealthyValue());
							  }
							   //   System.out.println("父节点  : pid--->"+evaluateProject_.getId()+"   healthyValue----->" + healthyValue);
						  }else{ //否则就计算根节点
							  Date startTimeLG = null;
							  Date endTimeLG = null;
							  startTimeLG = sdf.parse(curStartTime);
							  endTimeLG = sdf.parse(curEndTime);
							  healthyRate = evaluateProject_.getHealthyRate(); //健康权重值
							  LogicGroupResult logicGroupResult = new LogicGroupResult();
//							  logicGroupResult.setBridgeId(evaluateProject_.getBridgeId());
							  logicGroupResult.setLogicGroupOutputId(evaluateProject_.getOutputId());
							   //   System.out.println("evaluateProject_.getHealthyRate()------>" + evaluateProject_.getHealthyRate());
//							   //   System.out.println("evaluateProject_.getBridgeId()------->" + evaluateProject_.getBridgeId());
							   //   System.out.println("evaluateProject_.getOutputId()-------->" + evaluateProject_.getOutputId());
							   //   System.out.println("startTimeLG-------->" + startTimeLG);
							   //   System.out.println("endTimeLG-------->" + endTimeLG);
							  try {
								  healthyValue = iLogicGroupResultService.getAvgValue(startTimeLG, endTimeLG, logicGroupResult) * healthyRate;
							  } catch (Exception e) {
								   //   System.out.println(e);
								  healthyValue = -1.0;
							  }
								 
							   //   System.out.println("根节点  : id--->"+evaluateProject_.getId()+"   healthyValue----->" + healthyValue);
							   //   System.out.println("原始数据 ： data-->" + iLogicGroupResultService.getAvgValue(startTimeLG, endTimeLG, logicGroupResult));
						  }
						  if(evaluateProject_.getThreshold() != null){
							  threshold1 = Float.valueOf(evaluateProject_.getThreshold().split(",")[0]);
							  threshold2 = Float.valueOf(evaluateProject_.getThreshold().split(",")[1]);
							  threshold3 = Float.valueOf(evaluateProject_.getThreshold().split(",")[2]);
							  
							  if(healthyValue > 0 && healthyValue < threshold1){
								  level = 4;
							  }else if(healthyValue > threshold1 && healthyValue < threshold2){
								  level = 3;
							  }else if(healthyValue > threshold2 && healthyValue < threshold3){
								  level = 2;
							  }else if(healthyValue > threshold3){
								  level = 1;
							  }
						  }
						  
						  resultName = year+"年"+month+"月["+evaluateProject_.getName()+"]在线评估结果";
//							判断该是否存在该桥梁的报告
						  
						  EvaluateProjectResult evaluateProjectResult_ = new EvaluateProjectResult();
						  evaluateProjectResult_.setAdvice(advice);
						  evaluateProjectResult_.setBridgeId(evaluateProject_.getBridgeId());
						  evaluateProjectResult_.setEvaluateProjectPid(evaluateProject_.getPid());
						  evaluateProjectResult_.setEvaluateProjectId(evaluateProject_.getId());
						  evaluateProjectResult_.setHealthyValue(healthyValue);
						  evaluateProjectResult_.setLastUpdateTime(new Date());
						  evaluateProjectResult_.setProjectMonth(month);
						  evaluateProjectResult_.setProjectYear(year);
						  evaluateProjectResult_.setLevel(level);
						  evaluateProjectResult_.setName(resultName);
						  evaluateProjectResult_.setLastUpdateTime(new Date());
						  
						  Boolean boolean_ = iEvaluateProjectResultService.judgeItemExist(evaluateProjectResult_);
						  
						  if(boolean_ || evaluateProject_.getPid()  == 0){
 //							  如果存在,就更新
							  if(evaluateProject_.getPid()  == 0){
								  evaluateProjectResult_.setEvaluateProjectPid(0);
								  iEvaluateProjectResultService.updateByBridgeEvaluateProjectId(evaluateProjectResult_);
							  }else{
								  iEvaluateProjectResultService.updateByEvaluateProjectId(evaluateProjectResult_);
							  }
							 
							   //   System.out.println("更新成功!"); 
						  }else{
//							  不存在,就添加
							  iEvaluateProjectResultService.insertEvaluateProjectResult(evaluateProjectResult_);
							   //   System.out.println("添加成功!");
						  }
						   //   System.out.println("-------------------------------------------------------------------------------------------------------------");
					  }
				  }
			  } 
		} catch (Exception e) {
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
	}
	
	public static void main(String [] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new TaskForSetEvaluateResult(),0, 1000 * 60);
	}
}
	

