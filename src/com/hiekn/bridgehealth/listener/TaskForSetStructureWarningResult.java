package com.hiekn.bridgehealth.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jnr.ffi.annotations.IgnoreError;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.StructureWarningResult;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.IChannelDataService;
import com.hiekn.bridgehealth.service.ILogicGroupConstantService;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.IStructureWarningResultService;
import com.hiekn.bridgehealth.service.IStructureWarningService;

public class TaskForSetStructureWarningResult extends TimerTask{

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static ILogicGroupService iLogicGroupService = (ILogicGroupService)ctx.getBean("logicGroupService");
	private static ILogicGroupOutputService iLogicGroupOutputService = (ILogicGroupOutputService)ctx.getBean("logicGroupOutputService"); 
	private static ILogicGroupResultService iLogicGroupResultService = (ILogicGroupResultService)ctx.getBean("logicGroupResultService");
	private static IChannelDataService iChannelDataService = (IChannelDataService)ctx.getBean("channelDataService");
	private static IStructureWarningService iStructureWarningService = (IStructureWarningService) ctx.getBean("structureWarningService");
	private static IStructureWarningResultService iStructureWarningResultService = (IStructureWarningResultService)ctx.getBean("structureWarningResultService");
	
	@Override
	public void run() {
		int index = 0;
		int pageSizeLong = 999999999;
		//找到所有的预警设置
		//根据预警对象的逻辑组输出id和观测点通道的id找到这些数据
		//将这些数据和阈值相比较，如果这些数据的值小于阈值，则报警
		try {
			Long endTime = new Date().getTime();
			Long startTime = endTime - 60 * 1000 * 2;  //两分钟之前
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			SearchResult<StructureWarning> searchResult;
			 //   System.out.println("--------------正在扫描所有的预警设置--------------");
			Date testStartdate = new Date();
			searchResult = iStructureWarningService.getStructureWarningList(index, pageSizeLong,null, null, null);
			List<StructureWarning> structureWarningList = searchResult.getRsList();
			List<StructureWarningResult>structureWarningResultlist = new ArrayList<StructureWarningResult>();
//			 //   System.out.println("structureWarningLsist.size()--->" + structureWarningList.size());
			if(structureWarningList.size() > 0){
				System.err.println(structureWarningList.size());
				for(int i = 0 ;i< structureWarningList.size() ;i ++){
					StructureWarning structureWarning = structureWarningList.get(i);
					LogicGroupResult logicGroupResult = new LogicGroupResult();
					logicGroupResult.setBridgeId(structureWarning.getBridgeId());
					logicGroupResult.setLogicGroupId(structureWarning.getLogicGroupId());
					logicGroupResult.setLogicGroupOutputId(structureWarning.getOutputId());
//					查找逻辑组结果
					SearchResult<LogicGroupResult> searchResult_ = iLogicGroupResultService.getLogicResultList(index, pageSizeLong, startTime, endTime, logicGroupResult,1); 
//					找到所有的逻辑组结果
					List<LogicGroupResult>logicGroupResultList = searchResult_.getRsList();
//					 //   System.out.println("logicGroupResultList.size()--->" + searchResult_.getRsCount());
					Float threshold1 = (float)0;
					Float threshold2 = (float)0;
					Float threshold3 = (float)0;
					Float threshold4 = (float)0;
					Double curThreshold = (double)0;
					int level = 0;
					if(logicGroupResultList.size() > 0){
						 for(LogicGroupResult logicGroupResult_ : logicGroupResultList){
//							如果当前值满足条件，则添加记录
						    if(logicGroupResult_.getResult() != null &&  logicGroupResult_.getThreshold() != null){
						    	curThreshold = logicGroupResult_.getResult();
						    	threshold1  = Float.valueOf(logicGroupResult_.getThreshold().split(",")[0]);
						    	threshold2  = Float.valueOf(logicGroupResult_.getThreshold().split(",")[1]);
						    	threshold3  = Float.valueOf(logicGroupResult_.getThreshold().split(",")[2]);
						    	threshold4  = Float.valueOf(logicGroupResult_.getThreshold().split(",")[3]);
						    	if(curThreshold > threshold3 && curThreshold < threshold2){
						    		level = 0; //正常
						    		break;
						    	}else {
						    		if(curThreshold > threshold2 && curThreshold < threshold1 || curThreshold < threshold3 && curThreshold > threshold4){
						    			level = 1;
						    		}else if(curThreshold > threshold1 || curThreshold < threshold4){
						    			level = 2;
						    		}
						    	}
									StructureWarningResult structureWarningResult = new StructureWarningResult();
									structureWarningResult.setValue(logicGroupResult_.getResult());
									structureWarningResult.setStartTime(new Date());
									structureWarningResult.setDealResult(0);
									structureWarningResult.setBridgeId(logicGroupResult_.getBridgeId());
									structureWarningResult.setStructureWarningId(structureWarning.getId());
									structureWarningResult.setLogicGroupId(logicGroupResult_.getLogicGroupId());
									structureWarningResult.setLastUpdateTime(new Date());
									structureWarningResult.setLogicGroupOutputId(structureWarning.getOutputId());
									structureWarningResult.setThreshold(structureWarning.getThreshold());
									structureWarningResult.setLevel(level);
									structureWarningResult.setSensorId(structureWarning.getSensorId());
									structureWarningResultlist.add(structureWarningResult);
						    }
						} 
					}
//					找到所有的观测点通道
					List<ChannelData> channelDataList = MongoDBService.getChannelDataListBySensorChannelId(index, pageSizeLong, startTime, endTime, structureWarning.getSensorChannelId(),1);
//					 //   System.out.println("channelDataList.size()--->" + channelDataList());
					 if(channelDataList.size()  > 0){
						for(ChannelData channelData : channelDataList){
//							如果当前值小于阈值，则添加记录
							if(channelData.getValue() != null && channelData.getThreshold() !=null){
								curThreshold = channelData.getValue();
						    	threshold1  = Float.valueOf(channelData.getThreshold().split(",")[0]);
						    	threshold2  = Float.valueOf(channelData.getThreshold().split(",")[1]);
						    	threshold3  = Float.valueOf(channelData.getThreshold().split(",")[2]);
						    	threshold4  = Float.valueOf(channelData.getThreshold().split(",")[3]);
						    	if(curThreshold > threshold3 && curThreshold < threshold2){
						    		level = 0; //正常
						    		break;
						    	}else {
						    		if(curThreshold > threshold2 && curThreshold < threshold1 || curThreshold < threshold3 && curThreshold > threshold4){
						    			level = 1;
						    		}else if(curThreshold > threshold1 || curThreshold < threshold4){
						    			level = 2;
						    		}
						    	}
									StructureWarningResult structureWarningResult = new StructureWarningResult();
									structureWarningResult.setValue(channelData.getValue());
									structureWarningResult.setStartTime(new Date());
									structureWarningResult.setDealResult(0);
									structureWarningResult.setBridgeId(channelData.getBridgeId());
									structureWarningResult.setStructureWarningId(structureWarning.getId());
									structureWarningResult.setSensorChannelId(structureWarning.getSensorChannelId());
									structureWarningResult.setLastUpdateTime(new Date());
									structureWarningResult.setLogicGroupOutputId(structureWarning.getOutputId());
									structureWarningResult.setThreshold(structureWarning.getThreshold());
									structureWarningResult.setLevel(level);
									structureWarningResult.setSensorId(structureWarning.getSensorId());
									structureWarningResultlist.add(structureWarningResult);
							}
						}
					}
					 
					Date testEnddate = new Date();
					Long tstDateLong = (testEnddate.getTime() - testStartdate.getTime()) / 1000;
					 //   System.out.println("--------------共添加"+structureWarningResultlist.size()+"个,共花费"+tstDateLong+"秒-------------");
					if(structureWarningResultlist.size() > 0){
						iStructureWarningResultService.insertArrayStructureWarningResult(structureWarningResultlist);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 //   System.out.println(e);
		}
	}

	public static void main(String [] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new TaskForSetStructureWarningResult(),0, 2000);
	}
}
