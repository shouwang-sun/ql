package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.SensorChannelMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.SensorTypeMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.ISensorService;

@Service("sensorService")
public class SensorService extends SqlSessionDaoSupport implements ISensorService{
  
	@Override
	public SearchResult<Sensor> getSensorList(Integer index, Integer pageSize,Sensor sensor) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("sensorTypeId", sensor.getSensorTypeId());
		map.put("workSectionId", sensor.getWorkSectionId());
		map.put("bridgeId", sensor.getBridgeId());
		map.put("name", sensor.getName());
		List<Sensor> list = null;
		int count =  -1;
		try {
			list = getSqlSession().selectList(SensorMapper.class.getName()+".findByPage",map);
			count =  getSqlSession().selectOne(SensorMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		SearchResult<Sensor> sResult = new SearchResult<Sensor>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}
	
	@Override
	public Sensor insertSensor(Sensor sensor) throws Exception {
		try {
			int count = getSqlSession().insert(SensorMapper.class.getName() + ".insert",sensor);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return sensor;
	}

	@Override
	public void updateSensor(Sensor sensor) throws Exception {
		try {
			int count = getSqlSession().update(SensorMapper.class.getName() + ".updateById",sensor);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
	}

	@Override
	public Sensor findById(Integer id) throws Exception {
		Sensor sensor = null;
		try {
			sensor = getSqlSession().selectOne(SensorMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return sensor;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteByIds(int[] ids)  throws Exception{
		int count = -1;
		count =  getSqlSession().delete(SensorMapper.class.getName() + ".deleteByIds",ids);
	    count = getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteBySensorIds",ids);
		return count;
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteByWorkSectionIds(int [] workSectionIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorMapper.class.getName() + ".deleteByWorkSectionIds",workSectionIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
}
