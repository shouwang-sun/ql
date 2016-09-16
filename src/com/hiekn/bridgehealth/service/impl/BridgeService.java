package com.hiekn.bridgehealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiekn.bridgehealth.bean.Bridge;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.EvaluateProjectMapper;
import com.hiekn.bridgehealth.dao.EvaluateProjectResultMapper;
import com.hiekn.bridgehealth.dao.SensorChannelMapper;
import com.hiekn.bridgehealth.dao.SensorMapper;
import com.hiekn.bridgehealth.dao.StructureWarningMapper;
import com.hiekn.bridgehealth.dao.StructureWarningResultMapper;
import com.hiekn.bridgehealth.dao.WorkSectionMapper;
import com.hiekn.bridgehealth.service.IBridgeService;

@Service("bridgeService")
public class BridgeService extends SqlSessionDaoSupport implements IBridgeService{
	
	@Override
	public void updateBridge(Bridge bridge) throws Exception {
		try {
			int count = getSqlSession().update(BridgeMapper.class.getName() + ".updateById",bridge);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
	}

	@Override
	public Bridge findById(Integer id) throws Exception {
		Bridge bridge = null;
		try {
			bridge = getSqlSession().selectOne(BridgeMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return bridge;
	}

	@Override
	public SearchResult<Bridge> getBridgeList(Integer index,Integer pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		System.err.println(index);
		map.put("index", Integer.valueOf(index));
		map.put("pageSize", Integer.valueOf(pageSize));
		List<Bridge> list  = null;
		int count = -1;
		try{
			list = getSqlSession().selectList(BridgeMapper.class.getName() + ".findByPage",map);
			count =  getSqlSession().selectOne(BridgeMapper.class.getName() + ".findByItemNum",map);
		}catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<Bridge> sResult = new SearchResult<Bridge>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public Bridge insertBridge(Bridge bridge) throws Exception {
		try {
			int count = getSqlSession().insert(BridgeMapper.class.getName() + ".insert",bridge);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return bridge;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int  deleteByIds(int [] ids) throws Exception  {
		int count = -1;
		count =  getSqlSession().delete(BridgeMapper.class.getName() + ".deleteByIds",ids);
		count =  getSqlSession().delete(WorkSectionMapper.class.getName() + ".deleteByBridgeIds",ids);
		count =  getSqlSession().delete(SensorMapper.class.getName() + ".deleteByBridgeIds",ids);
		count =  getSqlSession().delete(SensorChannelMapper.class.getName() + ".deleteByBridgeIds",ids);
	    count =  getSqlSession().delete(EvaluateProjectMapper.class.getName() + ".deleteByBridgeIds",ids);
	    count =  getSqlSession().delete(EvaluateProjectResultMapper.class.getName() + ".deleteByBridgeIds",ids);
	    count =  getSqlSession().delete(StructureWarningMapper.class.getName() + ".deleteByBridgeIds",ids);
	    count =  getSqlSession().delete(StructureWarningResultMapper.class.getName() + ".deleteByBridgeIds",ids);
		return count;
	}

	@Override
	public Bridge findByPageAndWorkSection(Integer id) {
		Bridge  bridge = getSqlSession().selectOne(BridgeMapper.class.getName() + ".findByPageAndWorkSection",id);
		return bridge;
	}	
}
