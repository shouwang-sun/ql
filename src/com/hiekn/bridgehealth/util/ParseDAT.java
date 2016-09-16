package com.hiekn.bridgehealth.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.service.ISensorService;


public class ParseDAT {
	
	 private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	 private static ISensorChannelService iSensorChannelService = (ISensorChannelService)ctx.getBean("sensorChannelService");
	 private static ISensorService iSensorService = (ISensorService)ctx.getBean("sensorService");
	 
	 private static final String PARSE_FILE_STRING = CommonResource.PARSE_FILE_STRING;
	
	public static void main (String[] args) throws Exception {
// 		MongoDBService.getChannelDataListBySensorChannelId(104);
		String dict="F:\\qljk\\MelakBridge\\RealtimeData\\20160323\\";
		String fileName="01SAV1106-DZ#20160323457896541#N#D#20MS.DAT";
		List<ChannelData> channelDataList = parseFile(dict, fileName);
		MongoDBService.batchInsertChannelData(channelDataList);
	}
	
	//解析实时数据
	public static  List<ChannelData> parseFile (String dict,String fileName) throws Exception{
		String[] arr = fileName.split("-");
		Integer bridgeId_ = Integer.valueOf(arr[0]);
		String sensorName = arr[1];
		String sensorChannelName = arr[2].split("#")[0];
		
		Sensor sensor_ = new Sensor(sensorName);
//		sensor_.setName(sensorName);
		SearchResult<Sensor> sensorResult = iSensorService.getSensorList(0, 99999, sensor_);
		
		SensorChannel sensorChannel_ = new SensorChannel();
		sensorChannel_.setBridgeId(bridgeId_);
		sensorChannel_.setName(sensorChannelName);
		
		if(sensorResult.getRsList().size() > 0){
			sensorChannel_.setSensorId(sensorResult.getRsList().get(0).getId());
		}else{
			return null;
		}
		
//		System.out.println("bridgeId---->" + bridgeId_);
//		System.out.println("sensorName---->" + sensorName);
//		System.out.println("sensorChannelName---->" + sensorChannelName);
 		SearchResult<SensorChannel> searchResult =  iSensorChannelService.getSensorChannelList(0, 99999, sensorChannel_);
 		SensorChannel sensorChannel = null;
// 		System.out.println("sensorChannel.size()-->" + searchResult.getRsList().size());
 		if(searchResult.getRsList().size() > 0){
 			sensorChannel = searchResult.getRsList().get(0);//根据名称查找，只有一个;
 		}else{
 			 //   System.out.println("没有对象");
 			return new ArrayList<ChannelData>();
 		}
 		 
 		Integer sensorTypeId = sensorChannel.getSensorTypeId();
 		Integer workSectionId = sensorChannel.getWorkSectionId();
 		Integer sensorChannelId = sensorChannel.getId();
 		Integer bridgeId = sensorChannel.getBridgeId();
 		String threshold = sensorChannel.getThreshold();
		List<ChannelData> channelDataList = new ArrayList<ChannelData>();
		//System.out.println("readDatPy------->" + PARSE_FILE_STRING);
		//System.out.println("dict------->" + dict);
		//System.out.println("fileName------->" + fileName);
		ProcessBuilder pb = new ProcessBuilder("python",PARSE_FILE_STRING,dict,fileName);//dict 是dat文件存放的位置
		try {
			Process process = pb.start();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			String [] time_data = null;
			String time = null;
			String data = null;
//			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while((line = bufferedReader.readLine()) != null){
				//System.out.println("line------------->" + line);
				time_data = line.split(" ");
				time = time_data[0];
				data = time_data[1];
				ChannelData channelData = new ChannelData();
				channelData.setSensorChannelId(sensorChannelId);
				channelData.setTime(Long.valueOf(time));
				channelData.setSensorChannelName(sensorChannelName);
				channelData.setBridgeId(bridgeId);
				channelData.setWorksectionId(workSectionId);
				channelData.setSensorTypeId(sensorTypeId);
//				channelData.setThreshold(threshold);
				if(threshold != null){
					channelData.setThreshold(threshold);
				}
				channelData.setValue(Double.valueOf(data));
 				channelDataList.add(channelData);
			}
			 // System.out.println("channelDataList---->" + channelDataList);
			bufferedReader.close();
		} catch (Exception e) {
//		     System.out.println("error---->" + e);
			e.printStackTrace();
		}
		return channelDataList;
	}
} 


