package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialArray;


import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.SensorTypeAttribute;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.SensorTypeAttributeMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.ISensorTypeAttributeService;
@Service("sensorTypeAttributeService")
public class SensorTypeAttributeService extends SqlSessionDaoSupport implements ISensorTypeAttributeService{

	public SearchResult<SensorTypeAttribute> getSensorTypeAttributeList(Integer index,Integer pageSize,Integer sensorTypeId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("sensorTypeId", sensorTypeId);
		List<SensorTypeAttribute> list = null;
		List<SensorTypeAttribute> list_ = null;
		try {
			list = getSqlSession().selectList(SensorTypeAttributeMapper.class.getName() + ".findByPage",map);
			list_ = getSqlSession().selectList(SensorTypeAttributeMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		SearchResult<SensorTypeAttribute> searchResult = new SearchResult<SensorTypeAttribute>();
		searchResult.setRsList(list);
		searchResult.setRsCount(Long.valueOf(list_.size()));
		return searchResult;
	}

	@Override
	public SensorTypeAttribute insertSensorTypeAttribute(SensorTypeAttribute attribute) throws Exception {
		try {
			int count = getSqlSession().insert(SensorTypeAttributeMapper.class.getName() + ".insert",attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		return attribute;
	}

	@Override
	public void updateSensorTypeAttributeById(SensorTypeAttribute attribute) throws Exception {
		try {
			getSqlSession().update(SensorTypeAttributeMapper.class.getName() + ".updateById",attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
	
	@Override
	public void updateSensorTypeAttributeByName(SensorTypeAttribute attribute) throws Exception {
		try {
			getSqlSession().update(SensorTypeAttributeMapper.class.getName() + ".updateByName",attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public int deleteByNameList(String[] nameList) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorTypeAttributeMapper.class.getName() + ".deleteByName",nameList);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

	@Override
	public int deleteByIds(int [] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorTypeAttributeMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteBySensorTypeIds(int[] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorTypeAttributeMapper.class.getName() + ".deleteBySensorTypeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
}
