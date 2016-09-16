package com.hiekn.bridgehealth.mongo;

import java.io.Console;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.ChannelData;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.lucene.search.Sort;
import org.bson.Document;

public class MongoDBService {
	private static final String  MONGODB_DATABASE ="qljk";
	
	private static final String  MONGODB_TABLE ="channelData";
	
	Gson gson = new Gson();
	public static void batchInsertChannelData(List<ChannelData> channelDataList){
		MongoClient mongoClient = MongoDBSingleton.getInstance();
		MongoDatabase db = mongoClient.getDatabase(MONGODB_DATABASE);
		MongoCollection<Document> dbCollection =  db.getCollection(MONGODB_TABLE);
		List<Document>documentList = new ArrayList<Document>();
//		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(ChannelData channelData : channelDataList){
			Document document = new Document();
			document.append("_id", UUID.randomUUID().toString());
			document.append("value", channelData.getValue());
			document.append("time", channelData.getTime());
			document.append("threshold", channelData.getThreshold());
			document.append("bridgeId", channelData.getBridgeId());
			document.append("sensor_channel_name", channelData.getSensorChannelName());
			document.append("sensor_channel_id", channelData.getSensorChannelId());
			document.append("sensor_type_id", channelData.getSensorTypeId());
			document.append("sensor_worksection_id", channelData.getWorksectionId());
			documentList.add(document);
		}
		dbCollection.insertMany(documentList);
	}
	
	public static  List<ChannelData>getChannelDataListBySensorChannelId(int index, int pageSize,Long startTime,Long endTime, Integer SensorChannelId,Integer flag){
		final List<ChannelData> channelDataList = new ArrayList<ChannelData>();
		MongoClient mongoClient = MongoDBSingleton.getInstance();
		MongoDatabase db = mongoClient.getDatabase(MONGODB_DATABASE);
		MongoCollection<Document> dbCollection =  db.getCollection(MONGODB_TABLE);
		Document ql = new Document();
		ql = new Document("sensor_channel_id", SensorChannelId);
		if(startTime == null || endTime ==null){
//			
		}else{
			ql.append("time", new Document("$gte",startTime).append("$lte", endTime));
		}
//		FindIterable<Document> findIterable = dbCollection.find(ql).sort(new Document("time",-1)).limit(pageSize).skip(index);
		FindIterable<Document> findIterable  = null;
		if(flag == 2){
			findIterable = dbCollection.find(ql).limit(pageSize).skip(index).sort(new Document("time",-1)); //倒序
		}else{
			findIterable = dbCollection.find(ql).limit(pageSize).skip(index);
		}
		Gson gson = new Gson();
//		final SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		findIterable.forEach(new Block<Document>(){
		    public void apply(Document document){
 				ChannelData channelData = new ChannelData();
				channelData.setSensorChannelId(document.getInteger("sensor_channel_id"));
				channelData.setSensorChannelName(document.getString("sensor_channel_name"));
//					 //   System.out.println("--->" + document.getString("time"));
				channelData.setTime(document.getLong("time"));
				channelData.setBridgeId(document.getInteger("bridgeId"));
				channelData.setThreshold(document.getString("threshold"));
				channelData.setValue(document.getDouble("value"));
				channelDataList.add(channelData);
		    } 
		});   
		return channelDataList;
	}
	
	public static Long getTotalChannelDataCount(Long startTime,Long endTime, Integer SensorChannelId){
		MongoClient mongoClient = MongoDBSingleton.getInstance();
		MongoDatabase db = mongoClient.getDatabase(MONGODB_DATABASE);
		MongoCollection<Document> dbCollection =  db.getCollection(MONGODB_TABLE);
		Document ql = new Document();
		ql = new Document("sensor_channel_id", SensorChannelId);
		if(startTime == null || endTime ==null){
//		
		}else{
			ql.append("time", new Document("$gte",startTime).append("$lte", endTime));
		}
		Long count =  dbCollection.count(ql);
		return count;
	}
	
	public static  ChannelData getTheRecentlyChannelDataBySensorChannelId(Integer SensorChannelId){
		final ChannelData channelData = new ChannelData();
		MongoClient mongoClient = MongoDBSingleton.getInstance();
		MongoDatabase db = mongoClient.getDatabase(MONGODB_DATABASE);
		MongoCollection<Document> dbCollection =  db.getCollection(MONGODB_TABLE);
		Document ql = new Document();
		ql = new Document("sensor_channel_id", SensorChannelId);
		FindIterable<Document> findIterable = dbCollection.find(ql).sort(new Document("time",-1)).limit(1).skip(0);
		Gson gson = new Gson();
		final SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		findIterable.forEach(new Block<Document>(){
		    public void apply(Document document){
				channelData.setSensorChannelId(document.getInteger("sensor_channel_id"));
				channelData.setSensorChannelName(document.getString("sensor_channel_name"));
				channelData.setTime(document.getLong("time"));
				channelData.setBridgeId(document.getInteger("bridgeId"));
				channelData.setThreshold(document.getString("threshold"));
				channelData.setValue(document.getDouble("value"));
		    } 
		});   
		return channelData;
	}
	
	
	 public static void main(String[] args) throws Exception {
//		String dateString  ="2009-1-1 12:23:45";
//		Date date = new Date();
//		Timestamp sTimestamp =  new Timestamp(System.currentTimeMillis());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
// 		
////		 String date timeStamp 三者转换方法
////		 String -- >date
//		  //   System.out.println("String -- >date" + sdf.parse(dateString));
////		 String -- >timestamp
//		  //   System.out.println("String -- >timestamp" + sdf.parse(dateString).getTime());
////		 date -->String
//		  //   System.out.println(" date -->String" + sdf.format(date));
////		 date -->timestamp
//		  //   System.out.println(" date -->timestamp" + date.getTime());
////		 timeStamp-->String
//		  //   System.out.println(" timeStamp-->String" + sdf.format(sTimestamp));
////		 timeStamp -->date
//		 Date sDate = new Date();
//		 sDate = sTimestamp;
//		  //   System.out.println("timeStamp -->date" + sDate);
		 try {
			 String startTime  ="2016-03-23 18:18:00";
			 String endTime  ="2016-03-23 19:17:59";
//			 Long count = getTotalChannelDataCount(startTime, endTime, 1111);
			 
 		  //   System.out.println(getChannelDataListBySensorChannelId(0,999999,startTime,endTime,103,2).size());
			 
//			  //   System.out.println(count);
// 			ChannelData channelData = getTheRecentlyChannelDataBySensorChannelId(103);
//			getTotalChannelDataCount();
// 			 //   System.out.println(getTotalChannelDataCount()); 
		} catch (Exception e) {
			 //   System.out.println(e);
		} 
	} 
}
