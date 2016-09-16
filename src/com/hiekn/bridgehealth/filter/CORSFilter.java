package com.hiekn.bridgehealth.filter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter{

	@Override
	public ContainerResponse filter(ContainerRequest req, ContainerResponse crunchifyContainerResponse) {
		ResponseBuilder crunchifyResponseBuilder = Response.fromResponse(crunchifyContainerResponse.getResponse());

		crunchifyResponseBuilder.header("Access-Control-Allow-Origin", "*")

		.header("Access-Control-Allow-Methods", "API, CRUNCHIFYGET, GET, POST, PUT, UPDATE, OPTIONS")

		.header("Access-Control-Max-Age", "151200")

		.header("Access-Control-Allow-Headers", "x-requested-with,Content-Type");

		String crunchifyRequestHeader = req.getHeaderValue("Access-Control-Request-Headers");

		if (null != crunchifyRequestHeader && !crunchifyRequestHeader.equals("null")) {
			crunchifyResponseBuilder.header("Access-Control-Allow-Headers", crunchifyRequestHeader);
		}
		crunchifyContainerResponse.setResponse(crunchifyResponseBuilder.build());
		return crunchifyContainerResponse;
	}

}
