package com.hiekn.bridgehealth.util;

import java.io.InputStream;
import java.util.Properties;

import org.python.antlr.ast.Str;

public class CommonResource {
	
	static Properties props = new Properties();
	
	static{
		InputStream in = CommonResource.class.getClassLoader().getResourceAsStream("config.properties");
		try{
			props.load(in);
		}catch (Exception e) {
			System.exit(1);
		}
	}
	
	public static final String FILE_ABSOLUTE_FILE_PATH = props.getProperty("file.absoluteFilePath"); 
	
	public static final String FILE_STRING = props.getProperty("file.dir"); 
	
	public static final String MONGO_IP_STRING = props.getProperty("mongo.ip"); 
	
	public static final int MONGO_PORT = Integer.valueOf(props.getProperty("mongo.port")); 
		
	public static final String MONITORFILE_STRING = props.getProperty("monitor.folder");
	
	public static final String MONITOR_SENSORCHANNEL_STRING = props.getProperty("monitor.folderSensorChannelData");
	
	public static final String PARSE_FILE_STRING = props.getProperty("parse.File");
}
