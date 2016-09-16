package com.hiekn.bridgehealth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.EvaluateProject;
import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.EvaluateProjectMapper;
import com.hiekn.bridgehealth.dao.EvaluateProjectResultMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IEvaluateProjectService;

@Service("evaluateProjectService")
public class EvaluateProjectService extends SqlSessionDaoSupport implements IEvaluateProjectService{

	@Override
	public SearchResult<EvaluateProject> getEvaluateProjectList(Integer index,Integer pageSize,Date startTime,Date endTime,EvaluateProject evaluateProject) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		System.err.println(index);
		map.put("index", Integer.valueOf(index));
		map.put("pageSize", Integer.valueOf(pageSize));
		map.put("pid", evaluateProject.getPid());
		map.put("bridgeId", evaluateProject.getBridgeId());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<EvaluateProject> list  = null;
		int count = -1;
		try{
			list = getSqlSession().selectList(EvaluateProjectMapper.class.getName() + ".findByPage",map);
			count =  getSqlSession().selectOne(EvaluateProjectMapper.class.getName() + ".findByItemNum",map);
		}catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<EvaluateProject> sResult = new SearchResult<EvaluateProject>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public EvaluateProject insertEvaluateProject(EvaluateProject evaluateProject)throws Exception {
		try {
			int count = getSqlSession().insert(EvaluateProjectMapper.class.getName() + ".insert",evaluateProject);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return evaluateProject;
	}

	@Override
	public void updateEvaluateProject(EvaluateProject evaluateProject)throws Exception {
		try {
			int count = getSqlSession().update(EvaluateProjectMapper.class.getName() + ".updateById",evaluateProject);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
	}
	
	@Override
	public int deleteByIds(int[] ids) throws Exception{
		int count = -1;
		count =  getSqlSession().delete(EvaluateProjectMapper.class.getName() + ".deleteByIds",ids);
	    count =  getSqlSession().delete(EvaluateProjectResultMapper.class.getName() + ".deleteByEvaluateProjectIds",ids);
		return count;
	}

	@Override
	public EvaluateProject findById(Integer id) throws Exception {
		EvaluateProject evaluateProject = null;
		try {
			evaluateProject = getSqlSession().selectOne(EvaluateProjectMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return evaluateProject;
	}

	@Override
	public String findByIdByName(Integer id) throws Exception {
		String evaluateProjectName = null;
		try {
			evaluateProjectName = getSqlSession().selectOne(EvaluateProjectMapper.class.getName() + ".findByIdByName",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return evaluateProjectName;
	}

	@Override
	public void updateByIdByHealthyRate(EvaluateProject evaluateProject)throws Exception {
		try {
			int count = getSqlSession().update(EvaluateProjectMapper.class.getName() + ".updateByIdByHealthyRate",evaluateProject);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
	}

	@Override
	public List<EvaluateProject> findByPid(Integer id) {
		EvaluateProject evaluateProject = null;
		List<EvaluateProject> evaluateProjectList = new ArrayList<EvaluateProject>();
		try {
			evaluateProjectList = getSqlSession().selectList(EvaluateProjectMapper.class.getName() + ".findByPid",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return evaluateProjectList;
	}
	
	@Override
	public boolean judgeItemExist(EvaluateProject evaluateProject) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bridgeId", evaluateProject.getBridgeId());
		map.put("pid", evaluateProject.getPid());
		List<EvaluateProject> list = getSqlSession().selectList(EvaluateProjectMapper.class.getName() + ".judgeItemExist",map);
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(EvaluateProjectMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
}
