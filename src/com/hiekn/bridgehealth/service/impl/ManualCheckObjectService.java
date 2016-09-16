package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.ManualCheckObject;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.ManualCheckObjectMapper;
import com.hiekn.bridgehealth.service.IManualCheckObjectService;

@Service("manualCheckObjectService")
public class ManualCheckObjectService extends SqlSessionDaoSupport implements IManualCheckObjectService{
	
	@Override
	public SearchResult<ManualCheckObject> getManualCheckObjectList(Integer index,Integer pageSize) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		List<ManualCheckObject> list = null;
		List<ManualCheckObject> list_ = null;
		try {
			list = getSqlSession().selectList(ManualCheckObjectMapper.class.getName() + ".findByPage",map);
			list_ = getSqlSession().selectList(ManualCheckObjectMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		SearchResult<ManualCheckObject> searchResult = new SearchResult<ManualCheckObject>();
		searchResult.setRsList(list);
		searchResult.setRsCount(Long.valueOf(list_.size()));
		return searchResult;
	}

	@Override
	public ManualCheckObject insertManualCheckObject(ManualCheckObject manualCheckObject) throws Exception {
		try {
			int count = getSqlSession().insert(ManualCheckObjectMapper.class.getName() + ".insert",manualCheckObject);
		} catch (Exception e) {
			System.err.println(e);
		}
		return manualCheckObject;
	}

	@Override
	public void updateManualCheckObjectById(ManualCheckObject manualCheckObject) throws Exception {
		try {
			getSqlSession().update(ManualCheckObjectMapper.class.getName() + ".updateById",manualCheckObject);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@Override
	public void updateManualCheckObjectByName(ManualCheckObject manualCheckObject) throws Exception {
		try {
			getSqlSession().update(ManualCheckObjectMapper.class.getName() + ".updateByName",manualCheckObject);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public int deleteByNameList(String[] nameList) {
		int count = -1;
		try {
			count =  getSqlSession().delete(ManualCheckObjectMapper.class.getName() + ".deleteByName",nameList);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(ManualCheckObjectMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
}
