package com.hiekn.bridgehealth.service;

import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.search.SearchResult;
/**
 * @author govert.peng
 * @since 2014/12/16
 */
public interface IBridgeService {
	
	
	public SearchResult<Bridge> getBridgeList(Integer index,Integer pageSize)throws Exception;
	
	public Bridge insertBridge(Bridge bridge)throws Exception;
	
	public void updateBridge(Bridge bridge)throws Exception;
	 
	public Bridge findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids) throws Exception;
	
	public Bridge findByPageAndWorkSection(Integer id);
}
