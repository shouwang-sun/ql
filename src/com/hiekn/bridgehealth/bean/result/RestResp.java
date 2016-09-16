package com.hiekn.bridgehealth.bean.result;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RestResp<T> {

	private Integer code = null;
	private String msg = null;
	private RestData<T> data = null;
	private List<Map<String, Object>> mapList = null;
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public RestData<T> getData() {
		return data;
	}

	public void setData(RestData<T> data) {
		this.data = data;
	}
	
	
	
	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	public RestResp(){}
	
	
	
	public RestResp(Integer code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
 /*	public RestResp(Integer code, String msg,List<Map<String, Object>> mapList){
		this.code = code;
		this.msg = msg;
		this.mapList = mapList;
	}*/
	 
	public RestResp(Integer code, String msg, T data){
		this.code = code;
		this.msg = msg;
		this.data = new RestData<T>(data);
	}
	
	public RestResp(Integer code, String msg, List<T> data){
		this.code = code;
		this.msg = msg;
		this.data = new RestData<T>(data);
	}
	
	public RestResp(Integer code, String msg, RestData data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public RestResp(Integer code, String msg, List<T> data, int count){
		this.code = code;
		this.msg = msg;
		this.data = new RestData<T>(data);
		this.data.setRsCount(new Long(count));
	}

}
