package com.hiekn.bridgehealth.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.LogicGroupResult;
import com.hiekn.bridgehealth.bean.StructureWarningResult;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IStructureWarningResultService {
	
	public SearchResult<StructureWarningResult> getStructureWarningResultList (Integer index,Integer pageSize,Date createTime,Date endTime,StructureWarningResult structureWarningResult);
	
	public StructureWarningResult insertStructureWarningResult (StructureWarningResult structureWarningResult)throws Exception;
	
	public void insertArrayStructureWarningResult (List<StructureWarningResult> structureWarningResultList)throws Exception;
	
	public void updateStructureWarningResultById(StructureWarningResult structureWarningResult)throws Exception;

	public List<Integer>findDistinctBridge();
	
	public StructureWarningResult findById(Integer id);
	
	public int deleteByBridgeIds(int [] bridgeId);
	
	public int deleteByStructureIds(int [] structureIds);
	
//	public List<StructureWarningResult>findByPageAndOtherParams(Integer index,Integer pageSize,Date createTime,Date endTime,Integer bridgeId);
}
