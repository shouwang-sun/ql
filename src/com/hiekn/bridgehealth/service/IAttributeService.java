package com.hiekn.bridgehealth.service;

import java.util.List;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IAttributeService {
	public SearchResult<Attribute> getAttributeList(Integer index,Integer pageSiz,Integer bridgeId)throws Exception;
	
	public Attribute insertAttribute (Attribute attribute)throws Exception;
	
	public int deleteByNameList(String [] nameList);
	
	public int deleteByIds(int [] ids);
	
	public int deleteByBridgeIds(int [] bridgeIds);
	
	public void updateAttributeById(Attribute attribute)throws Exception;
	
	public void updateAttributeByName(Attribute attribute)throws Exception;
}
