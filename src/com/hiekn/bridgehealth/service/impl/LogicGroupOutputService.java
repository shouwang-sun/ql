package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.LogicGroupMapper;
import com.hiekn.bridgehealth.dao.LogicGroupOutputMapper;
import com.hiekn.bridgehealth.dao.SensorTypeMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.ILogicGroupOutputService;
@Service("logicGroupOutputService")
public class LogicGroupOutputService extends SqlSessionDaoSupport implements ILogicGroupOutputService{
 
	@Override
	public SearchResult<LogicGroupOutput> getLogicGroupOutputList(Integer index, Integer pageSize, Integer logicGroupId)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("logicGroupId", logicGroupId);
		List<LogicGroupOutput> list = null;
		int count = -1;
		try {
		     list = getSqlSession().selectList(LogicGroupOutputMapper.class.getName()+".findByPage",map);
			 count =  getSqlSession().selectOne(LogicGroupOutputMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			  //   System.out.println(e);
		}
		SearchResult<LogicGroupOutput> sResult = new SearchResult<LogicGroupOutput>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public LogicGroupOutput insertlogicGroupOut(LogicGroupOutput output)throws Exception {
		try {
			int count = getSqlSession().insert(LogicGroupOutputMapper.class.getName() + ".insert",output);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return output;
	}

	@Override
	public LogicGroupOutput findById(Integer id) throws Exception {
		LogicGroupOutput logicGroupOutput = null;
		try {
			logicGroupOutput  = getSqlSession().selectOne(LogicGroupOutputMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return logicGroupOutput;
	}

	@Override
	public void updateLogicGroupOutput(LogicGroupOutput logicGroupOutput)throws Exception {
		try {
			int count = getSqlSession().update(LogicGroupOutputMapper.class.getName() + ".updateById",logicGroupOutput);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(LogicGroupOutputMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	    return count;
	}

	@Override
	public List<LogicGroupOutput> getLogicGroupOutputList(Integer logicGroupId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(LogicGroupOutputMapper.class.getName()+".findByLogicGroupId",logicGroupId);
	}

	@Override
	public void updateThresholdById(Integer id, String threshold)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("threshold", threshold);
		try {
			int count = getSqlSession().update(LogicGroupOutputMapper.class.getName() + ".updateThresholdById",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
	}
	
	@Override
	public int deleteByLogicGroupIds(int [] logicGroupIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(LogicGroupOutputMapper.class.getName() + ".deleteByLogicGroupIds",logicGroupIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

}
