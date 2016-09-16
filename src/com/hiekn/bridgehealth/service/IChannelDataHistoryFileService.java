package com.hiekn.bridgehealth.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JSpinner.DateEditor;











import com.hiekn.bridgehealth.bean.ChannelDataHistoryFile;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.search.SearchResult;

/**
 * @author cao
 *
 * @date 2015-1-13
 */

public interface IChannelDataHistoryFileService {
	public void insertChannelDataHistoryFile(ChannelDataHistoryFile channelDataHistoryFile)throws Exception;
	 
	public SearchResult<ChannelDataHistoryFile> getChannelDataHistoryFileList(Integer index,Integer pageSize,Date createTime,Date endTime,ChannelDataHistoryFile channelDataHistoryFile)throws Exception;
	 
	
}
