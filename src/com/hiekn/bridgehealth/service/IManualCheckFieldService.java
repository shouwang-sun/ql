package com.hiekn.bridgehealth.service;

import com.hiekn.bridgehealth.bean.ManualCheckField;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IManualCheckFieldService {
 
public SearchResult<ManualCheckField> getManualCheckFieldList(Integer index,Integer pageSiz,Integer manualCheckDataId)throws Exception;
	
	public ManualCheckField insertManualCheckField (ManualCheckField manualCheckField)throws Exception;
	
	public int deleteByNameList(String [] nameList);
	
	public int deleteByIds(int [] ids);
	
	public int deleteByManualCheckDataIds(int [] manualCheckDataIds);
	
	public void updateManualCheckFieldById(ManualCheckField manualCheckField)throws Exception;
	
	public void updateManualCheckFieldByName(ManualCheckField manualCheckField)throws Exception;
}
