package com.hiekn.bridgehealth.service;

import java.util.List;
import java.util.Map;

import com.hiekn.bridgehealth.bean.UserLog;

public interface IUserLogService {
	/**
	 * 
	 * @param userLog
	 */
	public void addUserLog(UserLog userLog)throws Exception;
	
	/**
	 * 分页查询用户日志
	 * @param currentPage
	 * @return
	 */
	public Map findByPage(int index,int size)throws Exception;
	
	/**
	 * 根据UserLog的id删除UserLog
	 * @param id
	 */
	public void deleteUserLog(Integer id)throws Exception;
}
