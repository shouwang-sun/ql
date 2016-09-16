package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jnr.ffi.Struct.int16_t;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.ChannelDataHistoryFile;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.LogicGroupSensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.ChannelDataHistoryFileMapper;
import com.hiekn.bridgehealth.dao.LogicGroupResultMapper;
import com.hiekn.bridgehealth.dao.LogicGroupSensorChannelMapper;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;
import com.hiekn.bridgehealth.service.ILogicGroupSensorChannelService;
@Service("logicGroupSensorChannelService")
public class LogicGroupSensorChannelService extends SqlSessionDaoSupport implements  ILogicGroupSensorChannelService{

	@Override
	public LogicGroupSensorChannel insertLogicGroupSensorChannel(LogicGroupSensorChannel logicGroupSensorChannel) {
		try {
			int count = getSqlSession().insert(LogicGroupSensorChannelMapper.class.getName() + ".insert",logicGroupSensorChannel);
		} catch (Exception e) {
			System.err.println(e);
		}
		return logicGroupSensorChannel;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(LogicGroupSensorChannelMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	    return count;
	}

	@Override
	public SearchResult<LogicGroupSensorChannel> getLogicGroupSensorChannelList(int index, int pageSize, LogicGroupSensorChannel logicGroupSensorChannel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logicGroupId", logicGroupSensorChannel.getLogicGroupId());
		map.put("sensorChannelId", logicGroupSensorChannel.getSensorChannelId());
		map.put("index", index);
		map.put("pageSize", pageSize);
		
		List<LogicGroupSensorChannel> logicGroupSensorChannelList= getSqlSession().selectList(LogicGroupSensorChannelMapper.class.getName() + ".findByPage",map);
		int count =  getSqlSession().selectOne(LogicGroupSensorChannelMapper.class.getName() + ".findByItemNum",map);
		SearchResult<LogicGroupSensorChannel> sResult = new SearchResult<LogicGroupSensorChannel>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(logicGroupSensorChannelList);
		return sResult;
	}

	@Override
	public void insertArray(List<LogicGroupSensorChannel> list) {
		try {
			int count = getSqlSession().insert(LogicGroupSensorChannelMapper.class.getName() + ".insertArray",list);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public void updateById(LogicGroupSensorChannel logicGroupSensorChannel) {
		try {
			int count = getSqlSession().update(LogicGroupSensorChannelMapper.class.getName() + ".updateById",logicGroupSensorChannel);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

}
