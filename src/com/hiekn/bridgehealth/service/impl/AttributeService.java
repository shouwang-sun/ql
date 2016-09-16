package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialArray;


import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.Attribute;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.AttributeMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IAttributeService;
@Service("attributeService")
public class AttributeService extends SqlSessionDaoSupport implements IAttributeService{

	@Override
	public SearchResult<Attribute> getAttributeList(Integer index,Integer pageSize,Integer bridgeId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("bridgeId", bridgeId);
		List<Attribute> list = null;
		List<Attribute> list_ = null;
		try {
			list = getSqlSession().selectList(AttributeMapper.class.getName() + ".findByPage",map);
			list_ = getSqlSession().selectList(AttributeMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		SearchResult<Attribute> searchResult = new SearchResult<Attribute>();
		searchResult.setRsList(list);
		searchResult.setRsCount(Long.valueOf(list_.size()));
		return searchResult;
	}

	@Override
	public Attribute insertAttribute(Attribute attribute) throws Exception {
		try {
			int count = getSqlSession().insert(AttributeMapper.class.getName() + ".insert",attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		return attribute;
	}

	@Override
	public void updateAttributeById(Attribute attribute) throws Exception {
		try {
			getSqlSession().update(AttributeMapper.class.getName() + ".updateById",attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
	
	@Override
	public void updateAttributeByName(Attribute attribute) throws Exception {
		try {
			getSqlSession().update(AttributeMapper.class.getName() + ".updateByName",attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public int deleteByNameList(String[] nameList) {
		int count = -1;
		try {
			count =  getSqlSession().delete(AttributeMapper.class.getName() + ".deleteByName",nameList);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(AttributeMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(AttributeMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
}
