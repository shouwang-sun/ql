package com.hiekn.bridgehealth.service;

import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.EvaluateReport;
import com.hiekn.bridgehealth.bean.StructureWarningResult;

public interface IEvaluateReportService {
	public Integer saveEvaluateReport(EvaluateReport evaluateReport)throws Exception;

	public EvaluateReport findById(Integer id)throws Exception;

	public List<EvaluateReport> findYearByBridge(Integer id)throws Exception;

	public List<EvaluateReport> findMonthByBidAndYear(Integer id, Integer year)throws Exception;
	
	public List<StructureWarningResult> findAllWord1(Integer bid,Integer year ,Integer month) throws Exception;
	
	public Map findAllWord2(Integer[] cids, Integer[] type, Integer year, Integer month)throws Exception;
	
	public Map findAllTable1(Integer ids[],Integer eid) throws Exception;

	public boolean hasLastMonthReport(int year, int month, Integer bid)throws Exception;
}
