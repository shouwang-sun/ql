package com.hiekn.bridgehealth.bean;

import java.util.List;

public class LogicGroupSensorChannel {
	 private Integer id;

	 private Integer logicGroupId;

	 private Integer sensorChannelId;
	 
	 private SensorChannel sensorChannel;
	 
	 private String nickName;
	 
	 private LogicGroup logicGroup;
	 
	 public LogicGroupSensorChannel(){
		 
	 }
	 
	 public LogicGroupSensorChannel(Integer logicGroupId,Integer sensorChannelId,String nickName){
		 this.logicGroupId = logicGroupId;
		 this.sensorChannelId = sensorChannelId;
		 this.nickName = nickName;
	 }
	 
	 public LogicGroupSensorChannel(Integer id,Integer logicGroupId,Integer sensorChannelId,String nickName){
		 this.logicGroupId = logicGroupId;
		 this.id = id;
		 this.sensorChannelId = sensorChannelId;
		 this.nickName = nickName;
	 }
	 
	 public LogicGroupSensorChannel(Integer logicGroupId,Integer sensorChannelId){
		 this.logicGroupId = logicGroupId;
		 this.sensorChannelId = sensorChannelId;
	 }
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLogicGroupId() {
		return logicGroupId;
	}

	public void setLogicGroupId(Integer logicGroupId) {
		this.logicGroupId = logicGroupId;
	}

	public Integer getSensorChannelId() {
		return sensorChannelId;
	}

	public void setSensorChannelId(Integer sensorChannelId) {
		this.sensorChannelId = sensorChannelId;
	}

	public SensorChannel getSensorChannel() {
		return sensorChannel;
	}

	public void setSensorChannel(SensorChannel sensorChannel) {
		this.sensorChannel = sensorChannel;
	}

	public LogicGroup getLogicGroup() {
		return logicGroup;
	}

	public void setLogicGroup(LogicGroup logicGroup) {
		this.logicGroup = logicGroup;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	 
	 
}
