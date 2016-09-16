package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.LogicGroupOutputMapper;
import com.hiekn.bridgehealth.dao.LogicGroupResultMapper;
import com.hiekn.bridgehealth.service.ILogicGroupResultService;
@Service("logicGroupResultService")
public class LogicGroupResultService extends SqlSessionDaoSupport implements ILogicGroupResultService{

	@Override
	public SearchResult<LogicGroupResult> getLogicResultList(Integer index, Integer pageSize,Long createTime,Long endTime,LogicGroupResult logicGroupResult,Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("startTime", createTime);
		map.put("endTime", endTime);
		map.put("flag", flag);
//		map.put("bridgeId", logicGroupResult.getBridgeId());
		map.put("logicGroupId", logicGroupResult.getLogicGroupId());
		map.put("logicGroupOutputId", logicGroupResult.getLogicGroupOutputId());
		
		List<LogicGroupResult> logicGroupResultList = getSqlSession().selectList(LogicGroupResultMapper.class.getName() +".findByPage",map);
		
		int count = getSqlSession().selectOne(LogicGroupResultMapper.class.getName() +".findByItemNum",map);
		
		SearchResult<LogicGroupResult> sResult = new SearchResult<LogicGroupResult>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(logicGroupResultList);
		return sResult;
	}

	@Override
	public void insertArray(List<LogicGroupResult> logicGroupResults) {
		int count =  getSqlSession().insert(LogicGroupResultMapper.class.getName() +".insertArray",logicGroupResults);
	}

	@Override
	public Double getAvgValue(Date createTime, Date endTime,LogicGroupResult logicGroupResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", createTime);
		map.put("endTime", endTime);
		map.put("bridgeId", logicGroupResult.getBridgeId());
		map.put("logicGroupOutputId", logicGroupResult.getLogicGroupOutputId());
		return getSqlSession().selectOne(LogicGroupResultMapper.class.getName() +".getAvgValue",map);
	}

	@Override
	public void updateLogicGroupResult(LogicGroupResult logicGroupResult)throws Exception {
		try {
			int count = getSqlSession().update(LogicGroupResultMapper.class.getName() + ".updateById",logicGroupResult);
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
