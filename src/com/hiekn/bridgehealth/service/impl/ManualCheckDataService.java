package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.ManualCheckField;
import com.hiekn.bridgehealth.bean.ManualCheckData;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.ManualCheckDataMapper;
import com.hiekn.bridgehealth.dao.ManualCheckFieldMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.service.IManualCheckDataService;

@Service("manualCheckDataService")
public class ManualCheckDataService extends SqlSessionDaoSupport implements IManualCheckDataService{
	@Override
	public SearchResult<ManualCheckData> getManualCheckDataList(Integer index,Integer pageSize, Date createTime, Date endTime,Integer bridgeId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("createTime", createTime);
		map.put("endTime", endTime);
		map.put("bridgeId",bridgeId);
		List<ManualCheckData> list = null;
		List<ManualCheckData> list_ = null;
		try {
			list = getSqlSession().selectList(ManualCheckDataMapper.class.getName()+".findByPage",map);
			list_ =  getSqlSession().selectList(ManualCheckDataMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<ManualCheckData> sResult = new SearchResult<ManualCheckData>();
		sResult.setRsCount(Long.valueOf(list_.size()));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public ManualCheckData insertManualCheckData(ManualCheckData manualCheckData) {
		try {
			int count = getSqlSession().insert(ManualCheckDataMapper.class.getName() + ".insert",manualCheckData);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return manualCheckData;
	}
 

	@Override
	public void updateManualCheckDataById(ManualCheckData ManualCheckData)throws Exception {
		try {
			int count = getSqlSession().update(ManualCheckDataMapper.class.getName() + ".updateById",ManualCheckData);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public int deleteByIds(int[] ids) throws Exception {
		int count = -1;
			count =  getSqlSession().delete(ManualCheckDataMapper.class.getName() + ".deleteByIds",ids);
			count =  getSqlSession().delete(ManualCheckFieldMapper.class.getName() + ".deleteByManualCheckDataIds",ids);
	    return count;
	}

	@Override
	public ManualCheckData findById(Integer id) {
		ManualCheckData manualCheckData = null;
		try {
			manualCheckData = getSqlSession().selectOne(ManualCheckDataMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return manualCheckData;
	}

}
