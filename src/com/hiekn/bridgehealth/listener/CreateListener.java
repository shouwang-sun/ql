package com.hiekn.bridgehealth.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hiekn.bridgehealth.util.CommonResource;
import com.hiekn.bridgehealth.util.FileMonitor;

public class CreateListener  implements ServletContextListener{
	
	private static final String path = CommonResource.MONITORFILE_STRING;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.err.println("-----------------------------结束监控文件------------------------");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//////		实时解析逻辑组
//		Timer timer = new Timer();
//		timer.schedule(new TaskForSetLogicResult(),0,1000);
////////		
////////		实时生成预警
//		Timer timer2 = new Timer();
//		timer2.schedule(new TaskForSetStructureWarningResult(),0,1000 * 60 * 2); //两分钟
////
//////  	每月自动生成评估结果
//		Timer timer3 = new Timer();
//		timer3.schedule(new TaskForSetEvaluateResult(),0,1000 * 60 * 2); //20天	
		
		try {
//			System.err.println("-----------------------------开始监控文件------------------------");
			FileMonitor fileMonior = new FileMonitor();
			fileMonior.monitor(path,new FileListener());  
			fileMonior.start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
