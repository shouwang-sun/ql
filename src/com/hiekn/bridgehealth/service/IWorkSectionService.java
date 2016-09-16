package com.hiekn.bridgehealth.service;

import java.util.List;
import java.util.Map;
import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.WorkSection;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IWorkSectionService {
	
	public SearchResult<WorkSection> getWorkSectionList(Integer index,Integer pageSize,Integer bridgeId,Integer pid)throws Exception;
	
	public WorkSection insertWorkSection(WorkSection workSection)throws Exception;
	
	public void updateWorkSection(WorkSection workSection)throws Exception;
	 
	public WorkSection findById(Integer id)throws Exception;
	 
	public int deleteByIds (int [] ids) throws Exception;
	
	public int deleteByBridgeIds(int [] bridgeId);
	
	public WorkSection findByPageAndSensorType(Integer id);
}
