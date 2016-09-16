package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.StructureWarningMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IStructureWarningService;

@Service("structureWarningService")
public class StructureWarningService extends SqlSessionDaoSupport implements IStructureWarningService{

	@Override
	public SearchResult<StructureWarning> getStructureWarningList(Integer index,Integer pageSize,Integer bridgeId,Date startTime,Date endTime) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("bridgeId", bridgeId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<StructureWarning> list = null;
		int count = -1;
		try {
			list = getSqlSession().selectList(StructureWarningMapper.class.getName() + ".findByPage",map);
			count = getSqlSession().selectOne(StructureWarningMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		SearchResult<StructureWarning> searchResult = new SearchResult<StructureWarning>();
		searchResult.setRsList(list);
		searchResult.setRsCount(Long.valueOf(count));
		return searchResult;
	}

	@Override
	public StructureWarning insertStructureWarning(StructureWarning structureWarning) throws Exception {
		try {
			int count = getSqlSession().insert(StructureWarningMapper.class.getName() + ".insert",structureWarning);
		} catch (Exception e) {
			System.err.println(e);
		}
		return structureWarning;
	}

	@Override
	public void updateStructureWarningByName(StructureWarning structureWarning) throws Exception {
		try {
			getSqlSession().update(StructureWarningMapper.class.getName() + ".updateByName",structureWarning);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public int deleteByNameList(String[] nameList) {
		int count = -1;
		try {
			count =  getSqlSession().delete(StructureWarningMapper.class.getName() + ".deleteByName",nameList);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteByIds (int [] ids) {
		int count = -1;
		count =  getSqlSession().delete(StructureWarningMapper.class.getName() + ".deleteByIds",ids);
		return count;
	}

	@Override
	public void updateStructureWarningById(StructureWarning structureWarning)throws Exception {
		try {
			getSqlSession().update(StructureWarningMapper.class.getName() + ".updateById",structureWarning);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(StructureWarningMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
}
