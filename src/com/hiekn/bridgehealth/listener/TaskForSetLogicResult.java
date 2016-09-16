package com.hiekn.bridgehealth.listener;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import net.sf.json.JSONObject;

import org.bouncycastle.asn1.x509.qualified.TypeOfBiometricData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.LogicGroupSensorChannel;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.ILogicGroupConstantService;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;
import com.hiekn.bridgehealth.service.ILogicGroupSensorChannelService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.util.CommonResource;

public class TaskForSetLogicResult extends TimerTask{ 
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static ILogicGroupService iLogicGroupService = (ILogicGroupService)ctx.getBean("logicGroupService");
	private static ILogicGroupConstantService iLogicGroupConstantService = (ILogicGroupConstantService)ctx.getBean("logicGroupConstantService");
	private static ILogicGroupOutputService iLogicGroupOutputService = (ILogicGroupOutputService)ctx.getBean("logicGroupOutputService"); 
	private static ILogicGroupResultService iLogicGroupResultService = (ILogicGroupResultService)ctx.getBean("logicGroupResultService");
	private static ILogicGroupSensorChannelService iLogicGroupSensorChannelService = (ILogicGroupSensorChannelService)ctx.getBean("logicGroupSensorChannelService");

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			autoSetLogicResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	Gson gson = new Gson();

	//	自动生成逻辑组结果
	//	逻辑组的下一次启动时间就是执行脚本的时间，
	//	后台一直有一个5秒的定时器在定时扫描所有的逻辑组，
	//	把“当前时间”和“逻辑组的下一次启动时间”做比较，
	//	如果“当前时间”大于“逻辑组的下一次启动时间”，就找到该逻辑组的脚本地址，用python去解析，
	//	并将该逻辑组的下一次更新时间改为当前时间 + 间隔的时间

