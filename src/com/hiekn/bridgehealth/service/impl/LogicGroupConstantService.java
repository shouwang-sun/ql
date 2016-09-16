package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.LogicGroupConstantMapper;
import com.hiekn.bridgehealth.dao.LogicGroupOutputMapper;
import com.hiekn.bridgehealth.service.ILogicGroupConstantService;
@Service("logicGroupConstantService")
public class LogicGroupConstantService extends SqlSessionDaoSupport implements ILogicGroupConstantService{

	@Override
	public LogicGroupConstant insertLogicGroupConstant(LogicGroupConstant logicGroupConstant) throws Exception {
		try {
			int count = getSqlSession().insert(LogicGroupConstantMapper.class.getName() + ".insert",logicGroupConstant);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return logicGroupConstant;
	}

	@Override
	public SearchResult<LogicGroupConstant> getLogicGroupConstantList(Integer index, Integer pageSize,Integer logicGroupId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		System.err.println(index);
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("logicGroupId", logicGroupId);
		List<LogicGroupConstant> list  = null;
		int count = -1;
		try{
			list = getSqlSession().selectList(LogicGroupConstantMapper.class.getName() + ".findByPage",map);
			count =  getSqlSession().selectOne(LogicGroupConstantMapper.class.getName() + ".findByItemNum",map);
		}catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<LogicGroupConstant> sResult = new SearchResult<LogicGroupConstant>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public void updateLogicGroupConstant(LogicGroupConstant logicGroupConstant)throws Exception {
		try {
			int count = getSqlSession().update(LogicGroupConstantMapper.class.getName() + ".updateById",logicGroupConstant);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public LogicGroupConstant findById(Integer id) throws Exception {
		LogicGroupConstant logicGroupConstant = null;
		try {
			logicGroupConstant = getSqlSession().selectOne(LogicGroupConstantMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return logicGroupConstant;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(LogicGroupConstantMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	    return count;
	}

	@Override
	public List<LogicGroupConstant> getLogicGroupConstantByLogicGroupId(Integer logicGroupId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(LogicGroupConstantMapper.class.getName() + ".findByLogicGroupId",logicGroupId);
	}
	
	@Override
	public int deleteByLogicGroupIds(int [] logicGroupIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(LogicGroupConstantMapper.class.getName() + ".deleteByLogicGroupIds",logicGroupIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

}
