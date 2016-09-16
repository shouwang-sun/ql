package com.hiekn.bridgehealth.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.hiekn.bridgehealth.bean.User;
import com.hiekn.bridgehealth.bean.result.RestData;
import com.hiekn.bridgehealth.bean.result.RestResp;
import com.hiekn.bridgehealth.bean.search.SearchResult;
import com.hiekn.bridgehealth.error.ErrorInfo;
import com.hiekn.bridgehealth.service.IUserService;
import com.hiekn.bridgehealth.service.ISensorChannelService;

@Controller("user")
@Path("user")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserRestApi {
	static final Logger log = Logger.getLogger(UserRestApi.class);
	@Autowired
	private IUserService iUserService;
	
	Gson gson = new Gson();
	
	@POST
	@Path("getUserList")
	public Response getUserList(
			@QueryParam("index") Integer index,
			@QueryParam("pageSize") Integer pageSize) {
		RestResp<User> rs = null;
		try {
			SearchResult<User> sResult = iUserService.getUserList(index,pageSize);
			RestData<User> sData = new RestData<User>(sResult.getRsList(),sResult.getRsCount());
			rs = new RestResp<User>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG, sData);
		} catch (Exception e) {
			log.error(e);
			rs = new RestResp<User>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
	
	@POST
 	@Path("insertUser")
 	public Response insertUser(@FormParam("params")String params){
 		RestResp<User> rs = null;
		try {
			User user = gson.fromJson(params, User.class);
			user.setLastUpdateTime(new Date());
			User user_ = iUserService.insertUser(user);
			rs = new RestResp<User>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,user_);
		} catch (Exception e) {
			 //   System.out.println(e);
			rs = new RestResp<User>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 		
 	}
 	
 	@POST
 	@Path("findUserById")
 	public Response findUserById(@QueryParam("id")Integer id){
 		RestResp<User> rs = null;
		try {
			User user= iUserService.findById(id);
			rs = new RestResp<User>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG,user);
		} catch (Exception e) {
			rs = new RestResp<User>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
 	@POST
 	@Path("updateUserById")
 	public Response updateUserByIds(@FormParam("params")String  params){
 		RestResp<User> rs = null;
		try {
			User user = gson.fromJson(params, User.class);
			user.setLastUpdateTime(new Date());
			iUserService.updateUser(user);
			rs = new RestResp<User>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		} catch (Exception e) {
			rs = new RestResp<User>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
 	}
 	
	@POST
	@Path("deleteUserByIds")
	public Response deleteUserByIds(
			@FormParam("params") String params) throws Exception{
		RestResp<User> rs = null;
 		int [] ids = gson.fromJson(params, int[].class);
		try{
			int count =  iUserService.deleteByIds(ids);
			rs = new RestResp<User>(ErrorInfo.SUCCESS_CODE,ErrorInfo.SUCCESS_MSG);
		}catch (Exception e) {
			rs = new RestResp<User>(ErrorInfo.MYSQL_SERVER_ERROR_CODE,ErrorInfo.MYSQL_SERVER_ERROR_MSG);
		}
		return Response.ok().entity(rs).build();
	}
	
}
