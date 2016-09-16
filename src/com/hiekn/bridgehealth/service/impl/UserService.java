package com.hiekn.bridgehealth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import com.hiekn.bridgehealth.bean.User;
import com.hiekn.bridgehealth.bean.Sensor;
import com.hiekn.bridgehealth.bean.SensorChannel;
import com.hiekn.bridgehealth.bean.SensorType;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.dao.UserMapper;
import com.hiekn.bridgehealth.dao.SensorChannelMapper;
import com.hiekn.bridgehealth.service.IUserService;
@Service("userService")
public class UserService extends SqlSessionDaoSupport implements IUserService{
 
	public SearchResult<User> getUserList(Integer index,Integer pageSize)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		List<User> list = null;
		int count = -1;
		try {
			list = getSqlSession().selectList(UserMapper.class.getName()+".findByPage",map);
			count =  getSqlSession().selectOne(UserMapper.class.getName() + ".findByItemNum",map);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
		SearchResult<User> sResult = new SearchResult<User>();
		sResult.setRsCount(Long.valueOf(count));
		sResult.setRsList(list);
		return sResult;
	}

	@Override
	public User insertUser(User user) throws Exception {
		try {
			int count = getSqlSession().insert(UserMapper.class.getName() + ".insert",user);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return user;
	}

	@Override
	public void updateUser(User user) throws Exception {
		try {
			int count = getSqlSession().update(UserMapper.class.getName() + ".updateById",user);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		
	}

	@Override
	public User findById(Integer id) throws Exception {
		User user = null;
		try {
			user = getSqlSession().selectOne(UserMapper.class.getName() + ".findById",id);
		} catch (Exception e) {
			 //   System.out.println(e);
		}
		return user;
	}

	@Override
	public int deleteByIds(int[] ids) {
		int count = -1;
		try {
			count =  getSqlSession().delete(UserMapper.class.getName() + ".deleteByIds",ids);
		} catch (Exception e) {
			System.err.println(e);
		}
	    return count;
	}
}
