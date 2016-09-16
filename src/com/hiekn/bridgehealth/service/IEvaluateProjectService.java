package com.hiekn.bridgehealth.service;


import java.util.Date;
import java.util.List;

import com.hiekn.bridgehealth.bean.EvaluateProject;
import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IEvaluateProjectService {
	public SearchResult<EvaluateProject> getEvaluateProjectList(Integer index,Integer pageSize,Date startTime,Date endTime,EvaluateProject evaluateProject)throws Exception;
	
	public EvaluateProject insertEvaluateProject(EvaluateProject evaluateProject)throws Exception;
	
	public void updateEvaluateProject(EvaluateProject evaluateProject)throws Exception;
	
	public void updateByIdByHealthyRate(EvaluateProject evaluateProject)throws Exception;
	
	public int deleteByIds(int [] ids)throws Exception;
	
	public int deleteByBridgeIds(int [] ids);
	
	public EvaluateProject findById(Integer id)throws Exception;
	
	public String findByIdByName(Integer id)throws Exception;
	
	public List<EvaluateProject> findByPid(Integer id);
	
	public boolean judgeItemExist(EvaluateProject evaluateProject);
}
