package com.hiekn.bridgehealth.dao;

import java.util.Date;
import java.util.List;

import com.hiekn.bridgehealth.bean.StructureWarningResult;

public interface StructureWarningResultMapper {
	List<StructureWarningResult> findByPage(Integer index,Integer pageSize,Date createTime, Date endTime, Integer bridgeId, Integer structureWarningId);
	
	int findByItemNum(Date createTime, Date endTime, Integer bridgeId, Integer structureWarningId);
}
