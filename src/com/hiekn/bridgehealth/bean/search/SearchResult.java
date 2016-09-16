package com.hiekn.bridgehealth.bean.search;

import java.util.List;

public class SearchResult<T> {
	private List<T> rsList;
	private Long rsCount;
	
	public List<T> getRsList() {
		return rsList;
	}
	public void setRsList(List<T> rsList) {
		this.rsList = rsList;
	}
	public Long getRsCount() {
		return rsCount;
	}
	public void setRsCount(Long rsCount) {
		this.rsCount = rsCount;
	}
	
}

