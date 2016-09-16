package com.hiekn.bridgehealth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.search.SearchResult;
public interface ILogicGroupOutputService {

	public SearchResult<LogicGroupOutput> getLogicGroupOutputList(Integer index,Integer pageSize,Integer logicGroupId)throws Exception;

	public LogicGroupOutput insertlogicGroupOut(LogicGroupOutput output)throws Exception;
	
	public LogicGroupOutput findById(Integer id)throws Exception;

	public void updateLogicGroupOutput(LogicGroupOutput logicGroupOutput)throws Exception;

	public int deleteByIds(int [] ids);
	
	public int deleteByLogicGroupIds(int [] ids);
	
	public List<LogicGroupOutput> getLogicGroupOutputList(Integer logicGroupId);
	
	public void updateThresholdById(Integer id,String threshold)throws Exception;
	
}
