package com.hiekn.bridgehealth.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.EvaluateProjectResult;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IEvaluateProjectResultService {
	
	public SearchResult<EvaluateProjectResult> getEvaluateProjectResultList(Integer integer,Integer pageSixe, Date startTime,Date endTime,EvaluateProjectResult evaluateProjectResult) throws Exception; 

	public EvaluateProjectResult insertEvaluateProjectResult(EvaluateProjectResult evaluateProjectResult)throws Exception;
	
	public void updateEvaluateProjectResult(EvaluateProjectResult evaluateProjectResult)throws Exception;
	 
	public EvaluateProjectResult findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids);

	public int deleteByBridgeIds(int [] ids);
	
	public int deleteByEvaluateProjectIds(int [] ids);
	
	public boolean judgeItemExist(EvaluateProjectResult evaluateProjectResult);
	
	public void updateByEvaluateProjectId(EvaluateProjectResult evaluateProjectResult);
	
	public void updateByBridgeEvaluateProjectId(EvaluateProjectResult evaluateProjectResult);
	
	public List<Integer> findAllYear();
	
	public List<Integer> findAllMonth();
	
	public List<EvaluateProjectResult> findByEVAPid(Integer id);
}
