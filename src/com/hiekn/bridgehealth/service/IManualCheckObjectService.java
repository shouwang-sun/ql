package com.hiekn.bridgehealth.service;

import com.hiekn.bridgehealth.bean.ManualCheckObject;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IManualCheckObjectService {
 
   public SearchResult<ManualCheckObject> getManualCheckObjectList(Integer index,Integer pageSiz)throws Exception;
	
	public ManualCheckObject insertManualCheckObject (ManualCheckObject manualCheckObject)throws Exception;
	
	public int deleteByNameList(String [] nameList);
	
	public int deleteByIds(int [] ids);
	
	public void updateManualCheckObjectById(ManualCheckObject manualCheckObject)throws Exception;
	
	public void updateManualCheckObjectByName(ManualCheckObject manualCheckObject)throws Exception;
}
