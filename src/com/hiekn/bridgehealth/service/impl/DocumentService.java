package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import com.hiekn.bridgehealth.bean.Document;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.DocumentMapper;
import com.hiekn.bridgehealth.service.IDocumentService;

@Service("documentService")
public class DocumentService extends SqlSessionDaoSupport implements IDocumentService {
	@Override
	public SearchResult<Document> getDocumentList(Integer index,Integer pageSize,Date startTime,Date endTime,Long startSize,Long endSize,String fileType){ 
	Map<String,Object> map = new HashMap<String,Object>();
	map.put("index", index);
	map.put("pageSize", pageSize);
	map.put("fileType", fileType);
	map.put("startTime", startTime);
	map.put("endTime", endTime);
	map.put("startSize", startSize);
	map.put("endSize", endSize);
	
	List<Document> list  = null;
	int count = -1;
	try{
		list = getSqlSession().selectList(DocumentMapper.class.getName() + ".findByPage",map);
		count =  getSqlSession().selectOne(DocumentMapper.class.getName() + ".findByItemNum",map);
	}catch (Exception e) {
		 //   System.out.println(e);
	}
	
	SearchResult<Document> sResult = new SearchResult<Document>();
	sResult.setRsCount(Long.valueOf(count));
	sResult.setRsList(list);
	return sResult;
	}

	@Override
	public int deleteByIdList(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(DocumentMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	    return count;
	}

	@Override
	public Document insertDocument(Document document) throws Exception {
		try {
			int count = getSqlSession().insert(DocumentMapper.class.getName() + ".insert",document);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return document;
	}

	@Override
	public SearchResult<String> findDocumentTypeList() {
		// TODO Auto-generated method stub
		SearchResult<String> sResult = new SearchResult<String>();
		List<String> list  = null;
		list = getSqlSession().selectList(DocumentMapper.class.getName() + ".findByType");
		sResult.setRsList(list);
		return sResult;
	}
	
}
