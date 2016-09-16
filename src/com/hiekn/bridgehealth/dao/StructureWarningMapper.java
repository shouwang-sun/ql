package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.StructureWarning;

public interface StructureWarningMapper {
	List<StructureWarning> findByPage(Integer index,Integer pageSize,Integer bridgeId);
}
