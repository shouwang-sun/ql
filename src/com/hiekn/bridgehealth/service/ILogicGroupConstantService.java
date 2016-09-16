package com.hiekn.bridgehealth.service;


import java.util.List;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface ILogicGroupConstantService {
	
	public LogicGroupConstant insertLogicGroupConstant(LogicGroupConstant logicGroupConstant)throws Exception;

	public SearchResult<LogicGroupConstant> getLogicGroupConstantList(Integer index,Integer pageSize,Integer logicGroupId)throws Exception;
	
	public void updateLogicGroupConstant(LogicGroupConstant logicGroupConstant)throws Exception;
	 
	public LogicGroupConstant findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids);
	
	public int deleteByLogicGroupIds(int [] ids);
	
	public List<LogicGroupConstant> getLogicGroupConstantByLogicGroupId(Integer logicGroupId);
}
