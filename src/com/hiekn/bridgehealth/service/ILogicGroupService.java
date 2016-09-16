package com.hiekn.bridgehealth.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.LogicGroupConstant;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.LogicGroupOutput;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.search.SearchResult;

@Service("logicGroupService") 
public interface ILogicGroupService {
	public SearchResult<LogicGroup> getLogicGroupList(Integer index,Integer pageSize,Integer bridgeId,Date startTime,Date endTime)throws Exception;

	public LogicGroup insertLogicGroup(LogicGroup logicGroup)throws Exception;
	
	public void updateLogicGroup(LogicGroup logicGroup)throws Exception;
	
	public void updateNextRunDateById(Integer id,Long lastUpdateTime)throws Exception;
	
	public LogicGroup findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids)throws Exception;
	
	public String findByName(Integer id);
	
}
