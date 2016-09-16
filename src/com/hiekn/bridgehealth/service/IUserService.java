package com.hiekn.bridgehealth.service;

import java.util.Date;

import com.hiekn.bridgehealth.bean.User;
import com.hiekn.bridgehealth.bean.search.SearchResult;

public interface IUserService {
	public SearchResult<User> getUserList(Integer index,Integer pageSize)throws Exception;

	public User insertUser(User user)throws Exception;
	
	public void updateUser(User user)throws Exception;
	
	public User findById(Integer id)throws Exception;
	 
	public int deleteByIds(int [] ids);
}
