package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.LogicGroupConstantMapper;
import com.hiekn.bridgehealth.dao.LogicGroupMapper;
import com.hiekn.bridgehealth.dao.LogicGroupOutputMapper;
import com.hiekn.bridgehealth.dao.LogicGroupResultMapper;
import com.hiekn.bridgehealth.dao.SensorChannelMapper;
import com.hiekn.bridgehealth.service.ILogicGroupService;
@Service("logicGroupService")
public class LogicGroupService extends SqlSessionDaoSupport implements ILogicGroupService{
 
	public SearchResult<LogicGroup> getLogicGroupList(Integer index,Integer pageSize,Integer bridgeId,Date startTime,Date endTime)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("bridgeId", bridgeId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<LogicGroup> list = null;
		int count = -1;
		try {
			list = getSqlSession().selectList(LogicGroupMapper.class.getName()+".findByPage",map);
			count =  getSqlSession().selectOne(LogicGroupMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<LogicGroup> sResult = new SearchResult<LogicGroup>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public LogicGroup insertLogicGroup(LogicGroup logicGroup) throws Exception {
		try {
			int count = getSqlSession().insert(LogicGroupMapper.class.getName() + ".insert",logicGroup);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return logicGroup;
	}

	@Override
	public void updateLogicGroup(LogicGroup logicGroup) throws Exception {
		try {
			int count = getSqlSession().update(LogicGroupMapper.class.getName() + ".updateById",logicGroup);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public LogicGroup findById(Integer id) throws Exception {
		LogicGroup logicGroup = null;
		try {
			logicGroup = getSqlSession().selectOne(LogicGroupMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return logicGroup;
	}

	@Override
	public int deleteByIds(int[] ids) throws Exception {
		int count = -1;
			count =  getSqlSession().delete(LogicGroupMapper.class.getName() + ".deleteByIds",ids);
			count =  getSqlSession().delete(LogicGroupConstantMapper.class.getName() + ".deleteByLogicGroupIds",ids);
			count =  getSqlSession().delete(LogicGroupOutputMapper.class.getName() + ".deleteByLogicGroupIds",ids);
			count =  getSqlSession().delete(LogicGroupResultMapper.class.getName() + ".deleteByLogicGroupIds",ids);
	    return count;
	}

	@Override
	public String findByName(Integer id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(LogicGroupMapper.class.getName() + ".findByName",id);
	}

	@Override
	public void updateNextRunDateById(Integer id, Long nextRunTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("nextRunTime", nextRunTime);
		try {
			int count = getSqlSession().update(LogicGroupMapper.class.getName() + ".updateNextRunDateById",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
	}
 
}
