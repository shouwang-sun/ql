package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.ManualCheckDataMapper;
import com.hiekn.bridgehealth.dao.SensorChannelMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.SensorTypeMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IWorkSectionService;

@Service("workSectionService")
public class WorkSectionService extends SqlSessionDaoSupport implements IWorkSectionService {
 
	@Override
	public SearchResult<WorkSection> getWorkSectionList(Integer index,Integer pageSize, Integer bridgeId,Integer pid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("bridgeId", bridgeId);
		map.put("pid", pid);
		List<WorkSection> list = null;
		int count =  -1;
		try {
			 list = getSqlSession().selectList(WorkSectionMapper.class.getName()+".findByPage",map);
			 count =  getSqlSession().selectOne(WorkSectionMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		SearchResult<WorkSection> sResult = new SearchResult<WorkSection>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public WorkSection insertWorkSection(WorkSection workSection)throws Exception {
		try {
			int count = getSqlSession().insert(WorkSectionMapper.class.getName() + ".insert",workSection);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return workSection;
	}

	@Override
	public void updateWorkSection(WorkSection workSection) throws Exception {
		try {
			int count = getSqlSession().update(WorkSectionMapper.class.getName() + ".updateById",workSection);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public WorkSection findById(Integer id) throws Exception {
		WorkSection workSection = null;
		try {
			workSection = getSqlSession().selectOne(WorkSectionMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return workSection;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int  deleteByIds(int [] ids)throws Exception {
		int count = -1;
		count =  getSqlSession().delete(WorkSectionMapper.class.getName() + ".deleteByIds",ids);
		count =  getSqlSession().delete(SensorMapper.class.getName() + ".deleteByWorkSectionIds",ids);
		count =  getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteByWorkSectionIds",ids);
	    return count;
	}

	@Override
	public WorkSection findByPageAndSensorType(Integer id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(WorkSectionMapper.class.getName()+".findByPageAndSensorType",id);
	}

	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(WorkSectionMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
}