	public void autoSetLogicResult() throws Exception{
		int index = 0;
		int pageSizeLong = 999999999;
//		System.out.println("--------------正在扫描所有的逻辑组-----------");
		SearchResult<LogicGroup> sResult = iLogicGroupService.getLogicGroupList(index,pageSizeLong,null,null,null);
		Date testStartdate = new Date();
		List<LogicGroup> logicGroupList = sResult.getRsList();
		Long curDate = null; //当前时间
		Long nextRunDate = null;//逻辑组的时间
		Long newDate = null;
		Integer logicGroupId = 0;
		Integer bridgeId = 0;
		String fileLocation = CommonResource.FILE_ABSOLUTE_FILE_PATH;
		String filePath = "";
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("logicGroupList.size()-------->" + logicGroupList.size());
		if(logicGroupList.size() > 0){
			for(LogicGroup logicGroup : logicGroupList){
//				System.out.println("logicGroupName-------->" + logicGroup.getName());
				List<LogicGroupResult> logicGroupResultList = new ArrayList<LogicGroupResult>();
				curDate = new Date().getTime();
				nextRunDate = logicGroup.getNextRunTime();
				if(curDate > nextRunDate){
					if(logicGroup.getAlgorithm() != null){
						filePath = fileLocation + logicGroup.getAlgorithm();
					}else{
						break;
					}
					Map<String, Object> mapJSON = new HashMap<String, Object>();
					logicGroupId = logicGroup.getId();
					bridgeId = logicGroup.getBridgeId();
					newDate = curDate + logicGroup.getTimeInterval();
					iLogicGroupService.updateNextRunDateById(logicGroupId,newDate);
					LogicGroupSensorChannel logicGroupSensorChannel = new LogicGroupSensorChannel();
					logicGroupSensorChannel.setLogicGroupId(logicGroupId);
					SearchResult<LogicGroupSensorChannel> searchResult =  iLogicGroupSensorChannelService.getLogicGroupSensorChannelList(index, pageSizeLong, logicGroupSensorChannel);
//					System.out.println("逻辑组id--->" +logicGroupId);
					List<SensorChannel> sensorChannelList = new ArrayList<SensorChannel>();
//					System.out.println("searchResult.getRsList().size()--->" +searchResult.getRsList().size());
					if(searchResult.getRsList().size() > 0){
						for(LogicGroupSensorChannel logicGroupSensorChannel_ : searchResult.getRsList()){
							SensorChannel sensorChannel = logicGroupSensorChannel_.getSensorChannel();
							String nickName = logicGroupSensorChannel_.getNickName();
							Map<String,List> mapSensorChannelDataMap = new HashMap<String, List>();
//							String endTime  = sdf.format(curDate);
//							String startTime  = sdf.format(curDate - logicGroup.getTimeInterval());
							Long endTime  = curDate;
							Long startTime  = curDate - logicGroup.getTimeInterval() - 1000;
							List<ChannelData> channelDataList =  MongoDBService.getChannelDataListBySensorChannelId(0,999999999,startTime,endTime,sensorChannel.getId(),1);
							List<Double> valueList = new ArrayList<Double>();
							List<Long> dateList = new ArrayList<Long>();
//							System.out.println("channelDataList.size()---->" + channelDataList.size());
							if(channelDataList.size() > 0){
								for(ChannelData channelData : channelDataList){
									valueList.add(channelData.getValue());
									dateList.add(channelData.getTime());
								}
//								System.out.println("valueList--->" + valueList);
//								System.out.println("dateList--->" + dateList);
								mapSensorChannelDataMap.put("\"data\"", valueList);
								mapSensorChannelDataMap.put("\"time\"", dateList);
								mapJSON.put("\""+nickName+"\"", mapSensorChannelDataMap);
//							 System.out.println("mapJSON--->" + mapJSON);
//							 System.out.println("----------------------------------------------");
							}
						}
					}
					
					List<LogicGroupConstant> logicGroupConstantList = iLogicGroupConstantService.getLogicGroupConstantByLogicGroupId(logicGroupId);//根据逻辑组id获得所有常量
					if(logicGroupConstantList.size() > 0){
						for(LogicGroupConstant logicGroupConstant : logicGroupConstantList){
							mapJSON.put("\""+logicGroupConstant.getName() +"\"",Integer.parseInt(logicGroupConstant.getValue()));//对应的常量按照 键值 放入一个map
						}
					}
					//System.out.println("mapJSON------->" + mapJSON);
					String mapJSONStr = gson.toJson(mapJSON);
					mapJSONStr = writeTxt(mapJSONStr);
					//System.out.println("mapJSONStr------->" + mapJSONStr);//获得刚才的路径
					if(mapJSON.size() > 0){
						List<LogicGroupOutput>logicGroupOutputList = iLogicGroupOutputService.getLogicGroupOutputList(logicGroupId);
						Map resMap = null;
						try {
							resMap = doAlgorim(filePath,mapJSONStr);
							//System.out.println("resMap------->" + resMap);
							if(resMap != null){
								if(resMap.containsKey("2d_histogram")){ //如果是图片
									Map result= (Map) resMap.get("2d_histogram");
									//	List<Long> timeList = (List<Long>) result.get("time"); //获取时间列表
//									System.out.println("result----------->" + result);
									if(result != null){
										List<Long> timeList = (List<Long>) result.get("time"); //获取时间列表
										LogicGroupResult logicGroupResult = new LogicGroupResult();
										logicGroupResult.setLogicGroupId(logicGroupId);
										logicGroupResult.setLogicGroupOutputId(logicGroupOutputList.get(0).getId());
										logicGroupResult.setOutString(resMap.toString());
										logicGroupResult.setBridgeId(bridgeId);
										logicGroupResult.setAddTime(timeList.get(0));
										
										logicGroupResult.setPicSrc("windrose.jpeg"); //图片唯一，会重复覆盖
										logicGroupResultList.add(logicGroupResult); 
//										System.out.println("------------------------------------------------------------------>添加图片");
									}
								}else {
									for(LogicGroupOutput logicGroupOutput : logicGroupOutputList){
										if(resMap.containsKey(logicGroupOutput.getName())){ //logicGroupOutput.getName()
											//System.out.println("logicGroupOutput.getName()--------->" + logicGroupOutput.getName());
											Map result= (Map) resMap.get(logicGroupOutput.getName()); //获取返回结果
											//									Map result= (Map) resMap.get("SAV1106-DA"); //获取返回结果  (测试)
											// //System.out.println("输出名称-->" +logicGroupOutput.getName());
											//  //System.out.println("result-->" + result );
											if(result != null){
												List<Double> dataList = (List<Double>) result.get("result");//获取数据列表
												List<Long> timeList = (List<Long>) result.get("time"); //获取时间列表
												//  //System.out.println("dataList--->" + dataList);
												//  //System.out.println("timeList--->" + timeList);

												if(result != null && dataList.size() > 0 && timeList.size() > 0){
													for(int i=0;i<dataList.size() ;i ++){
														LogicGroupResult logicGroupResult = new LogicGroupResult();
														logicGroupResult.setLogicGroupId(logicGroupId);
														logicGroupResult.setLogicGroupOutputId(logicGroupOutput.getId());
//														System.out.println(dataList.get(i).getClass().getName());
//														System.out.println("dataList.get(i)--->" +dataList.get(i));
														
														logicGroupResult.setResult(dataList.get(i));
														logicGroupResult.setBridgeId(bridgeId);
														logicGroupResult.setAddTime(timeList.get(i));
														logicGroupResult.setThreshold(logicGroupOutput.getThreshold());
														logicGroupResultList.add(logicGroupResult);
//														System.out.println("------------------------------------------------------------------>添加数据");
													}
												} 
											}
										}
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
				
				Date testEnddate = new Date();
				Long tstDateLong = (testEnddate.getTime() - testStartdate.getTime()) / 1000;
				System.out.println("--------------共添加"+logicGroupResultList.size()+"个,共花费"+tstDateLong+"秒-------------");
				if(logicGroupResultList.size() > 0){
					iLogicGroupResultService.insertArray(logicGroupResultList);
				} 
			}
		}
	}
	
	public Map doAlgorim(String path,String param){
		//图片
		//		param = "{\"\"wind_speed\"\": {\"\"data\"\": [2.0679034890925507, 4.090239558245592, 0.8171826390860162, 5.851419139332822, 0.8162952164982606], \"\"time\"\": [10.0, 12.0, 14.0, 16.0, 18.0]}, \"\"wind_direction\"\": {\"\"data\"\": [231.06754611236843, 70.3991721829053, 324.48700983707005, 249.34663390717495, 281.7474180610217], \"\"time\"\": [10.0, 12.0, 14.0, 16.0, 18.0]},\"\"image_dir\"\":\"\"F:/789.jpeg\"\"}";
		//		path = "F:/Java/apache-tomcat-7.0.64/webapps/ql/file/windrose_logic_group.py";
		//数据
//		path = "F:/Java/apache-tomcat-7.0.64/webapps/ql/file/rms_logic_group.py";
//		param = "{\"\"SAV1106-DA\"\": {\"\"data\"\": [4.70836e-43, 2.20221e-23, -0.110402, 4.70836e-43,2.20221e-23], \"\"time\"\": [1445011200000, 1445011200020, 1445011200040, 1445011200060, 1445011200080]}, \"\"SEW1112-DX\"\": {\"\"data\"\": [0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9], \"\"time\"\": [1445011200000, 1445011200020, 1445011200040, 1445011200060, 1445011200080]},\"\"dt\"\": 2}";

		//	     //System.out.println("path--->" + path);
		JSONObject jsonObjectText = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(param)));
			String line="";
			String res= "";
			try {
				while ((line = reader.readLine()) != null ) {
					res += line;
				}
				if(!res.equals("")){
					jsonObjectText = JSONObject.fromObject(res);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//删除临时储存的文件
//		String location = param.replaceAll("\\\\","/");
//		String realPath = location.replaceAll("/","\\\\\\\\");
//		F:\Java\apache-tomcat-7.0.64\webapps\ql\file\txtFold\7414469489117.txt


		File file = new File(param);
		  // //System.out.println("file->" + file);
		if(file.exists() && file.exists()){
			file.delete();
		}

		param = jsonObjectText.toString();
		param = param.replaceAll(":", ": ");
 		param = param.replaceAll("\\\\\"", "");//把"\ 替换成 空 linux
//		System.out.println("path--->" + path); 
//		System.out.println("param--->" + param); 
 		 ProcessBuilder pb = new ProcessBuilder("python", path,param);
		 JSONObject jsonObject = null;
		 try {
			 Process process  = pb.start();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			 String line = "";
			 String res= "";
			 while ((line = reader.readLine()) != null ) {
// 				 System.out.println("line1----------------->" + line);
				 res += line;
//				 System.out.println("res----------------->" + res);
			 }
			 reader.close();
//			 System.out.println("re2----------------->" + res);
			 try {
				 if(res.indexOf("NaN") != -1){
					 res = res.replaceAll("NaN", "0.0");
	 			  }
				 jsonObject = JSONObject.fromObject(res);
				 //System.out.println("lin3----------------->" + jsonObject);
			 } catch (Exception e) {
				 // TODO: handle exception
			 }
		 } catch (IOException e) {
//			 e.printStackTrace();
		 }
		 return (Map)jsonObject;
	}

	public String writeTxt(String str){
		String txtPath = CommonResource.FILE_STRING  + "txtFold";
		//System.out.println("txtPath------->" + txtPath);
		//		//System.out.println("str------->" + str);
		File dir = new File(txtPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String fileName = System.nanoTime() + ".txt";
		String txt = dir + File.separator + fileName; 
		PrintWriter pw= null;
		try {
			pw = new PrintWriter(txt);
			pw.write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pw != null){
				pw.close();
			}
		}
		return txt;
	}

	public String findServerPath(){  
		String classPath = this.getClass().getClassLoader().getResource("/").getPath();  
		try {  
			classPath = URLDecoder.decode(classPath, "gb2312");  
		} catch (UnsupportedEncodingException e) {  
			e.printStackTrace();  
		}  
		String[] strPath = classPath.split("/");  
		String path = "";  
		for(int i = 0;i < strPath.length ; i++){  
			if(i > 0 && i <= 3){  
				path = path + strPath[i]+"/";  
			}  
		}  
		return path;  
	} 

	public static void main(String [] args) throws InterruptedException {
		//		String string = "F:\\Java\\apache-tomcat-7.0.64\\webapps\\ql\\file\\txtFold\\7881819576173.txt";
		//		File file = new File(string);
		//		file.delete();
		//		 //System.out.println(file); 
				Timer timer = new Timer();
				timer.schedule(new TaskForSetLogicResult(),0, 1000);
	}
}
