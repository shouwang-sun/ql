package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.ManualCheckField;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.ManualCheckFieldMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IManualCheckFieldService;

@Service("manualCheckFieldService")
public class ManualCheckFieldService extends SqlSessionDaoSupport implements IManualCheckFieldService{
	
	@Override
	public SearchResult<ManualCheckField> getManualCheckFieldList(Integer index,Integer pageSize,Integer manualCheckDataId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("manualCheckDataId", manualCheckDataId);
		List<ManualCheckField> list = null;
		List<ManualCheckField> list_ = null;
		try {
			list = getSqlSession().selectList(ManualCheckFieldMapper.class.getName() + ".findByPage",map);
			list_ = getSqlSession().selectList(ManualCheckFieldMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		SearchResult<ManualCheckField> searchResult = new SearchResult<ManualCheckField>();
		searchResult.setRsList(list);
		searchResult.setRsCount(Long.valueOf(list_.size()));
		return searchResult;
	}

	@Override
	public ManualCheckField insertManualCheckField(ManualCheckField manualCheckField) throws Exception {
		try {
			int count = getSqlSession().insert(ManualCheckFieldMapper.class.getName() + ".insert",manualCheckField);
		} catch (Exception e) {
			System.err.println(e);
		}
		return manualCheckField;
	}

	@Override
	public void updateManualCheckFieldById(ManualCheckField manualCheckField) throws Exception {
		try {
			getSqlSession().update(ManualCheckFieldMapper.class.getName() + ".updateById",manualCheckField);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@Override
	public void updateManualCheckFieldByName(ManualCheckField manualCheckField) throws Exception {
		try {
			getSqlSession().update(ManualCheckFieldMapper.class.getName() + ".updateByName",manualCheckField);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public int deleteByNameList(String[] nameList) {
		int count = -1;
		try {
			count =  getSqlSession().delete(ManualCheckFieldMapper.class.getName() + ".deleteByName",nameList);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(ManualCheckFieldMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteByManualCheckDataIds(int [] manualCheckDataIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(ManualCheckFieldMapper.class.getName() + ".deleteByManualCheckDataIds",manualCheckDataIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
}
