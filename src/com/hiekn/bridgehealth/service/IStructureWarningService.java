package com.hiekn.bridgehealth.service;


import java.util.Date;

import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IStructureWarningService {
	
	public SearchResult<StructureWarning> getStructureWarningList(Integer index,Integer pageSize,Integer bridgeId,Date startTime,Date endTime)throws Exception;

	public StructureWarning insertStructureWarning (StructureWarning structureWarning)throws Exception;
	
	public int deleteByNameList(String [] nameList);
	
	public int deleteByIds(int [] ids)throws Exception;
	
	public int deleteByBridgeIds(int [] bridgeId);
	
	public void updateStructureWarningById(StructureWarning structureWarning)throws Exception;
	
	public void updateStructureWarningByName(StructureWarning structureWarning)throws Exception;
	
}
