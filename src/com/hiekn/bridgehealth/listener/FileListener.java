package com.hiekn.bridgehealth.listener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import jnr.ffi.annotations.In;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.ChannelDataHistoryFile;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.IChannelDataHistoryFileService;
import com.hiekn.bridgehealth.service.IChannelDataService;
import com.hiekn.bridgehealth.service.ISensorChannelService;
import com.hiekn.bridgehealth.util.CommonResource;
import com.hiekn.bridgehealth.util.FileMonitor;
import com.hiekn.bridgehealth.util.ParseDAT;

public class FileListener implements FileAlterationListener{

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static ISensorChannelService iSensorChannelService = (ISensorChannelService)ctx.getBean("sensorChannelService");
	private static IChannelDataService iChannelDataService = (IChannelDataService)ctx.getBean("channelDataService");
	private static IChannelDataHistoryFileService iChannelDataHistoryFileService = (IChannelDataHistoryFileService)ctx.getBean("channelDataHistoryService");
	
	 @Override
	public void onDirectoryChange(File file) {
		//System.out.println("文件目录改变--------->"+file.getName());
		
	}

	@Override
	public void onDirectoryCreate(File file) {
		System.out.println("文件目录创建--------->"+file.getName());
		
	}

	@Override
	public void onDirectoryDelete(File file) {
		//System.out.println("文件目录删除--------->"+file.getName());
		
	}

	@Override
	public void onFileChange(File file) {
		//System.out.println("文件改变--------->"+file.getName());
		
	}

	@Override
	public void onFileCreate(File file) {
		String path = file.getAbsolutePath();
		String fileName = file.getName();
//		System.out.println("fileName--------->"+fileName);
		Long fileSize = file.length();
//		System.out.println("fileSize------------->" +Double.valueOf(fileSize));
//		String sensorChannelName = fileName.substring(2,fileName.indexOf("#"));
//		System.out.println("观测点通道名称--------->"+sensorChannelName);
//		实时数据
//		System.out.println("absolutePath------------>" +path );
		if(path.indexOf("RealtimeData") != -1){
//			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String dateStr = sdf.format(new Date());
//			System.out.println("fileCreatePath--------->"+path);
	 		List<ChannelData> channelDataList = new ArrayList<ChannelData>();
			try {
//				System.out.println("afterpath----->" + path);
//				System.out.println("fileName----->" + fileName);
//				path = path.substring(0,path.lastIndexOf("\\") + 1 ); //windows
				path = path.substring(0,path.lastIndexOf("/") + 1 );  //linux
//				System.out.println("path.lastIndexOf------------>" +path );
	 			channelDataList = ParseDAT.parseFile(path, fileName);
 	 			//System.out.println("channelDataList.size()----->" + channelDataList.size());
	 			if(channelDataList.size() > 0){
	 				MongoDBService.batchInsertChannelData(channelDataList);
	 			}
	 			File file_ = new File(file.getAbsolutePath());
	 			if(file_.isFile() && file_.exists()){
	 				file_.delete();
	 			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(path.indexOf("HistoryData") != -1){
//			历史数据
			 // System.out.println(file);
			ChannelDataHistoryFile channelDataHistoryFile = new ChannelDataHistoryFile();
			channelDataHistoryFile.setDataSize(fileSize);
			channelDataHistoryFile.setName(fileName);
			channelDataHistoryFile.setDatTypeUrl(path);
			channelDataHistoryFile.setStartTime(new Date());
			SearchResult<SensorChannel> searchResult;
			Integer sensorChannelId ;
			try {
				String[] arr = fileName.split("-");
				Integer bridgeId_ = Integer.valueOf(arr[0]);
				String sensorName = arr[1];
				String sensorChannelName = arr[2].split("#")[0];
				SensorChannel sensorChannel_ = new SensorChannel();
				sensorChannel_.setBridgeId(bridgeId_);
				sensorChannel_.setName(sensorChannelName);
				searchResult = iSensorChannelService.getSensorChannelList(0, 999999999, sensorChannel_);
				 // System.out.println("searchResult.getRsList().size()--------------->" + searchResult.getRsList().size());
				sensorChannelId = searchResult.getRsList().get(0).getId();
				channelDataHistoryFile.setSensorChannelId(sensorChannelId);
				iChannelDataHistoryFileService.insertChannelDataHistoryFile(channelDataHistoryFile);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
	}

	@Override
	public void onFileDelete(File file) {
		//System.out.println("文件删除--------->"+file.getName());
		
	}

	@Override
	public void onStart(FileAlterationObserver file) {
		
		
	}

	@Override
	public void onStop(FileAlterationObserver file) {
		
	}
	private static final String path = CommonResource.MONITORFILE_STRING;
	public static void main(String [] args) throws InterruptedException {
		FileMonitor fileMonior = new FileMonitor();
		fileMonior.monitor(path,new FileListener());  
		try {
			fileMonior.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
