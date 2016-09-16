package com.hiekn.bridgehealth.service;

import java.util.List;

import com.hiekn.bridgehealth.bean.EvaluateReportTemplet;

public interface IEvaluateReportTempletService {
	/**
	 * 保存评估报告模板
	 * @param evaluateReportTemplet
	 * @return
	 */
	public Integer saveEvaluateReportTemplet(EvaluateReportTemplet evaluateReportTemplet)throws Exception;
	 
	public List<EvaluateReportTemplet> findByType(int type)throws Exception;
	 /**
	  * 通过bridgeId 查找模板
	  * @param id
	  * @return
	  */
	public EvaluateReportTemplet findByBridge(Integer id)throws Exception;
	 
	public void deleteEvaluateReportTemplet(Integer id)throws Exception;

}

