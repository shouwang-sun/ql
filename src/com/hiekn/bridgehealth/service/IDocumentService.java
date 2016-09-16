package com.hiekn.bridgehealth.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.python.antlr.ast.Str;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.Document;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IDocumentService {
	
	public SearchResult<Document> getDocumentList(Integer index,Integer pageSize,Date startTime,Date endTime,Long startSize,Long endSize,String fileType)throws Exception;

	public Document insertDocument (Document document)throws Exception;
	
	public SearchResult<String> findDocumentTypeList();
	
	public int deleteByIdList(int [] idList);
}
