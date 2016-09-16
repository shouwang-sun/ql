package com.hiekn.bridgehealth.service;

import java.util.Date;
import java.util.List;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface ILogicGroupResultService{
	
	public SearchResult<LogicGroupResult> getLogicResultList(Integer index,Integer pageSize,Long createTime,Long endTime,LogicGroupResult logicGroupResult,Integer flag);
	
	public void insertArray(List<LogicGroupResult> logicGroupResults);
	
	public int deleteByLogicGroupIds(int [] ids);
	
	public Double getAvgValue(Date createTime,Date endTime,LogicGroupResult logicGroupResult);
	
	public void updateLogicGroupResult(LogicGroupResult logicGroupResult)throws Exception;
	
}
