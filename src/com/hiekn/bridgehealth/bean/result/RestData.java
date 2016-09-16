package com.hiekn.bridgehealth.bean.result;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RestData<T> {

	private List<T> rsData;
	private Long rsCount;
	
	public List<T> getRsData() {
		return rsData;
	}
	public void setRsData(List<T> rsData) {
		this.rsData = rsData;
	}
	public Long getRsCount() {
		return rsCount;
	}
	public void setRsCount(Long rsCount) {
		this.rsCount = rsCount;
	}
	public RestData(List<T> rsData){
		this.rsData = rsData;
	}
	public RestData(T data){
		List<T> rsData = new ArrayList<T>(1);
		rsData.add(data);
		this.rsData = rsData;
	}
	
	public RestData(List<T> rsData, Long count){
		this.rsData = rsData;
		this.rsCount = count;
	}
	
}
