package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.LogicGroupResult;

public interface LogicGroupResultMapper {
	List<LogicGroupResult> findByPage (Integer index,Integer pageSize,Integer logicGroupId,Integer logicGroupOutputId);
}
