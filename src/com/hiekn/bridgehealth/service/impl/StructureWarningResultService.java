package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jnr.ffi.Struct.int16_t;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.LogicGroup;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.StructureWarning;
import com.hiekn.bridgehealth.bean.StructureWarningResult;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.LogicGroupMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.StructureWarningMapper;
import com.hiekn.bridgehealth.dao.StructureWarningResultMapper;
import com.hiekn.bridgehealth.service.IChannelDataHistoryFileService;
import com.hiekn.bridgehealth.service.ILogicGroupService;
import com.hiekn.bridgehealth.service.IStructureWarningResultService;
import com.hiekn.bridgehealth.service.IStructureWarningService;

@Service("structureWarningResultService")
public class StructureWarningResultService extends SqlSessionDaoSupport implements IStructureWarningResultService{
	@Autowired
	private ILogicGroupService logicGroupService;
	@Autowired
	private IStructureWarningService structureWarningService;
	@Override
	public SearchResult<StructureWarningResult> getStructureWarningResultList(Integer index, Integer pageSize, Date createTime, Date endTime,StructureWarningResult structureWarningResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("startTime", createTime);
		map.put("endTime", endTime);
		map.put("bridgeId", structureWarningResult.getBridgeId());
		map.put("dealResult", structureWarningResult.getDealResult());
		List<StructureWarningResult> list = null;
		int count =  -1;
		try {
			list = getSqlSession().selectList(StructureWarningResultMapper.class.getName()+".findByPage",map);
			count =  getSqlSession().selectOne(StructureWarningResultMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		SearchResult<StructureWarningResult> sResult = new SearchResult<StructureWarningResult>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}
	@Override
	public StructureWarningResult insertStructureWarningResult(StructureWarningResult structureWarningResult) throws Exception {
		try {
			int count = getSqlSession().insert(StructureWarningResultMapper.class.getName() + ".insert",structureWarningResult);
		} catch (Exception e) {
			System.err.println(e);
		}
		return structureWarningResult;
	}
	@Override
	public void updateStructureWarningResultById(StructureWarningResult structureWarningResult) throws Exception {
		try {
			int count = getSqlSession().update(StructureWarningResultMapper.class.getName() + ".updateById",structureWarningResult);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}
	@Override
	public void insertArrayStructureWarningResult(List<StructureWarningResult> structureWarningResultList)throws Exception {
		 int count = getSqlSession().insert(StructureWarningResultMapper.class.getName()+".insertArray",structureWarningResultList);
	}
	@Override
	public List<Integer> findDistinctBridge() {
		List<Integer>bridgeIds = getSqlSession().selectList(StructureWarningResultMapper.class.getName()+".findDistinctBridgeId");
		return bridgeIds;
	}
	@Override
	public StructureWarningResult findById(Integer id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(StructureWarningResultMapper.class.getName() + ".findById",id);
	}
	
	@Override
	public int deleteByBridgeIds(int [] bridgeIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(StructureWarningResultMapper.class.getName() + ".deleteByBridgeIds",bridgeIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	@Override
	public int deleteByStructureIds(int [] structureIds) {
		int count = -1;
		try {
			count =  getSqlSession().delete(StructureWarningResultMapper.class.getName() + ".deleteByStructureIds",structureIds);
		} catch (Exception e) {
			System.err.println(e);
		}
		return count;
	}
	
	/*@Override
	public List<StructureWarningResult> findByPageAndOtherParams(Integer index,Integer pageSize, Date createTime, Date endTime, Integer bridgeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("startTime", createTime);
		map.put("endTime", endTime);
		map.put("bridgeId", bridgeId);
		return getSqlSession().selectList(StructureWarningResultMapper.class.getName()+".findByPageAndOtherParams",map);
	}*/
}
