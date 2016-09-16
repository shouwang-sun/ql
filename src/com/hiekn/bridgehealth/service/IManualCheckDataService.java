package com.hiekn.bridgehealth.service;

import java.util.Date;
import java.util.List;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.ManualCheckData;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IManualCheckDataService {
	 public SearchResult<ManualCheckData> getManualCheckDataList(Integer index,Integer pageSize,Date createTime,Date endTime,Integer bridgeId)throws Exception;
	
	 public ManualCheckData insertManualCheckData(ManualCheckData manualCheckData);
	 
	 public ManualCheckData findById(Integer id);
	 
	 public void updateManualCheckDataById(ManualCheckData ManualCheckData)throws Exception;
	
	 public int deleteByIds(int [] ids) throws Exception;

}
