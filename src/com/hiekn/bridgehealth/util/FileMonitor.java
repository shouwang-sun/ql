package com.hiekn.bridgehealth.util;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitor {
//	监听文件变化
	FileAlterationMonitor monitor = null;
	
	public void monitor(String path,FileAlterationListener listener){
		FileAlterationObserver observer = new FileAlterationObserver(new File(path));
		monitor = new FileAlterationMonitor(5000, observer); //五秒钟
		monitor.addObserver(observer);  
		observer.addListener(listener);  
	}
	
	public void stop() throws Exception{  
		monitor.stop();  
	}  
	public void start() throws Exception {  
		monitor.start();  
	}  
}
