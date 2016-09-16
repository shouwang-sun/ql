package com.hiekn.bridgehealth.dao;

import java.util.Date;
import java.util.List;

import com.hiekn.bridgehealth.bean.ChannelDataHistoryFile;

public interface ChannelDataHistoryFileMapper {
	
	List<ChannelDataHistoryFile> findByPage(Integer index,Integer pageSize,Date createTime, Date endTime, Integer bridgeId,Integer sensorTypeId,Integer workSectionId,Integer sensorId,Integer sensorChannelId );
	
	int findByItemNum(Date createTime, Date endTime,Integer bridgeId,Integer sensorTypeId,Integer workSectionId,Integer sensorId,Integer sensorChannelId);
}
