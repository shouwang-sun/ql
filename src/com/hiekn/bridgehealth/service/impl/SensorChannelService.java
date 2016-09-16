package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.AttributeMapper;
import com.hiekn.bridgehealth.dao.SensorChannelMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.SensorTypeMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.ISensorChannelService;

@Service("sensorChannelService")
public class SensorChannelService extends SqlSessionDaoSupport implements ISensorChannelService {
 
	@Override
	public SearchResult<SensorChannel> getSensorChannelList(Integer index,Integer pageSize, SensorChannel sensorChannel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("bridgeId", sensorChannel.getBridgeId());
		map.put("sensorId", sensorChannel.getSensorId());
		map.put("workSectionId", sensorChannel.getWorkSectionId());
		map.put("sensorTypeId", sensorChannel.getSensorTypeId());
		map.put("name", sensorChannel.getName());
		List<SensorChannel> list = null;
		int count = -1;
		try {
			list = getSqlSession().selectList(SensorChannelMapper.class.getName()+".findByPage",map);
			count =  getSqlSession().selectOne(SensorChannelMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		SearchResult<SensorChannel> sResult = new SearchResult<SensorChannel>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public SensorChannel insertSensorChannel(SensorChannel sensorChannel)
			throws Exception {
		try {
			int count = getSqlSession().insert(SensorChannelMapper.class.getName() + ".insert",sensorChannel);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return sensorChannel;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	    return count;
	}

	@Override
	public SensorChannel findById(Integer id) throws Exception {
		SensorChannel sensorChannel = null;
		try {
			sensorChannel = getSqlSession().selectOne(SensorChannelMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return sensorChannel;
	}

	@Override
	public void updateSensorChannel(SensorChannel sensorChannel)throws Exception {
		try {
			getSqlSession().update(SensorChannelMapper.class.getName() + ".updateById",sensorChannel);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public void updateThresholdById(Integer id,String threshold) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("threshold", threshold);
		map.put("id", id);
		try {
			getSqlSession().update(SensorChannelMapper.class.getName() + ".updateThresholdById",map);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public String findByName(Integer id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(SensorChannelMapper.class.getName() + ".findByName",id);
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteByWorkSectionIds(int [] worksectionIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteByWorkSectionIds",worksectionIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteBySensorIds(int [] sensorIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteBySensorIds",sensorIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

}
