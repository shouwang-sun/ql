package com.hiekn.bridgehealth.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jnr.ffi.Struct.int16_t;

import com.hiekn.bridgehealth.bean.ChannelData;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;
/**
 * @author govert.peng
 * @since 2014/12/16
 */
public interface IChannelDataService {
	 
	public SearchResult<ChannelData> getChannelDataList(int index, int pageSize,Long startTime,Long endTime, Integer SensorChannelId,Integer flag)throws Exception;;
	 
}
