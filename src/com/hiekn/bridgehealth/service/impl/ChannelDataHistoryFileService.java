package com.hiekn.bridgehealth.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.ChannelDataHistoryFile;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.BridgeMapper;
import com.hiekn.bridgehealth.dao.ChannelDataHistoryFileMapper;
import com.hiekn.bridgehealth.service.IChannelDataHistoryFileService;

@Service("channelDataHistoryService")

public class ChannelDataHistoryFileService extends SqlSessionDaoSupport implements IChannelDataHistoryFileService {

	@Override
	public void insertChannelDataHistoryFile(ChannelDataHistoryFile channelDataHistoryFile) throws Exception {
		try {
			int count = getSqlSession().insert(ChannelDataHistoryFileMapper.class.getName() + ".insert",channelDataHistoryFile);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	@Override
	public SearchResult<ChannelDataHistoryFile> getChannelDataHistoryFileList (Integer index,Integer pageSize,Date createTime, Date endTime,ChannelDataHistoryFile channelDataHistoryFile)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("createTime", createTime);
		map.put("endTime", endTime);
		
		map.put("bridgeId", channelDataHistoryFile.getBridgeId());
		map.put("sensorTypeId", channelDataHistoryFile.getSensorTypeId());
		map.put("workSectionId", channelDataHistoryFile.getWorkSectionId());
		map.put("sensorId", channelDataHistoryFile.getSensorId());
		map.put("sensorChannelId", channelDataHistoryFile.getSensorChannelId());
		
		List<ChannelDataHistoryFile> channelDataHistoryFiles = getSqlSession().selectList(ChannelDataHistoryFileMapper.class.getName() + ".findByPage",map);
		int count =  getSqlSession().selectOne(ChannelDataHistoryFileMapper.class.getName() + ".findByItemNum",map);
		SearchResult<ChannelDataHistoryFile> sResult = new SearchResult<ChannelDataHistoryFile>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(channelDataHistoryFiles);
		return sResult;
	}

}
