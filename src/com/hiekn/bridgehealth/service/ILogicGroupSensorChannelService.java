package com.hiekn.bridgehealth.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.InitBinder;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.LogicGroupSensorChannel;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface ILogicGroupSensorChannelService{
	
	public LogicGroupSensorChannel insertLogicGroupSensorChannel (LogicGroupSensorChannel logicGroupSensorChannel);
	
	public int deleteByIds(int [] ids);
	
	public SearchResult<LogicGroupSensorChannel> getLogicGroupSensorChannelList (int index, int pageSize,LogicGroupSensorChannel logicGroupSensorChannel);

	public void insertArray(List<LogicGroupSensorChannel> list);
	
	public void updateById(LogicGroupSensorChannel logicGroupSensorChannel);
}
