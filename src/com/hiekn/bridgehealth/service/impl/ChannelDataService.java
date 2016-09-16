package com.hiekn.bridgehealth.service.impl;

import java.util.List;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.mongo.MongoDBService;
import com.hiekn.bridgehealth.service.IChannelDataService;

@Service("channelDataService")
public class ChannelDataService extends SqlSessionDaoSupport implements IChannelDataService {

 
	@Override
	public SearchResult<ChannelData> getChannelDataList(int index, int pageSize,Long startTime,Long endTime, Integer SensorChannelId,Integer flag) throws Exception {
		List<ChannelData> list = MongoDBService.getChannelDataListBySensorChannelId(index, pageSize, startTime, endTime, SensorChannelId, flag);
		SearchResult<ChannelData> searchResult = new SearchResult<ChannelData>();
		searchResult.setRsList(list);
		searchResult.setRsCount(MongoDBService.getTotalChannelDataCount(startTime, endTime, SensorChannelId));
		return searchResult;
	}
}
