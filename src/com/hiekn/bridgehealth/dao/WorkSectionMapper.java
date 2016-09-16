package com.hiekn.bridgehealth.dao;

import java.util.List;

import com.hiekn.bridgehealth.bean.WorkSection;

public interface WorkSectionMapper {
	List<WorkSection> findByBridge(Integer bridgeId);
}
