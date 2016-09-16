package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.EvaluateProjectResultMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IEvaluateProjectResultService;

@Service("evaluateProjectResultService")
public class EvaluateProjectResultService extends SqlSessionDaoSupport implements IEvaluateProjectResultService{

	@Override
	public SearchResult<EvaluateProjectResult> getEvaluateProjectResultList(Integer index, Integer pageSixe,Date startTime, Date endTime,EvaluateProjectResult evaluateProjectResult) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", Integer.valueOf(index));
		map.put("pageSize", Integer.valueOf(pageSixe));
		map.put("startTime", startTime);
		map.put("endTime",endTime);
		map.put("evaluateProjectId", evaluateProjectResult.getEvaluateProjectId());
		map.put("evaluateProjectPid", evaluateProjectResult.getEvaluateProjectPid());
		map.put("bridgeId", evaluateProjectResult.getBridgeId());
		map.put("projectYear", evaluateProjectResult.getProjectYear());
		map.put("projectMonth", evaluateProjectResult.getProjectMonth());
		List<EvaluateProjectResult> list  = null;
		int count = -1;
		try{
			list = getSqlSession().selectList(EvaluateProjectResultMapper.class.getName() + ".findByPage",map);
			count =  getSqlSession().selectOne(EvaluateProjectResultMapper.class.getName() + ".findByItemNum",map);
		}catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<EvaluateProjectResult> sResult = new SearchResult<EvaluateProjectResult>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public EvaluateProjectResult insertEvaluateProjectResult(EvaluateProjectResult evaluateProjectResult) throws Exception {
		try {
			int count = getSqlSession().insert(EvaluateProjectResultMapper.class.getName() + ".insert",evaluateProjectResult);
		} catch (Exception e) {
			System.err.println(e);
		}
		return evaluateProjectResult;
	}

	@Override
	public void updateEvaluateProjectResult(EvaluateProjectResult evaluateProjectResult) throws Exception {
		try {
			int count = getSqlSession().update(EvaluateProjectResultMapper.class.getName() + ".updateById",evaluateProjectResult);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public EvaluateProjectResult findById(Integer id) throws Exception {
		EvaluateProjectResult evaluateProjectResult = null;
		try {
			evaluateProjectResult = getSqlSession().selectOne(EvaluateProjectResultMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return evaluateProjectResult;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(EvaluateProjectResultMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	   
	    return count;
	}

	@Override
	public boolean judgeItemExist(EvaluateProjectResult evaluateProjectResult) {
		List<EvaluateProjectResult> list = getSqlSession().selectList(EvaluateProjectResultMapper.class.getName() + ".judgeItemExist",evaluateProjectResult);
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void updateByEvaluateProjectId(EvaluateProjectResult evaluateProjectResult) {
		try {
			int count = getSqlSession().update(EvaluateProjectResultMapper.class.getName() + ".updateByEvaluateProjectId",evaluateProjectResult);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public void updateByBridgeEvaluateProjectId(EvaluateProjectResult evaluateProjectResult) {
		try {
			int count = getSqlSession().update(EvaluateProjectResultMapper.class.getName() + ".updateByBridgeEvaluateProjectId",evaluateProjectResult);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public List<Integer> findAllYear() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(EvaluateProjectResultMapper.class.getName() + ".findAllYear");
	}

	@Override
	public List<Integer> findAllMonth() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(EvaluateProjectResultMapper.class.getName() + ".findAllMonth");
	}

	@Override
	public List<EvaluateProjectResult> findByEVAPid(Integer evaluateProjectPid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("evaluateProjectPid", evaluateProjectPid);
		return getSqlSession().selectList(EvaluateProjectResultMapper.class.getName() + ".findByEVAPid",evaluateProjectPid);
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(EvaluateProjectResultMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

	@Override
	public int deleteByEvaluateProjectIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(EvaluateProjectResultMapper.class.getName() + ".deleteByEvaluateProjectIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
}
