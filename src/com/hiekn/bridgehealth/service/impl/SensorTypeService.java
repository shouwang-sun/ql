package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.SensorTypeMapper;
import com.hiekn.bridgehealth.service.ISensorTypeService;

@Service("sensorTypeService")
public class SensorTypeService extends SqlSessionDaoSupport implements ISensorTypeService {
 
	public SearchResult<SensorType> getSensorTypeList(Integer index, Integer pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		List<SensorType> list = null;
		int count =  -1;
		try {
			list = getSqlSession().selectList(SensorTypeMapper.class.getName()+".findByPage",map);
			count =  getSqlSession().selectOne(SensorTypeMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		SearchResult<SensorType> sResult = new SearchResult<SensorType>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public SensorType insertSensorType(SensorType senType) throws Exception {
		try {
			int count = getSqlSession().insert(SensorTypeMapper.class.getName() + ".insert",senType);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return senType;
	}

	@Override
	public void updateSensorType(SensorType senType) throws Exception {
		try {
			int count = getSqlSession().update(SensorTypeMapper.class.getName() + ".updateById",senType);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public SensorType findById(Integer id) throws Exception {
		SensorType senType = null;
		try {
			senType = getSqlSession().selectOne(SensorTypeMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return senType;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(SensorTypeMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	   
	    return count;
	}

	@Override
	public List<SensorType> findByIds( Integer [] ids) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(SensorTypeMapper.class.getName()+ ".findByIds",ids);
	}
}
