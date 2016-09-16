package com.hiekn.bridgehealth.mongo;

 
import com.hiekn.bridgehealth.util.CommonResource;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * 
 * @ClassName: MongoDBSingleton
 * @author wzh
 * @date 2015年12月30日 下午7:47:25
 * @Email wangzh@hiekn.com
 */
public class MongoDBSingleton {

	//mongo url
	private static final String MONGODB_URL = CommonResource.MONGO_IP_STRING;

	//mongo port
	private static final int MONGODB_PORT = CommonResource.MONGO_PORT;

	private static MongoDBSingleton singleton;
	private static MongoClient mgClient = null;
	
	private MongoDBSingleton(){
		MongoClientOptions options = MongoClientOptions.builder().connectTimeout(10000).socketTimeout(10000).build();
		mgClient = new MongoClient(new ServerAddress(MONGODB_URL, MONGODB_PORT), options);
	}
	
	public MongoClient getClient() {
		return mgClient;
	}
	
	public static MongoClient getInstance(){
		if(singleton == null || mgClient == null){
			synchronized (MongoDBSingleton.class) {
				if(singleton == null || mgClient == null){
					singleton = new MongoDBSingleton();
				}
			}
		}
		return singleton.getClient();
	}
	
}
